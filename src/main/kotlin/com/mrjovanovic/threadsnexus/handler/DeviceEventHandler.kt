package com.mrjovanovic.threadsnexus.handler

import com.mrjovanovic.threadsnexus.model.DeviceEvent
import com.mrjovanovic.threadsnexus.model.dto.request.EventCreateRequest
import com.mrjovanovic.threadsnexus.model.dto.response.toEventResponse
import com.mrjovanovic.threadsnexus.model.dto.response.toEventsResponse
import com.mrjovanovic.threadsnexus.service.DeviceEventsService
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class DeviceEventHandler(
    private val deviceEventsService: DeviceEventsService,
    private val valueFlux: Flux<DeviceEvent>
) {

    fun findAllEvents(request: ServerRequest): Mono<ServerResponse> = ok()
        .json()
        .body(deviceEventsService.findEvents().toEventsResponse())

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val eventId = req.pathVariable("id").toMono()
        val event = deviceEventsService.findEventById(eventId)

        return ok()
            .json()
            .body(event)
    }

    fun saveEvent(req: ServerRequest): Mono<ServerResponse> = ok()
        .json()
        .body(
            deviceEventsService
                .saveEvent(req.bodyToMono(EventCreateRequest::class.java))
                .toEventResponse()
        )

    fun streamEvents(req: ServerRequest): Mono<ServerResponse> = ok()
        .contentType(MediaType.TEXT_EVENT_STREAM)
        .body(
            valueFlux.map { e -> ServerSentEvent.builder(e).build() }
        )
}
