package com.mrjovanovic.threadsnexus.handler

import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.repository.DeviceRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono

@Component
class DeviceHandler(private val deviceRepository: DeviceRepository) {

    fun saveDevice(req: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .json()
            .body(deviceRepository.saveAll(req.bodyToMono(Device::class.java)), Device::class.java)

    fun getDevicesByGroupId(req: ServerRequest): Mono<ServerResponse> {
        val groupId = req.queryParam("groupId")
        val searchText = req.queryParam("searchText").orElse("")
        val devices = groupId
            .map {
                deviceRepository.findDevicesByGroupIdAndNameContainingIgnoreCase(it, searchText)
            }
            .orElseGet { deviceRepository.findAll() }

        return ServerResponse.ok()
            .json()
            .body(devices, Device::class.java)
    }
}
