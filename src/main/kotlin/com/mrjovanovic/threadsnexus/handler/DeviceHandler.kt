package com.mrjovanovic.threadsnexus.handler

import com.mrjovanovic.threadsnexus.model.dto.request.DeviceCreateRequest
import com.mrjovanovic.threadsnexus.model.dto.request.DevicesGetRequest
import com.mrjovanovic.threadsnexus.model.dto.response.toDeviceResponse
import com.mrjovanovic.threadsnexus.model.dto.response.toDevicesResponse
import com.mrjovanovic.threadsnexus.service.DeviceService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono
import kotlin.jvm.optionals.getOrNull

@Component
class DeviceHandler(private val deviceService: DeviceService) {

    fun saveDevice(req: ServerRequest): Mono<ServerResponse> {
        val deviceCreateRequest = req.bodyToMono(DeviceCreateRequest::class.java)
        val deviceResponse = deviceService.saveDevice(deviceCreateRequest).toDeviceResponse()

        return ServerResponse.ok()
            .json()
            .body(deviceResponse)
    }

    fun getDevicesByGroupId(req: ServerRequest): Mono<ServerResponse> {
        // TODO Introduce validation for not nullable values(groupId)
        val groupId = req.queryParam("groupId").getOrNull()
        val searchText = req.queryParam("searchText").orElse("")
        val devices = deviceService.getDevices(Mono.just(DevicesGetRequest(groupId, searchText)))
            .toDevicesResponse()

        return ServerResponse.ok()
            .json()
            .body(devices)
    }
}
