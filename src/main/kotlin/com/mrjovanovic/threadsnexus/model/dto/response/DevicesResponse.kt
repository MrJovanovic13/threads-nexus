package com.mrjovanovic.threadsnexus.model.dto.response

import com.mrjovanovic.threadsnexus.model.Device
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toMono

data class DevicesResponse(val deviceResponses: Flux<DeviceResponse>)

fun Flux<Device>.toDevicesResponse() = DevicesResponse(
    flatMap { it.toMono().toDeviceResponse() }
).toMono()
