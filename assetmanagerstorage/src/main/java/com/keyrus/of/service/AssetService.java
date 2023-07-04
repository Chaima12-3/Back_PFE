package com.keyrus.of.service;


import com.keyrus.of.domain.AssetConstant;
import com.keyrus.of.domain.Error;
import com.keyrus.of.domain.ErrorDetails;
import com.keyrus.of.domain.dto.AssetResponse;
import com.keyrus.of.domain.dto.RequestAsset;

import com.keyrus.of.exception.AssetUploadException;
import com.keyrus.of.exception.RequestException;
import com.keyrus.of.utils.AssetUtils;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;

@ApplicationScoped
@RegisterForReflection(targets= {PutObjectArgs.class, BucketExistsArgs.class})
public class AssetService {

    private final Logger log = LoggerFactory.getLogger(AssetService.class);

    @Inject
    Validator validator;

    @ConfigProperty(name = "minio.url")
    String minioUrl;
    @ConfigProperty(name = "minio.access-key")
    String miniologin;
    @ConfigProperty(name = "minio.secret-key")
    String minioPassword;
    @ConfigProperty(name = "minio.bucket")
    String minioBucket;

    public Response uploadAsset(InputStream inputStream, String tenantId, String module, String filename) throws IOException, NoSuchAlgorithmException, InvalidKeyException, AssetUploadException {

        String mimeType = formatDetector(filename);
        if(mimeType.equals("false")){return Response.status(Response.Status.NOT_FOUND).entity(Error.builder().message("format not supported yet").build()).build();}

        try {
            var minioClient =
                    MinioClient.builder()
                            .endpoint(minioUrl)
                            .credentials(miniologin, minioPassword)
                            .build();

            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioBucket)
                            .object(tenantId+"/"+module+"/"+filename)
                            .stream(inputStream,  -1, 10485760)
                            .contentType(mimeType)
                            .build());
            var message =  String.format("%s%s/%s/%s/%s", minioUrl,minioBucket,tenantId, module, filename);
            return Response.status(Response.Status.CREATED).entity(AssetResponse.builder().url(message).build()).build();

        } catch (MinioException e) {
            log.error("Error occurred : {0}", e);
        }
        return Response.status(Response.Status.NOT_FOUND).entity(Error.builder().message("Error").build()).build();
    }

    public List<AssetResponse> cloneAsset(HttpHeaders headers, RequestAsset request)  {

        AssetUtils.validateHeadersParams(headers, List.of(AssetConstant.TENANT_ID, AssetConstant.USERNAME));

        List<AssetResponse> assetResponses = new ArrayList<>();

        var minioClient =
                MinioClient.builder()
                        .endpoint(minioUrl)
                        .credentials(miniologin, minioPassword)
                        .build();

        if (!request.getFilesNames().isEmpty()) {
            request.getFilesNames().stream().forEach(fileName -> {
                try {
                    assetResponses.add(copyAsset(minioClient, request, fileName));
                } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                    assetResponses.add(AssetResponse.builder().status("An error occurred when copying file "+ fileName).build());
                }
            });
            return assetResponses;
        } else {
            throw new AssetUploadException("filesNames should not be empty", "5000");
        }

    }

    public String formatDetector(String filename){

        String mimeType ;

        mimeType = checkImageFormats(filename);
        if (mimeType.equals("other")){
            mimeType = checkOtherFormats(filename);
        }
        return mimeType;
    }

    private String checkImageFormats(String filename) {

        if(filename.contains(".jpg") || filename.contains(".jpeg")){
            return "image/jpeg";
        }else if(filename.contains(".png")){
            return "image/png";
        }else if(filename.contains(".tif") || filename.contains(".tiff")){
            return "image/tiff";
        }else if(filename.contains(".gif")){
            return "image/gif";
        }else if(filename.contains(".txt")){
            return "text/plain";
        }else{
            return "other";
        }
    }

    private String checkOtherFormats(String filename) {

        if(filename.contains(".pdf")){
            return "application/pdf";
        }else if(filename.contains(".mpeg") ){
            return "video/mpeg";
        }else if(filename.contains(".avi") ){
            return "video/x-msvideo";
        }else if(filename.contains(".qt") || filename.contains(".avi") || filename.contains(".mov") ){
            return "video/quicktime";
        }else if(filename.contains(".mp3")  ){
            return "audio/mpeg";
        }else if(filename.contains(".mp4")  ){
            return "video/mp4";
        }else if(filename.contains(".csv")  ){
            return "text/csv";
        }else if(filename.contains(".xlsx")  ){
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }else if(filename.contains(".xls")  ){
            return "application/vnd.ms-excel";
        }else{
            return "false";
        }
    }

    private AssetResponse copyAsset(MinioClient minioClient, RequestAsset request, String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        try {

            minioClient.copyObject(CopyObjectArgs.builder()
                    .bucket(minioBucket)
                    .object(request.getTenantIdDestination() + "/" + request.getModule() + "/" + fileName)
                    .source(CopySource.builder()
                            .bucket(minioBucket)
                            .object(request.getTenantIdSource() + "/" + request.getModule() + "/" + fileName)
                            .build())
                    .build());
            return AssetResponse.builder()
                    .status("Success to clone file")
                    .url(String.format("%s%s/%s/%s/%s", minioUrl,minioBucket,request.getTenantIdDestination(), request.getModule().name(),fileName))
                    .build();

        } catch (ErrorResponseException errorResponseException) {

            if (errorResponseException.errorResponse().code().equals("NoSuchKey")) {
                return AssetResponse.builder().status("The specified source object '" + request.getTenantIdSource() + "/" + request.getModule() + "/" + fileName + "' does not exist").build();
            } else {
                return AssetResponse.builder().status(errorResponseException.getMessage()).build();
            }

        } catch (MinioException minioException) {
            return AssetResponse.builder().status(minioException.getMessage()).build();
        }

    }

    public void validateEntity(RequestAsset request) {

        Set<ConstraintViolation<RequestAsset>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            List<ErrorDetails> details = new ArrayList<>();
            violations.stream().forEach(constraintViolation -> details.add(new ErrorDetails(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage())));
            throw new RequestException("5003",Response.Status.INTERNAL_SERVER_ERROR,"Constraint fields required",details);
        }
    }

    public Response uploadFile(HttpHeaders headers, MultipartFormDataInput input, String tenantId, String module) throws NoSuchAlgorithmException, InvalidKeyException {

        AssetUtils.validateHeadersParams(headers, List.of(AssetConstant.TENANT_ID, AssetConstant.USERNAME));

        var fileName = "";
        try {
            Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
            if (uploadForm.get("file") != null) {

                var inputPart = uploadForm.get("file").get(0);

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                String finalFileName = fileName;
                Optional<Map<String, String>> optionalExtension = Optional.ofNullable(fileName)
                        .filter(f -> f.contains("."))
                        .map(f -> {
                            Map<String, String> nameSplit = new HashMap<>();
                            nameSplit.put(f.substring(0, finalFileName.lastIndexOf(".")), f.substring(finalFileName.lastIndexOf(".")).toLowerCase());
                            return nameSplit;
                        });


                if (optionalExtension.isPresent()) {
                    fileName = optionalExtension.get().entrySet().iterator().next().getKey() + "_" + new Timestamp(System.currentTimeMillis()).getTime() + optionalExtension.get().entrySet().iterator().next().getValue();
                } else {
                    throw new AssetUploadException("problem in file name", "5000");
                }
                var inputStream = inputPart.getBody(InputStream.class, null);

                return uploadAsset(inputStream, tenantId, module, fileName);
            } else {
                throw new AssetUploadException("file parameters not found", "5000");
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return Response.status(200).entity("uploadFile is called, Uploaded file name : " + fileName).build();

    }

    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                return name[1].trim().replace("\"", "");
            }
        }
        return "unknown";
    }

    public Response editFile(HttpHeaders headers, MultipartFormDataInput input, String tenantId, String module) throws NoSuchAlgorithmException, InvalidKeyException {

        AssetUtils.validateHeadersParams(headers, List.of(AssetConstant.TENANT_ID, AssetConstant.USERNAME));

        var fileName = "";
        try {
            Map<String, List<InputPart>> uploadForm2 = input.getFormDataMap();
            if (uploadForm2.get("file") != null) {

                var input2 = uploadForm2.get("file").get(0);

                MultivaluedMap<String, String> header = input2.getHeaders();
                fileName = getFileName(header);
                var inputStream = input2.getBody(InputStream.class, null);

                return uploadAsset(inputStream, tenantId, module, fileName);
            } else {
                throw new AssetUploadException("file parameters not found", "5000");
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return Response.status(200).entity("uploadFile is called, Uploaded file name : " + fileName).build();

    }

}
