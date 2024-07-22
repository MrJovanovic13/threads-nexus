package com.mrjovanovic.threadsnexus.model.dto.response

import com.mrjovanovic.threadsnexus.model.Device
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toMono

data class DevicesResponse(val deviceResponse: Flux<DeviceResponse>)

fun Flux<Device>.toDevicesResponse() = flatMap {
    it.toMono().toDeviceResponse()
}
