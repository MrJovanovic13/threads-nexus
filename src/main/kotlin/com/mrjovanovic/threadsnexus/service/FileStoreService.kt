package com.mrjovanovic.threadsnexus.service

import aws.smithy.kotlin.runtime.content.ByteStream
import org.springframework.web.multipart.MultipartFile

interface FileStoreService {

    fun uploadFile(fileName: String, multipartFile: MultipartFile)

    fun downloadFile(fileName: String): ByteStream

    fun deleteFile(fileName: String)
}