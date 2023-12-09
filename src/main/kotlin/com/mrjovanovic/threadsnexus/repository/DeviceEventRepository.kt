package com.mrjovanovic.threadsnexus.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import com.mrjovanovic.threadsnexus.model.DeviceEvent

interface DeviceEventRepository : ReactiveMongoRepository<DeviceEvent, String>