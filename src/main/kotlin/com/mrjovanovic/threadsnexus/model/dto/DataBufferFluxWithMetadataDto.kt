package com.mrjovanovic.threadsnexus.model.dto

import org.springframework.core.io.buffer.DataBuffer
import reactor.core.publisher.Flux

data class DataBufferFluxWithMetadataDto(
    val dataBufferFlux: Flux<DataBuffer>,
    val metadata: Map<String, String>?
)
