//package com.evan.minio;
//
//import com.zeekr.JacocoAutoConfig;
//import com.zeekr.jacoco.JacocoConsumerConfig;
//import io.minio.BucketExistsArgs;
//import io.minio.MinioClient;
//import io.minio.ObjectWriteResponse;
//import io.minio.UploadObjectArgs;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.File;
//
//@Service
//@Slf4j
//public class MinioTemplete {
//
//    @Autowired
//    private MinioConfig minioConfig ;
//
//    private MinioClient minioClient;
//
//    @PostConstruct
//    public void init() {
//        minioConfig = new MinioConfig();
//        minioClient = MinioClient.builder().endpoint(minioConfig.getEndpoint()).credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey()).build();
//    }
//
//    public UploadResult uploadFile(String bucket, File file) {
//        UploadResult uploadResult = new UploadResult();
//        try {
//            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
//            if (!isExist) {
//                log.error("上传文件失败，bucket:{}不存在", bucket);
//
//                uploadResult.setMessage("上传文件失败，bucket:" + bucket + "不存在");
//                return uploadResult;
//            }
//
//            ObjectWriteResponse response = minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucket).object(minio.APP_NAME + "/" + file.getName()).filename(file.getPath()).build());
//            uploadResult.setSuccess(true);
//            uploadResult.setMessage("success");
//            uploadResult.setEtag(response.etag());
//            uploadResult.setVersionId(response.versionId());
//            uploadResult.setBucket(response.bucket());
//            uploadResult.setRegion(response.region());
//            uploadResult.setFileName(response.object());
//        } catch (Exception e) {
//            log.error("上传文件失败", e);
//            uploadResult.setMessage(e.getMessage());
//            return uploadResult;
//        } finally {
//            file.delete();
//        }
//
//        return uploadResult;
//    }
//
//}
