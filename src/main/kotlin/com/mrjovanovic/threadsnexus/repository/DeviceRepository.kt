package com.mrjovanovic.threadsnexus.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import com.mrjovanovic.threadsnexus.model.Device

interface DeviceRepository : ReactiveMongoRepository<Device, String>