package `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.repository

import `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.model.DeviceEvent
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import java.time.Instant

interface DeviceEventRepository : ReactiveMongoRepository<DeviceEvent, String>