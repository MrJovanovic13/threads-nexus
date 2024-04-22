package com.mrjovanovic.threadsnexus.controller

import com.mrjovanovic.threadsnexus.service.CommandIssuerService
import com.mrjovanovic.threadsnexus.service.FileStoreService
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toMono
import java.util.*


@RestController
class FileController(
    private val commandIssuerService: CommandIssuerService,
    private val fileStoreService: FileStoreService
) {

    @PostMapping("/api/devices/{deviceId}/files", consumes = ["multipart/form-data"])
    suspend fun uploadFileToDevices(
        @RequestPart("file") filePart: FilePart,
        @RequestPart("recipientDeviceIds") recipientDeviceIds: List<String>,
        @PathVariable deviceId: String,
    ) {
        fileStoreService.uploadFile(deviceId, filePart)
            .toMono()
            .doOnSuccess {
                commandIssuerService.issueFileDownloadCommandFromOneToManyDevices(
                    deviceId,
                    recipientDeviceIds
                )
            }
            .subscribe()
    }

    @PostMapping("/api/groups/{groupId}/files", consumes = ["multipart/form-data"])
    suspend fun uploadFileToDevicesInGroup(
        @RequestPart("file") filePart: FilePart,
        @PathVariable groupId: String,
        @RequestParam fileUploaderDeviceId: String
    ) {
        fileStoreService.uploadFile(groupId, filePart)
            .toMono()
            .doOnSuccess {
                commandIssuerService.issueFileDownloadCommandToEntireDeviceGroup(
                    fileUploaderDeviceId,
                    groupId
                )
            }
            .subscribe()
    }

    @GetMapping("/api/devices/{deviceId}/files")
    suspend fun downloadFile(@PathVariable deviceId: String): ResponseEntity<Flux<DataBuffer>> {
        val dataBufferFluxWithMetadata = fileStoreService.downloadFile(deviceId)
        val filename = dataBufferFluxWithMetadata.metadata
            ?.get(HttpHeaders.CONTENT_DISPOSITION.lowercase())
            ?.substringAfter("attachment; ")

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(dataBufferFluxWithMetadata.dataBufferFlux)
    }
}
