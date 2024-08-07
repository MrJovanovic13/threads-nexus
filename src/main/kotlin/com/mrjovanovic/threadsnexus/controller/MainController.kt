package com.mrjovanovic.threadsnexus.controller

import com.mrjovanovic.threadsnexus.handler.CommandHandler
import com.mrjovanovic.threadsnexus.handler.DeviceEventHandler
import com.mrjovanovic.threadsnexus.handler.DeviceGroupHandler
import com.mrjovanovic.threadsnexus.handler.DeviceHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class MainController(
    private val deviceEventHandler: DeviceEventHandler,
    private val deviceHandler: DeviceHandler,
    private val commandHandler: CommandHandler,
    private val deviceGroupHandler: DeviceGroupHandler
) {

    @Bean
    fun mainRouter() = router {

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
                GET("").invoke(deviceHandler::getDevicesByGroupId)
                POST("/{deviceId}/publish-command").invoke(commandHandler::publishCommand)
                POST("").invoke(deviceHandler::saveDevice)
            }

        path("/api/device-groups")
            .nest {
                GET("/{deviceGroupId}").invoke(deviceGroupHandler::findById)
                GET("").invoke(deviceGroupHandler::findAll)
                POST("").invoke(deviceGroupHandler::saveDeviceGroup)
            }
    }
}
