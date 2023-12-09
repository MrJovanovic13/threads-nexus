package `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.model

import `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.model.enumeration.DeviceStatus
import `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.model.enumeration.DeviceType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

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
