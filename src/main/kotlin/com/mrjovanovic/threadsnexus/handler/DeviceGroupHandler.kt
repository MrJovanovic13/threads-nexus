package com.mrjovanovic.threadsnexus.handler

import com.mrjovanovic.threadsnexus.model.dto.request.DeviceGroupCreateRequest
import com.mrjovanovic.threadsnexus.model.dto.response.toDeviceGroupResponse
import com.mrjovanovic.threadsnexus.model.dto.response.toDevicesResponse
import com.mrjovanovic.threadsnexus.service.DeviceGroupService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class DeviceGroupHandler(private val deviceGroupService: DeviceGroupService) {

    fun saveDeviceGroup(req: ServerRequest): Mono<ServerResponse> {
        val deviceGroupCreateRequest = req.bodyToMono(DeviceGroupCreateRequest::class.java)
        val deviceGroup = deviceGroupService.saveDeviceGroup(deviceGroupCreateRequest).toDeviceGroupResponse()

        return ServerResponse.ok()
            .json()
            .body(deviceGroup)
    }

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val deviceGroupId = req.pathVariable("deviceGroupId").toMono()
        val deviceGroup = deviceGroupService.findDeviceGroupById(deviceGroupId)

        return ServerResponse.ok()
            .json()
            .body(deviceGroup.toDeviceGroupResponse())
    }

    fun findAll(req: ServerRequest): Mono<ServerResponse> {
        val devices = deviceGroupService.findDevices()

        return ServerResponse.ok()
            .json()
            .body(devices.toDevicesResponse())
    }
}
