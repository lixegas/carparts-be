package com.morelli.carparts.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${spring.cloud.aws.s3.endpoint}")
    private String s3Endpoint;

    @Value("${spring.cloud.aws.access-key}")
    private String accessKeyId;

    @Value("${spring.cloud.aws.secret-key}")
    private String secretAccessKey;


    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
        builder.setCredentials(new AWSStaticCredentialsProvider(credentials));
        builder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Endpoint, "us-east-1"));
        builder.setPathStyleAccessEnabled(true);
        return builder.build();
    }
}






