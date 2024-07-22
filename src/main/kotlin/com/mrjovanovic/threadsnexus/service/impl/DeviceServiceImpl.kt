package com.mrjovanovic.threadsnexus.service.impl

import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.dto.request.DeviceCreateRequest
import com.mrjovanovic.threadsnexus.model.dto.request.DevicesGetRequest
import com.mrjovanovic.threadsnexus.model.dto.request.toEntity
import com.mrjovanovic.threadsnexus.repository.DeviceRepository
import com.mrjovanovic.threadsnexus.service.DeviceService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class DeviceServiceImpl(private val deviceRepository: DeviceRepository) : DeviceService {

    override fun saveDevice(deviceCreateRequest: Mono<DeviceCreateRequest>): Mono<Device> =
        deviceCreateRequest.toEntity().flatMap(deviceRepository::save)

    override fun getDevices(devicesGetRequest: Mono<DevicesGetRequest>): Flux<Device> {
        return devicesGetRequest.flatMapMany {
            it.groupId?: return@flatMapMany deviceRepository.findDevicesByNameContainingIgnoreCase(it.searchText)

            deviceRepository.findDevicesByGroupIdAndNameContainingIgnoreCase(it.groupId, it.searchText)
        }
    }
}
