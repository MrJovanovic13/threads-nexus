package com.mrjovanovic.threadsnexus.handler

import com.mrjovanovic.threadsnexus.model.Command
import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.dto.request.CommandCreateRequest
import com.mrjovanovic.threadsnexus.model.dto.response.toCommandResponse
import com.mrjovanovic.threadsnexus.model.enumeration.BackendServerDeviceIdentifier
import com.mrjovanovic.threadsnexus.model.enumeration.CommandType
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceStatus
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceType
import com.mrjovanovic.threadsnexus.repository.DeviceRepository
import com.mrjovanovic.threadsnexus.service.CommandIssuerService
import com.mrjovanovic.threadsnexus.service.DeviceEventsService
import org.springframework.beans.factory.annotation.Value
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
    private val commandService: CommandIssuerService,
    private val valueFlux: Flux<Command>,
    private val deviceRepository: DeviceRepository,
    private val deviceEventsService: DeviceEventsService,
    @Value("\${sse.heartbeat.frequency.seconds}")
    private var heartbeatFrequencySeconds: Long
) {

    fun publishCommand(req: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .json()
            .body(
                commandService.publishCommand(req.bodyToMono(CommandCreateRequest::class.java))
                    .toCommandResponse()
            )

    fun streamCommands(req: ServerRequest): Mono<ServerResponse> {
        val backendDevice = Device(
            BackendServerDeviceIdentifier.ID.value,
            BackendServerDeviceIdentifier.NAME.value,
            DeviceType.UNKNOWN,
            DeviceStatus.ONLINE,
            null,
            null
        )
        val subscriberDeviceId = req.pathVariable("deviceId")

        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(Flux.merge(
                valueFlux
                    .filter { it.device.id == subscriberDeviceId }
                    .doOnSubscribe { sendDeviceStatusUpdateEvent(subscriberDeviceId, DeviceStatus.ONLINE) }
                    .doFinally { sendDeviceStatusUpdateEvent(subscriberDeviceId, DeviceStatus.OFFLINE) },
                emitHeartbeatCommandServerSentEvents(backendDevice)
            ).map { e ->
                ServerSentEvent.builder(e).build()
            })
    }

    private fun sendDeviceStatusUpdateEvent(subscriberDeviceId: String, deviceStatus: DeviceStatus) {
        deviceRepository.findById(subscriberDeviceId)
            .flatMap { device ->
                device.status = deviceStatus
                Mono.just(device)
            }
            .flatMap { device -> deviceRepository.save(device) }
            .flatMap { device ->
                deviceEventsService.postCurrentDeviceStatusEvent(device)
                Mono.just(device)
            }
            .subscribe()
    }

    private fun emitHeartbeatCommandServerSentEvents(device: Device) =
        Flux.interval(Duration.ofSeconds(heartbeatFrequencySeconds))
            .map { Command(null, CommandType.HEARTBEAT, device) }
}
