package com.keyrus.of.utils;


import com.keyrus.of.domain.AssetConstant;
import com.keyrus.of.domain.ErrorDetails;
import com.keyrus.of.exception.RequestException;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RegisterForReflection
public class AssetUtils {

    public static void validateHeadersParams(HttpHeaders headers, List<String> headersToCheck) {
        List<ErrorDetails> details = new ArrayList<>();
        Iterator<String> var4 = headersToCheck.iterator();

        while(true) {
            while(var4.hasNext()) {
                String hValue = var4.next();
                if (hValue.equals(AssetConstant.ROLES) && Boolean.FALSE.equals(roleIdHeaderValueNull(headers))) {
                    details.add(new ErrorDetails(AssetConstant.ROLES, AssetConstant.HEADER_ERROR_MESSAGE));
                } else if (List.of(AssetConstant.LANG, AssetConstant.VENDOR_KEY, AssetConstant.USERNAME).contains(hValue) && Boolean.FALSE.equals(hasValueOnHeaders(headers, hValue))) {
                    details.add(new ErrorDetails(hValue, AssetConstant.HEADER_ERROR_MESSAGE));
                } else if (hValue.equals(AssetConstant.TENANT_ID) && !hasRoles(headers, List.of(AssetConstant.ROLE_SUPERADMIN)) && Boolean.FALSE.equals(hasValueOnHeaders(headers, hValue))) {
                    details.add(new ErrorDetails(hValue, AssetConstant.HEADER_ERROR_MESSAGE));
                }
            }

            if (!details.isEmpty()) {
                throw new RequestException("4000", Response.Status.BAD_REQUEST,AssetConstant.REQUEST_FORMAT_ERROR_MESSAGE, details);
            }

            return;
        }
    }

    public static Boolean roleIdHeaderValueNull(HttpHeaders headers) {
        return headers.getRequestHeader(AssetConstant.ROLES) == null || !headers.getRequestHeader(AssetConstant.ROLES).isEmpty();
    }

    public static boolean hasRoles(HttpHeaders headers, List<String> roles) {
        Iterator<String> var3 = roles.iterator();

        String role;
        do {
            if (!var3.hasNext()) {
                return Boolean.FALSE;
            }

            role = var3.next();
        } while(!roleIdHeaderValueNull(headers).equals(Boolean.TRUE) || !headers.getHeaderString(AssetConstant.ROLES).contains(role));

        return Boolean.TRUE;
    }

    public static Boolean hasValueOnHeaders(HttpHeaders headers, String headerValue) {
        return headers != null && headerValue != null && !headerValue.isEmpty() && headers.getHeaderString(headerValue) != null && !headers.getHeaderString(headerValue).isEmpty();
    }

}
