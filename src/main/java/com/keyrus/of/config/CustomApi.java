package com.keyrus.of.config;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title="controltower-asset-manager",
                version = "1.0.0",
                description = "asset manager api MINIO"),
        components = @Components(

        ))
public class CustomApi extends Application {
}
