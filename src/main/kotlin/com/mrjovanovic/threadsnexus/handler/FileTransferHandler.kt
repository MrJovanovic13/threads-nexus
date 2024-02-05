package com.mrjovanovic.threadsnexus.handler

import com.mrjovanovic.threadsnexus.service.CommandIssuerService
import com.mrjovanovic.threadsnexus.service.FileTransferContextHolderService
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono

@Component
class FileTransferHandler(
    private val fileTransferContextHolderService: FileTransferContextHolderService,
    private val commandIssuerService: CommandIssuerService
) {

    fun saveFile(req: ServerRequest): Mono<ServerResponse> {
        val recipientId = req.queryParam("deviceId").get()
        commandIssuerService.issueFileDownloadCommand(recipientId)

        return Mono.empty()
    }

    fun getFile(req: ServerRequest): Mono<ServerResponse> {
        val recipientId = req.queryParam("deviceId").get()

        return ServerResponse.ok()
            .json()
            .body(
                fileTransferContextHolderService.getFileFromContextForRecipient(recipientId),
                MultipartFile::class.java
            )
    }
}
