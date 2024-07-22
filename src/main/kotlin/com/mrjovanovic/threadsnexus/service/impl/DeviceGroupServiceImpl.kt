package com.mrjovanovic.threadsnexus.service.impl

import com.mrjovanovic.threadsnexus.model.DeviceGroup
import com.mrjovanovic.threadsnexus.model.dto.request.DeviceGroupCreateRequest
import com.mrjovanovic.threadsnexus.model.dto.request.toEntity
import com.mrjovanovic.threadsnexus.repository.DeviceGroupRepository
import com.mrjovanovic.threadsnexus.service.DeviceGroupService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class DeviceGroupServiceImpl(private val deviceGroupRepository: DeviceGroupRepository) : DeviceGroupService {

    override fun saveDeviceGroup(deviceGroupCreateRequest: Mono<DeviceGroupCreateRequest>): Mono<DeviceGroup> =
        deviceGroupCreateRequest.toEntity().flatMap(deviceGroupRepository::save)

    override fun findDeviceGroupById(deviceGroupId: Mono<String>): Mono<DeviceGroup> =
        deviceGroupRepository.findById(deviceGroupId)

    override fun findDevices(): Flux<DeviceGroup> = deviceGroupRepository.findAll()
}
