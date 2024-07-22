package com.mrjovanovic.threadsnexus.model.dto.request

import com.mrjovanovic.threadsnexus.model.Command
import com.mrjovanovic.threadsnexus.model.enumeration.CommandType
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

data class CommandCreateRequest(
    val commandType: CommandType,
    val device: DeviceCreateRequest,
    val metadata: Map<String, String>? = emptyMap()
)

fun Mono<CommandCreateRequest>.toEntity() = flatMap {
    it.toEntity().toMono()
}

fun CommandCreateRequest.toEntity() =
    Command(
        commandType = this.commandType,
        device = this.device.toEntity(),
        metadata = this.metadata
    )
