package com.mrjovanovic.threadsnexus.service

import com.mrjovanovic.threadsnexus.model.Command
import com.mrjovanovic.threadsnexus.model.dto.request.CommandCreateRequest
import reactor.core.publisher.Mono

interface CommandIssuerService {

    fun issueFileDownloadCommandFromOneToManyDevices(fileUploaderDeviceId: String, fileRecipientDeviceIds: List<String>)

    fun issueFileDownloadCommandToEntireDeviceGroup(fileUploaderDeviceId: String, deviceGroupId: String)

    fun publishCommand(command: Command)

    fun publishCommand(command: Mono<CommandCreateRequest>): Mono<Command>
}