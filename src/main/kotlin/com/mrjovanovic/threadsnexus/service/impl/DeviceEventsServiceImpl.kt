package com.mrjovanovic.threadsnexus.service.impl

import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.DeviceEvent
import com.mrjovanovic.threadsnexus.model.dto.request.EventCreateRequest
import com.mrjovanovic.threadsnexus.model.dto.request.toEntity
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceEventType
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceStatus.ONLINE
import com.mrjovanovic.threadsnexus.repository.DeviceEventRepository
import com.mrjovanovic.threadsnexus.service.DeviceEventsService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant

@Service
class DeviceEventsServiceImpl(private val deviceEventRepository: DeviceEventRepository) : DeviceEventsService {

    override fun postCurrentDeviceStatusEvent(device: Device) {
        val deviceEventType: DeviceEventType =
            if (device.status == ONLINE) DeviceEventType.DEVICE_ONLINE
            else DeviceEventType.DEVICE_OFFLINE

        val deviceEvent =
            DeviceEvent(null, deviceEventType.title, null, Instant.now(), deviceEventType.severity, device)

        postDeviceEvent(deviceEvent)
    }

    override fun postDeviceEvent(deviceEvent: DeviceEvent) {
        deviceEventRepository.save(deviceEvent).subscribe()
    }

    override fun findEvents(): Flux<DeviceEvent> = deviceEventRepository.findAll()

    override fun findEventById(eventId: Mono<String>): Mono<DeviceEvent> = deviceEventRepository.findById(eventId)

    override fun saveEvent(eventCreateRequest: Mono<EventCreateRequest>): Mono<DeviceEvent> =
        eventCreateRequest.toEntity().flatMap(deviceEventRepository::save)
}
