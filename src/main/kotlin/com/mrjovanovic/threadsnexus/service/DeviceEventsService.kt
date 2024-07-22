package com.mrjovanovic.threadsnexus.service

import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.DeviceEvent
import com.mrjovanovic.threadsnexus.model.dto.request.EventCreateRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DeviceEventsService {

    fun postDeviceEvent(deviceEvent: DeviceEvent)

    fun postCurrentDeviceStatusEvent(device: Device)

    fun findEvents(): Flux<DeviceEvent>

    fun findEventById(eventId: Mono<String>): Mono<DeviceEvent>

    fun saveEvent(eventCreateRequest: Mono<EventCreateRequest>): Mono<DeviceEvent>
}
