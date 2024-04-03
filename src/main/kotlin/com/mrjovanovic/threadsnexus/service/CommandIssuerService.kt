package com.mrjovanovic.threadsnexus.service

import com.mrjovanovic.threadsnexus.model.Command

interface CommandIssuerService {

    fun issueFileDownloadCommandFromOneToManyDevices(fileUploaderDeviceId: String, fileRecipientDeviceIds: List<String>)

    fun issueFileDownloadCommandToEntireDeviceGroup(fileUploaderDeviceId: String, deviceGroupId: String)

    fun issueCommand(command: Command)
}