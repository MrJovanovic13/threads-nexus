package com.mrjovanovic.threadsnexus.config

import com.mrjovanovic.threadsnexus.model.converter.InetConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions


@Configuration
class ConvertersConfig {

    @Bean
    fun mongoCustomConversions(): MongoCustomConversions {
        return MongoCustomConversions(
            listOf<Any?>(
                InetConverter()
            )
        )
    }
}
