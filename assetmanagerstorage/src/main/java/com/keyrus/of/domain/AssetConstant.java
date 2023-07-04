package com.keyrus.of.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssetConstant {

    public static final String TENANT_ID = "x-tenantId";
    public static final String USERNAME = "x-userName";
    public static final String LANG = "x-lang";
    public static final String ROLES = "x-roles";
    public static final String VENDOR_KEY = "x-vendorKey";
    public static final String HEADER_ERROR_MESSAGE = "This header should not be null nor empty!";
    public static final String REQUEST_FORMAT_ERROR_MESSAGE = "Invalid request format (JSON request could not be parsed)";
    public static final String REQUIRED_ERROR_MESSAGE = "Constraint fields required";
    public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";


}
