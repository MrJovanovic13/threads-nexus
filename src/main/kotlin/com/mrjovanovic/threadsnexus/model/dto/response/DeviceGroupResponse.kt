package com.mrjovanovic.threadsnexus.model.dto.response

import com.mrjovanovic.threadsnexus.model.DeviceGroup
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

data class DeviceGroupResponse(
    val id: String?,
    var name: String,
    val groupAdminDeviceId: String
)

fun Mono<DeviceGroup>.toDeviceGroupResponse() = flatMap {
    it.toDeviceGroupResponse().toMono()
}

fun DeviceGroup.toDeviceGroupResponse() = DeviceGroupResponse(
    id = this.id,
    name = this.name,
    groupAdminDeviceId = this.groupAdminDeviceId
)
