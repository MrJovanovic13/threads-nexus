package `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.repository

import `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus.model.Device
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface DeviceRepository : ReactiveMongoRepository<Device, String>