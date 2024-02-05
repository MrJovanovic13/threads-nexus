package com.mrjovanovic.threadsnexus.service

import org.springframework.web.multipart.MultipartFile

interface FileTransferContextHolderService {

    fun putFileInContext(recipientId: String, file: MultipartFile)

    fun getFileFromContextForRecipient(recipientId: String): MultipartFile
}
