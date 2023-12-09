package com.mrjovanovic.threadsnexus.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceStatus
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceType

@Document(collection = "Devices")
data class Device(
    @Id
    var id: String? = null,
    var name: String?,
    var type: DeviceType,
    val status: DeviceStatus,
    // TODO Implement InetAddress Deserializer to enable it's usage here instead of String
    var ip: String
)
