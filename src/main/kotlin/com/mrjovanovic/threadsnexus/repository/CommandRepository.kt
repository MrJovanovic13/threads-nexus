package com.mrjovanovic.threadsnexus.repository

import com.mrjovanovic.threadsnexus.model.Command
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CommandRepository : ReactiveMongoRepository<Command, String>