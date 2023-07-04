package com.keyrus.of.domain;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(name = "error")
public class Error {
    private String code;
    private String message;
    private List<ErrorDetails> details ;
}
