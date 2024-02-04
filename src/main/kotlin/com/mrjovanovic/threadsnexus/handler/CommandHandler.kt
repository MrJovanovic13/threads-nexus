package com.mrjovanovic.threadsnexus.handler

import com.mrjovanovic.threadsnexus.model.Command
import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.enumeration.CommandType
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceStatus
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceType
import com.mrjovanovic.threadsnexus.repository.CommandRepository
import com.mrjovanovic.threadsnexus.repository.DeviceRepository
import com.mrjovanovic.threadsnexus.service.DeviceEventsService
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class CommandHandler(
    private val commandRepository: CommandRepository,
    private val valueFlux: Flux<Command>,
    private val deviceRepository: DeviceRepository,
    private val deviceEventsService: DeviceEventsService
) {

    fun publishCommand(req: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .json()
            .body(
                commandRepository.saveAll(
                    req.bodyToMono(Command::class.java)
                ),
                Command::class.java
            )

    fun streamCommands(req: ServerRequest): Mono<ServerResponse> {
        val device = Device("BACKEND_SERVER", "BACKEND_SERVER", DeviceType.UNKNOWN, DeviceStatus.ONLINE, null, null)
        val subscriberDeviceId = req.pathVariable("deviceId")

        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(
                Flux.merge(
                    valueFlux.filter { it.device.id.equals(subscriberDeviceId) }
                        .doFinally {
                            deviceRepository.findById(subscriberDeviceId).map { device ->
                                device.status = DeviceStatus.OFFLINE
                                deviceRepository.save(device).subscribe()
                                deviceEventsService.postDeviceOnlineStatusChangeEvent(device, device.status)
                            }.subscribe()
                        },
                    Flux.interval(Duration.ofSeconds(5))
                        .map { Command(null, CommandType.HEARTBEAT, device) }
                ).map { e ->
                    ServerSentEvent.builder(e)
                        .build()
                }
            )
    }
}
