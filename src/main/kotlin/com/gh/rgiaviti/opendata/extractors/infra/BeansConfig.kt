package com.gh.rgiaviti.opendata.extractors.infra

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeansConfig {

    @Bean
    fun mapper(): ObjectMapper {
        val mapper = ObjectMapper()

        mapper.registerModule(KotlinModule())
        mapper.registerModule(JavaTimeModule())

        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        return mapper
    }
}