package com.mrjovanovic.threadsnexus.controller

import com.mrjovanovic.threadsnexus.handler.CommandHandler
import com.mrjovanovic.threadsnexus.handler.DeviceEventHandler
import com.mrjovanovic.threadsnexus.handler.DeviceHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class MainController {

    @Bean
    fun mainRouter(
        @Autowired deviceEventHandler: DeviceEventHandler,
        @Autowired deviceHandler: DeviceHandler,
        @Autowired commandHandler: CommandHandler
    ) = router {

        path("/api/events")
            .nest {
                GET("/stream").invoke(deviceEventHandler::streamEvents)
                GET("/{id}").invoke(deviceEventHandler::findById)
                GET("").invoke(deviceEventHandler::findAllEvents)
                POST("").invoke(deviceEventHandler::saveEvent)
            }

        path("/api/devices")
            .nest {
                GET("/{deviceId}/commands").invoke(commandHandler::streamCommands)
                GET("").invoke(deviceHandler::getAllDevices)
                POST("/{deviceId}/publish-command").invoke(commandHandler::publishCommand)
                POST("").invoke(deviceHandler::saveDevice)
            }
    }
}
