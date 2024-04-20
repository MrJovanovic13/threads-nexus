package com.mrjovanovic.threadsnexus.model

import com.mrjovanovic.threadsnexus.model.enumeration.Severity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "DeviceEvents")
data class DeviceEvent(

    @Id
    val id: String? = null,
    val title: String,
    val info: String?,
    val timestamp: Instant,
    val severity: Severity?,
    val device: Device
)
