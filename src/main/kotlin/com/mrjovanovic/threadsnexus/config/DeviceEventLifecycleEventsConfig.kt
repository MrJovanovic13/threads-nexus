package com.mrjovanovic.threadsnexus.config

import com.mrjovanovic.threadsnexus.model.DeviceEvent
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent
import reactor.core.publisher.Sinks

@Configuration
class DeviceEventLifecycleEventsConfig(private val sink: Sinks.Many<DeviceEvent>) :
    AbstractMongoEventListener<DeviceEvent>() {

    override fun onAfterSave(event: AfterSaveEvent<DeviceEvent>) {
        sink.tryEmitNext(event.source)
        super.onAfterSave(event)
    }
}