package com.keyrus.of.exception;

import com.keyrus.of.domain.Error;
import com.keyrus.of.domain.ErrorDetails;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

public class RequestException extends WebApplicationException {

    public RequestException(String code, Response.Status status, String message, List<ErrorDetails> details){
        super(Response.status(status)
                .entity(Error.builder()
                        .code(code)
                        .message(message)
                        .details(details)
                        .build())
                .build());
    }

}
