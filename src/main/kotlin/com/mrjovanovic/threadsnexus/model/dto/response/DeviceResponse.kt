package com.mrjovanovic.threadsnexus.model.dto.response

import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceStatus
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceType
import reactor.core.publisher.Mono
import java.net.InetAddress

data class DeviceResponse(
    val id: String,
    val name: String,
    val type: DeviceType,
    val status: DeviceStatus?,
    val ip: InetAddress?,
    val groupId: String?
)

fun Mono<Device>.toDeviceResponse() = flatMap {
    Mono.just(it.toDeviceResponse())
}

fun Device.toDeviceResponse(): DeviceResponse =
    DeviceResponse(
        id = this.id,
        name = this.name,
        type = this.type,
        status = this.status,
        ip = this.ip,
        groupId = this.groupId
    )