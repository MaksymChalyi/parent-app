package com.maksimkaxxl.springbootrestfulapi.dtos.responce;

import java.util.List;

public record UploadedEmployeeResponse(
        int successfulImports,
        int failedImports,
        List<String> detailsOfFail
) {
}
