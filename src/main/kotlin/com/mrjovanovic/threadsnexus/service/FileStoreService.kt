package com.mrjovanovic.threadsnexus.service

import com.mrjovanovic.threadsnexus.model.dto.DataBufferFluxWithMetadataDto
import org.springframework.http.codec.multipart.FilePart

interface FileStoreService {

    suspend fun uploadFile(fileName: String, filePart: FilePart)
    suspend fun downloadFile(fileName: String): DataBufferFluxWithMetadataDto
    suspend fun deleteFile(fileName: String)
}