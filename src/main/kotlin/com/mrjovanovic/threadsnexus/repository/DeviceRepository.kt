package com.mrjovanovic.threadsnexus.repository

import com.mrjovanovic.threadsnexus.model.Device
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface DeviceRepository : ReactiveMongoRepository<Device, String> {

    fun findDevicesByGroupId(groupId: String): Flux<Device>
}
