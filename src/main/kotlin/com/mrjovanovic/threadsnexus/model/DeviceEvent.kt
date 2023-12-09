package `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.model

import `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.model.enumeration.Severity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "DeviceEvents")
data class DeviceEvent(

    @Id
    val id: String? = null,
    val info: String?,
    val timestamp: Instant,
    val severity: Severity?,
    val device: Device
)
