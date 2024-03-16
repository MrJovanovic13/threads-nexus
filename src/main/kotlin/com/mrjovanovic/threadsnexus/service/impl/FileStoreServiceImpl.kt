package com.mrjovanovic.threadsnexus.service.impl

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.deleteObject
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.model.ObjectCannedAcl
import aws.sdk.kotlin.services.s3.putObject
import aws.smithy.kotlin.runtime.content.ByteStream
import com.mrjovanovic.threadsnexus.service.FileStoreService
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders.CONTENT_LENGTH
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileStoreServiceImpl(private val s3Client: S3Client) : FileStoreService {

    @Value("\${amazon.properties.bucket.name}")
    private lateinit var bucketName: String

    override fun uploadFile(fileName: String, multipartFile: MultipartFile) {
        runBlocking {
            s3Client.use { s3 ->
                s3.putObject {
                    bucket = bucketName
                    key = fileName
                    body = ByteStream.fromBytes(multipartFile.bytes)
                    metadata = mapOf(
                        CONTENT_TYPE to multipartFile.contentType!!, // TODO will this ever be null if file is not null?
                        CONTENT_LENGTH to multipartFile.size.toString()
                    )
                    acl = ObjectCannedAcl.PublicRead
                }
            }
        }
    }

    override fun downloadFile(fileName: String): ByteStream {
        return runBlocking {
            s3Client.getObject(GetObjectRequest.invoke {
                key = fileName
                bucket = bucketName
            }) { getObjectResponse -> getObjectResponse.body!! } // TODO What if file does not exist/is null?
        }
    }

    override fun deleteFile(fileName: String) {
        runBlocking {
            s3Client.deleteObject {
                key = fileName
                bucket = bucketName
            }
        }
    }
}
