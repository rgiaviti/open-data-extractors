package com.gh.rgiaviti.opendata.extractors.data.censo.ufs

import com.gh.rgiaviti.opendata.extractors.common.services.FileService
import com.gh.rgiaviti.opendata.extractors.infra.AppProperties
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class UFService(
        val appProperties: AppProperties,
        val ufProperties: UFProperties,
        val fileService: FileService
) {
    companion object {
        private val log by lazy { KotlinLogging.logger {} }
    }
}