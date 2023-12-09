package kotlin.com.mrjovanovic.threadsnexus.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import kotlin.com.mrjovanovic.threadsnexus.model.Device

interface DeviceRepository : ReactiveMongoRepository<Device, String>