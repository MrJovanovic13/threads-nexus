package kotlin.com.mrjovanovic.threadsnexus.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono
import kotlin.com.mrjovanovic.threadsnexus.model.DeviceEvent
import kotlin.com.mrjovanovic.threadsnexus.repository.DeviceEventRepository
import kotlin.com.mrjovanovic.threadsnexus.repository.DeviceRepository

@Component
class DeviceEventHandler(
    private val repository: DeviceEventRepository,
    private val deviceRepository: DeviceRepository
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

                ), DeviceEvent::class.java
            )
}
