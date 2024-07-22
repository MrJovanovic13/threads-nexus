package com.mrjovanovic.threadsnexus.model.dto.response

import com.mrjovanovic.threadsnexus.model.Command
import com.mrjovanovic.threadsnexus.model.enumeration.CommandType
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

data class CommandResponse(
    val id: String?,
    val commandType: CommandType,
    val device: DeviceResponse,
    val metadata: Map<String, String>? = emptyMap()
)

fun Mono<Command>.toCommandResponse() = flatMap {
    it.toCommandResponse().toMono()
}

fun Command.toCommandResponse() = CommandResponse(
    id = this.id,
    commandType = this.commandType,
    device = this.device.toDeviceResponse(),
    metadata = this.metadata
)
