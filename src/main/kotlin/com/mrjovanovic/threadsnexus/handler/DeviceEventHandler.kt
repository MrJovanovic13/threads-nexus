package com.mrjovanovic.threadsnexus.handler

import com.mrjovanovic.threadsnexus.model.DeviceEvent
import com.mrjovanovic.threadsnexus.repository.DeviceEventRepository
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

@Component
class DeviceEventHandler(
    private val repository: DeviceEventRepository,
    private val valueFlux: Flux<DeviceEvent>
) {

    fun findAllEvents(request: ServerRequest): Mono<ServerResponse> =
        ok()
            .json()
            .body(repository.findAll(), DeviceEvent::class.java)

    fun findById(req: ServerRequest): Mono<ServerResponse> =
        ok()
            .json()
            .body(repository.findById(req.pathVariable("id")), DeviceEvent::class.java)

    fun saveEvent(req: ServerRequest): Mono<ServerResponse> =
        ok()
            .json()
            .body(
                repository.saveAll(
                    req.bodyToMono(DeviceEvent::class.java)
                ),
                DeviceEvent::class.java
            )

    fun streamEvents(req: ServerRequest): Mono<ServerResponse> {
        return ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(
                valueFlux.map { e -> ServerSentEvent.builder(e).build() }
            )
    }
}
