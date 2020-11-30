package com.gh.rgiaviti.opendata.extractors.data.censo.municipios

import com.gh.rgiaviti.opendata.extractors.common.services.FileService
import com.gh.rgiaviti.opendata.extractors.infra.AppProperties
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class MunicipioService(
        val appProperties: AppProperties,
        val municipioProperties: MunicipioProperties,
        val fileService: FileService
) {
    companion object {
        private val log by lazy { KotlinLogging.logger {} }
    }
}