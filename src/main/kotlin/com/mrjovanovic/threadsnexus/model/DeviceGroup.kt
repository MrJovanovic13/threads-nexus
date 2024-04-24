package com.mrjovanovic.threadsnexus.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "DeviceGroups")
data class DeviceGroup(

    @Id
    val id: String? = null,
    var name: String,
    val groupAdminDeviceId: String
)
