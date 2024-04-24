package com.mrjovanovic.threadsnexus.repository

import com.mrjovanovic.threadsnexus.model.DeviceGroup
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface DeviceGroupRepository : ReactiveMongoRepository<DeviceGroup, String>
