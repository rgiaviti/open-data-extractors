package com.gh.rgiaviti.ods.b3ce.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.gh.rgiaviti.ods.b3ce.domains.Company
import com.gh.rgiaviti.ods.b3ce.domains.CompanyList
import com.gh.rgiaviti.ods.b3ce.domains.MetaInfo

object JSONService {

    private val mapper = ObjectMapper()

    init {
        enableModules()
        enableOrDisableFeatures()
    }

    fun toJson(metainfo: MetaInfo, companies: List<Company>): String {
        return toJson(CompanyList(metainfo, companies))
    }

    private fun toJson(obj: Any): String {
        return mapper.writeValueAsString(obj)
    }

    private fun enableModules() {
        mapper.registerModule(KotlinModule())
        mapper.registerModule(JavaTimeModule())
    }

    private fun enableOrDisableFeatures() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
}