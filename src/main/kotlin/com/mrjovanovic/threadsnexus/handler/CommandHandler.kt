package com.mrjovanovic.threadsnexus.handler

import com.mrjovanovic.threadsnexus.model.Command
import com.mrjovanovic.threadsnexus.repository.CommandRepository
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class CommandHandler(
    private val commandRepository: CommandRepository,
    private val valueFlux: Flux<Command>
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
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(
                valueFlux
                    .filter { it.device.id.equals(req.pathVariable("deviceId")) }
                    .map { e ->
                        ServerSentEvent.builder(e)
                            .build()
                    }
            )
    }
}
