package com.evan.minio;

import lombok.Data;

@Data
public class MinioConfig {
    private String endpoint = "http://10.2.250.136:32501";
    private String accessKey = "dev";
    private String secretKey = "dev@123456";
}
