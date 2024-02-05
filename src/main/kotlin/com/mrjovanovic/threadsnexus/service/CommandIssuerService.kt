package com.mrjovanovic.threadsnexus.service

import com.mrjovanovic.threadsnexus.model.Command

interface CommandIssuerService {

    fun issueFileDownloadCommand(deviceId: String)

    fun issueCommand(command: Command)
}