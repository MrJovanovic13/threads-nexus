package com.mrjovanovic.threadsnexus.model.dto.response

import com.mrjovanovic.threadsnexus.model.DeviceEvent
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toMono

data class EventsResponse(val events: Flux<EventResponse>)

fun Flux<DeviceEvent>.toEventsResponse() = EventsResponse(
    flatMap {
        it.toMono().toEventResponse()
    }
).toMono()
