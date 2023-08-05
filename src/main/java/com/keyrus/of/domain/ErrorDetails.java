package com.keyrus.of.domain;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(name = "details")
public class ErrorDetails {

    private String field;
    private String message;
}
