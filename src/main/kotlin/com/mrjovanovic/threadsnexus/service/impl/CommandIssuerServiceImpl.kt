package com.mrjovanovic.threadsnexus.service.impl

import com.mrjovanovic.threadsnexus.model.Command
import com.mrjovanovic.threadsnexus.model.enumeration.CommandType
import com.mrjovanovic.threadsnexus.repository.CommandRepository
import com.mrjovanovic.threadsnexus.repository.DeviceRepository
import com.mrjovanovic.threadsnexus.service.CommandIssuerService
import org.springframework.stereotype.Service

@Service
class CommandIssuerServiceImpl(
    private val commandRepository: CommandRepository,
    private val deviceRepository: DeviceRepository
) : CommandIssuerService {

    override fun issueFileDownloadCommand(fileUploaderDeviceId: String, fileRecipientDeviceIds: List<String>) {
        for (fileRecipientDeviceId in fileRecipientDeviceIds) {
            val metadata = hashMapOf("fileUploaderDeviceId" to fileUploaderDeviceId)
            deviceRepository.findById(fileRecipientDeviceId).map {
                val downloadFileCommand = Command(null, CommandType.DOWNLOAD_PENDING_FILE_FROM_CONTEXT, it, metadata)
                issueCommand(downloadFileCommand)
            }.subscribe()
        }
    }

    override fun issueCommand(command: Command) {
        commandRepository.save(command).subscribe()
    }
}
