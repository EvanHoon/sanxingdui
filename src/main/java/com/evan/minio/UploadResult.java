package com.evan.minio;

import lombok.Data;

@Data
public class UploadResult {
    private Boolean success = false;
    private String message;
    private String etag;
    private String versionId;
    private String bucket;
    private String region;
    private String fileName;
}
