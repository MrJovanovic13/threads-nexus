package com.mrjovanovic.threadsnexus.model

import com.mrjovanovic.threadsnexus.model.enumeration.DeviceStatus
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.net.InetAddress

@Document(collection = "Devices")
data class Device(

    @Id
    var id: String,
    var name: String,
    var type: DeviceType,
    var status: DeviceStatus? = null,
    var ip: InetAddress? = null,
    var groupId: String? = null
)
