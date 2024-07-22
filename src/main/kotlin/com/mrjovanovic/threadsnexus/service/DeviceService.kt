package com.mrjovanovic.threadsnexus.service

import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.dto.request.DeviceCreateRequest
import com.mrjovanovic.threadsnexus.model.dto.request.DevicesGetRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DeviceService {

    fun saveDevice(deviceCreateRequest: Mono<DeviceCreateRequest>): Mono<Device>

    fun getDevices(devicesGetRequest: Mono<DevicesGetRequest>): Flux<Device>
}
