package com.mrjovanovic.threadsnexus.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono
import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.repository.DeviceRepository

@Component
class DeviceHandler(private val deviceRepository: DeviceRepository) {

    fun saveDevice(req: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .json()
            .body(deviceRepository.saveAll(req.bodyToMono(Device::class.java)), Device::class.java)

    fun getAllDevices(req: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .json()
            .body(deviceRepository.findAll(), Device::class.java)
}
