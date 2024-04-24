package com.mrjovanovic.threadsnexus.handler

import com.mrjovanovic.threadsnexus.model.DeviceGroup
import com.mrjovanovic.threadsnexus.repository.DeviceGroupRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono

@Component
class DeviceGroupHandler(private val deviceGroupRepository: DeviceGroupRepository) {

    fun saveDeviceGroup(req: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .json()
            .body(deviceGroupRepository.saveAll(req.bodyToMono(DeviceGroup::class.java)), DeviceGroup::class.java)

    fun findById(req: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .json()
            .body(deviceGroupRepository.findById(req.pathVariable("deviceGroupId")), DeviceGroup::class.java)
}
