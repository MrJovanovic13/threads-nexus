package com.mrjovanovic.threadsnexus.model.dto.response

import com.mrjovanovic.threadsnexus.model.DeviceGroup
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toMono

data class DeviceGroupsResponse(val deviceGroups: Flux<DeviceGroupResponse>)

fun Flux<DeviceGroup>.toDevicesResponse() = DeviceGroupsResponse(
    flatMap {
        it.toMono().toDeviceGroupResponse()
    }
).toMono()