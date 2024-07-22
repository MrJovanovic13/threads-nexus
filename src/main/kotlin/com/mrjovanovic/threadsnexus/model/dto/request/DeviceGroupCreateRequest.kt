package com.mrjovanovic.threadsnexus.model.dto.request

import com.mrjovanovic.threadsnexus.model.DeviceGroup
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

data class DeviceGroupCreateRequest(
    val name: String,
    val groupAdminDeviceId: String
)

fun Mono<DeviceGroupCreateRequest>.toEntity() = flatMap {
    it.toEntity().toMono()
}

fun DeviceGroupCreateRequest.toEntity() =
    DeviceGroup(
        id = null,
        name = this.name,
        groupAdminDeviceId = this.groupAdminDeviceId
    )
