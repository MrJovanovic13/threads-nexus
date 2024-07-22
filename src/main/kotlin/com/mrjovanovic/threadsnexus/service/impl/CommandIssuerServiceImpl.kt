package com.mrjovanovic.threadsnexus.service.impl

import com.mrjovanovic.threadsnexus.model.Command
import com.mrjovanovic.threadsnexus.model.dto.request.CommandCreateRequest
import com.mrjovanovic.threadsnexus.model.dto.request.toEntity
import com.mrjovanovic.threadsnexus.model.enumeration.CommandType
import com.mrjovanovic.threadsnexus.repository.CommandRepository
import com.mrjovanovic.threadsnexus.repository.DeviceRepository
import com.mrjovanovic.threadsnexus.service.CommandIssuerService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CommandIssuerServiceImpl(
    private val commandRepository: CommandRepository,
    private val deviceRepository: DeviceRepository
) : CommandIssuerService {

    override fun issueFileDownloadCommandFromOneToManyDevices(
        fileUploaderDeviceId: String,
        fileRecipientDeviceIds: List<String>
    ) {
        for (fileRecipientDeviceId in fileRecipientDeviceIds) {
            val metadata = hashMapOf("fileUploaderDeviceId" to fileUploaderDeviceId)
            deviceRepository.findById(fileRecipientDeviceId).map {
                val downloadFileCommand = Command(null, CommandType.DOWNLOAD_PENDING_FILE_FROM_CONTEXT, it, metadata)
                publishCommand(downloadFileCommand)
            }.subscribe()
        }
    }

    override fun issueFileDownloadCommandToEntireDeviceGroup(fileUploaderDeviceId: String, deviceGroupId: String) {
        val metadata = hashMapOf("fileUploaderDeviceId" to fileUploaderDeviceId)
        deviceRepository.findDevicesByGroupId(deviceGroupId)
            .filter { device -> device.id != fileUploaderDeviceId }
            .map {
                val downloadFileCommand = Command(null, CommandType.DOWNLOAD_PENDING_FILE_FROM_CONTEXT, it, metadata)
                publishCommand(downloadFileCommand)
            }.subscribe()
    }

    override fun publishCommand(command: Command) {
        commandRepository.save(command).subscribe()
    }

    override fun publishCommand(commandCreateRequest: Mono<CommandCreateRequest>) =
        commandCreateRequest.toEntity().flatMap(commandRepository::save)
}
