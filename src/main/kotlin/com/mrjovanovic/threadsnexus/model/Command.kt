package com.mrjovanovic.threadsnexus.model

import com.mrjovanovic.threadsnexus.model.enumeration.CommandType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Commands")
data class Command(

    @Id
    var id: String? = null,
    var commandType: CommandType,
    var device: Device,
    var metadata: Map<String, String>? = emptyMap()
)
