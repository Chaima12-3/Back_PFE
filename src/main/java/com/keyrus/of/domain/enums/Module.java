package com.keyrus.of.domain.enums;


import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "module", hidden = true)
public enum Module {
    CARE_MEDIA_CENTER,
    CARE_LOYALTY_FAMILY,
    CARE_OFFER_FAMILY,
    CARE_PACKAGE,
    CARE_OPTION_FAMILY,
    PLACE_VENDOR,
    PLACE_CATEGORY,
    PLACE_SERVICE,
    PLACE_SHOP,
    PLACE_PRODUCT,
    STORIES,
    COUNTRY,
    CAMPAIGN,
    AUDIENCE
}