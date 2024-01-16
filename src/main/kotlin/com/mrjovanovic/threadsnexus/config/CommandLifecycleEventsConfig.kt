package com.mrjovanovic.threadsnexus.config

import com.mrjovanovic.threadsnexus.model.Command
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent
import reactor.core.publisher.Sinks

@Configuration
class CommandLifecycleEventsConfig(private val sink: Sinks.Many<Command>) : AbstractMongoEventListener<Command>() {

    override fun onAfterSave(event: AfterSaveEvent<Command>) {
        sink.tryEmitNext(event.source)
        super.onAfterSave(event)
    }
}
