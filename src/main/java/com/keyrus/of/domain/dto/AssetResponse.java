package com.keyrus.of.domain.dto;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(name = "assetResponse")
public class AssetResponse {

    private String url;
    private String status;

}
