package com.mrjovanovic.threadsnexus.service

import com.mrjovanovic.threadsnexus.model.Command

interface CommandIssuerService {

    fun issueFileDownloadCommand(fileUploaderDeviceId: String, fileRecipientDeviceIds: List<String>)

    fun issueCommand(command: Command)
}