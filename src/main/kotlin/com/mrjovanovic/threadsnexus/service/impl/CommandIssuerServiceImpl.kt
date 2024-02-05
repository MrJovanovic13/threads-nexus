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

    override fun issueFileDownloadCommand(deviceId: String) {
        deviceRepository.findById(deviceId).map {
            val downloadFileCommand = Command(null, CommandType.DOWNLOAD_PENDING_FILE_FROM_CONTEXT, it)
            issueCommand(downloadFileCommand)
        }.subscribe()
    }

    override fun issueCommand(command: Command) {
        commandRepository.save(command).subscribe()
    }
}
