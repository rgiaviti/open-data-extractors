package com.gh.rgiaviti.ods.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.gh.rgiaviti.ods.domains.OpenDataUF

object JSONSerialize {

    fun toJSON(openDataUF: OpenDataUF): String {
        val mapper = ObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return mapper.writeValueAsString(openDataUF)
    }
}