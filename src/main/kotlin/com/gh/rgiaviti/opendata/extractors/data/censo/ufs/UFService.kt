package com.gh.rgiaviti.opendata.extractors.data.censo.ufs

import com.gh.rgiaviti.opendata.extractors.common.services.FileService
import com.gh.rgiaviti.opendata.extractors.common.services.JSONService
import com.gh.rgiaviti.opendata.extractors.core.AppProperties
import org.springframework.stereotype.Service

@Service
class UFService(
        val appProperties: AppProperties,
        val ufProperties: UFProperties,
        val jsonService: JSONService,
        val fileService: FileService
) {
}