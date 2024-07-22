package com.mrjovanovic.threadsnexus.service

import com.mrjovanovic.threadsnexus.model.DeviceGroup
import com.mrjovanovic.threadsnexus.model.dto.request.DeviceGroupCreateRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DeviceGroupService {

    fun saveDeviceGroup(deviceGroupCreateRequest: Mono<DeviceGroupCreateRequest>): Mono<DeviceGroup>

    fun findDeviceGroupById(deviceGroupId: Mono<String>): Mono<DeviceGroup>

    fun findDevices(): Flux<DeviceGroup>
}
