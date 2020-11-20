package com.gh.rgiaviti.opendata.extractors.common.services

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class JSONService(private val mapper: ObjectMapper) {

    fun toJSON(data: Any): String {
        return mapper.writeValueAsString(data)
    }
}