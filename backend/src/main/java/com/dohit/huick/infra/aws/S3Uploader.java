package com.dohit.huick.infra.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dohit.huick.global.util.ImageUtil;
import com.dohit.huick.global.vo.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3 amazonS3;
    private final ImageUtil imageUtil;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.urlPrefix}")
    private String s3UrlPrefix;

    public Image uploadSignature(MultipartFile file, String dirName) throws IOException {
        Image image = imageUtil.convertMultipartToImage(file);
        String fileName = convertSignatureName(dirName, image.getImageUUID(), image.getImageName(), image.getImageType().toString());
        image.setUrl(uploadSignatureToS3(fileName, file));

        return image;
    }

    public void deleteSignature(Image image, String dirName) {
        if (image != null && image.getImageUUID() != null) {
            String fileName = convertSignatureName(dirName, image.getImageUUID(), image.getImageName(), image.getImageType().toString());
            amazonS3.deleteObject(bucket, fileName);
        }
    }

    public String uploadPdf(byte[] pdfContract, String dirName, Long contractId)  throws IOException {
        String fileName = convertPdfName(dirName, contractId);

        return uploadPdfToS3(fileName, pdfContract);
    }

    private String convertSignatureName(String dirName, String UUID, String name, String type) {
        return dirName + "/" + UUID + "_" + name + "." + type;
    }

    private String convertPdfName(String dirName, Long contractId) {
        return dirName + "/" + contractId.toString() + ".pdf";
    }

    public String uploadSignatureToS3(String fileName, MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);

        return "https://" + bucket + "." + s3UrlPrefix + "/" + fileName;
    }

    public String uploadPdfToS3(String fileName, byte[] pdfContract) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        amazonS3.putObject(bucket, fileName, new ByteArrayInputStream(pdfContract), metadata);

        return "https://" + bucket + "." + s3UrlPrefix + "/" + fileName;
    }
}
