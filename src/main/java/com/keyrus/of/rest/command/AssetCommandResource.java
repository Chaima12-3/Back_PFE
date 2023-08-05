package com.keyrus.of.rest.command;


import com.keyrus.of.domain.dto.AssetResponse;
import com.keyrus.of.domain.dto.RequestAsset;
import com.keyrus.of.service.AssetService;
import com.keyrus.of.domain.Error;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.HttpHeaders;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@RegisterForReflection(targets = {MultivaluedMap.class})
@Path("/api/asset-command/assets")
public class AssetCommandResource {


    private final Logger log = LoggerFactory.getLogger(AssetCommandResource.class);

    @Inject
    AssetService assetService;

    @POST
    @Path("/add-asset/{tenantId}/{module}")
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "success to upload asset", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = AssetResponse.class))),
            @APIResponse(responseCode = "400", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = "401", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = "403", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = "406", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
    })
    @RequestBody(required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA, schema = @Schema(name = "file", type = SchemaType.OBJECT, properties = @SchemaProperty(name = "file", type = SchemaType.STRING, format = "binary"))))
    public Response uploadFile(@Context HttpHeaders headers, MultipartFormDataInput input, @PathParam("tenantId") String tenantId,
                               @PathParam("module") @Schema(enumeration = {"CARE_MEDIA_CENTER", "CARE_LOYALTY_FAMILY", "CARE_OFFER_FAMILY", "CARE_PACKAGE",
                                       "CARE_OPTION_FAMILY", "PLACE_VENDOR", "PLACE_CATEGORY", "PLACE_SERVICE", "PLACE_SHOP", "PLACE_PRODUCT", "STORIES", "COUNTRY"}) String module) throws NoSuchAlgorithmException, InvalidKeyException {

        log.info("REST request to add asset : {}", tenantId);

        return assetService.uploadFile(headers,input,tenantId,module);

    }


    @POST
    @Path("/clone-asset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = AssetResponse.class))),
            @APIResponse(responseCode = "400", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = "401", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = "403", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = "406", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
    })
    @RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RequestAsset.class)))
    public List<AssetResponse> cloneAsset(@Context HttpHeaders headers, RequestAsset request) {

        log.info("REST request to clone asset : {}", request);

        assetService.validateEntity(request);

        return assetService.cloneAsset(headers, request);

    }


    @POST
    @Path("/update-asset/{tenantId}/{module}")
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    @RequestBody(required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA, schema = @Schema(name = "file", type = SchemaType.OBJECT, properties = @SchemaProperty(name = "file", type = SchemaType.STRING, format = "binary"))))
    public Response editFile(@Context HttpHeaders headers, MultipartFormDataInput input, @PathParam("tenantId") String tenantId,
                             @PathParam("module") @Schema(enumeration = {"CARE_MEDIA_CENTER", "CARE_LOYALTY_FAMILY", "CARE_OFFER_FAMILY", "CARE_PACKAGE",
                                     "CARE_OPTION_FAMILY", "PLACE_VENDOR", "PLACE_CATEGORY", "PLACE_SERVICE", "PLACE_SHOP","CAMPAIGN","AUDIENCE", "PLACE_PRODUCT", "STORIES", "COUNTRY"}) String module) throws NoSuchAlgorithmException, InvalidKeyException {

        log.info("REST request to edit asset : {}", tenantId);

        return assetService.editFile(headers,input,tenantId,module);

    }

}
