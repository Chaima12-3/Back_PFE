package com.keyrus.of.domain.dto;

import com.keyrus.of.domain.enums.Module;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(name = "requestAsset")
public class RequestAsset {

    @NotNull
    private String tenantIdDestination;
    @NotNull
    private String tenantIdSource;
    @NotNull
    private List<String> filesNames;
    @NotNull
    @Schema(enumeration = {"CARE_MEDIA_CENTER", "CARE_LOYALTY_FAMILY", "CARE_OFFER_FAMILY", "CARE_PACKAGE",
                            "CARE_OPTION_FAMILY", "PLACE_VENDOR", "PLACE_CATEGORY", "PLACE_SERVICE",
                             "PLACE_SHOP", "PLACE_PRODUCT", "STORIES", "COUNTRY"},
                                type = SchemaType.STRING, implementation = Void.class)
    private Module module;




}
