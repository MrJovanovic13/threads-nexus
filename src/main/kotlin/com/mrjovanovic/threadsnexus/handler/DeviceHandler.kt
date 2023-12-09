package `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.handler

import `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.model.Device
import `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.repository.DeviceRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono

@Component
class DeviceHandler(private val deviceRepository: DeviceRepository) {

    fun saveDevice(req: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .json()
            .body(deviceRepository.saveAll(req.bodyToMono(Device::class.java)), Device::class.java)
}
