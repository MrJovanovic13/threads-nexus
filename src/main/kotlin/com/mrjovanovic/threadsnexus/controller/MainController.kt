package com.mrjovanovic.threadsnexus.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router
import com.mrjovanovic.threadsnexus.handler.DeviceEventHandler
import com.mrjovanovic.threadsnexus.handler.DeviceHandler

@Configuration
class MainController {

    @Bean
    fun mainRouter(
        @Autowired deviceEventHandler: DeviceEventHandler,
        @Autowired deviceHandler: DeviceHandler
    ) = router {
        accept(APPLICATION_JSON)
        path("/api/events")
            .nest {
                GET("").invoke(deviceEventHandler::findAllEvents)
                GET("/{id}").invoke(deviceEventHandler::findById)
                POST("").invoke(deviceEventHandler::saveEvent)
            }
        path("/api/devices")
            .nest {
                POST("").invoke(deviceHandler::saveDevice)
            }
    }
}
