package com.mrjovanovic.threadsnexus.service.impl

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.deleteObject
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.model.ObjectCannedAcl
import aws.sdk.kotlin.services.s3.putObject
import aws.smithy.kotlin.runtime.content.ByteStream
import aws.smithy.kotlin.runtime.content.toByteArray
import com.mrjovanovic.threadsnexus.model.dto.DataBufferFluxWithMetadataDto
import com.mrjovanovic.threadsnexus.service.FileStoreService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.HttpHeaders.*
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


@Service
class FileStoreServiceImpl(private val s3Client: S3Client) : FileStoreService {

    @Value("\${amazon.properties.bucket.name}")
    private lateinit var bucketName: String

    override suspend fun uploadFile(fileName: String, filePart: FilePart) {
        val fromBytes = ByteStream.fromBytes(mergeDataBuffers(filePart.content()))
        val filename = filePart.filename()

        s3Client.let { s3 ->
            s3.putObject {
                bucket = bucketName
                key = fileName
                body = fromBytes
                metadata = mapOf(
                    CONTENT_TYPE to filePart.headers().contentType.toString(),
                    CONTENT_LENGTH to fromBytes.contentLength.toString(),
                    CONTENT_DISPOSITION to "attachment; $filename"
                )
                acl = ObjectCannedAcl.PublicRead
            }
        }
    }

    override suspend fun downloadFile(fileName: String): DataBufferFluxWithMetadataDto {
        return s3Client.getObject(GetObjectRequest.invoke {
            key = fileName
            bucket = bucketName
        }) { getObjectResponse ->
            val byteArray = getObjectResponse.body!!.toByteArray()
            val dataBufferFlux = DataBufferUtils.read(
                ByteArrayResource(byteArray),
                DefaultDataBufferFactory(),
                1024
            )
            val metadata = getObjectResponse.metadata
            println(metadata)
            DataBufferFluxWithMetadataDto(dataBufferFlux, metadata)
        }
    }

    override suspend fun deleteFile(fileName: String) {
        s3Client.deleteObject {
            key = fileName
            bucket = bucketName
        }
    }

    // Entire file is being read in memory. Refactor to a streaming solution in the future.
    suspend fun mergeDataBuffers(dataBufferFlux: Flux<DataBuffer>): ByteArray {
        return DataBufferUtils.join(dataBufferFlux)
            .map { dataBuffer ->
                val bytes = ByteArray(dataBuffer.readableByteCount())
                dataBuffer.read(bytes)
                DataBufferUtils.release(dataBuffer)
                bytes
            }.awaitSingle()
    }
}
