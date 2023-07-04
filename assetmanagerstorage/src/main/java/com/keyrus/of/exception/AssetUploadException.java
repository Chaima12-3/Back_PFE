package com.keyrus.of.exception;

import com.keyrus.of.domain.Error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class AssetUploadException extends WebApplicationException {

    public AssetUploadException( String message, String code){
        super(Response
                .status(Response.Status.BAD_REQUEST)
                .entity(Error.builder()
                        .message(message)
                        .code(code)
                        .build())
                .build());
    }

}
