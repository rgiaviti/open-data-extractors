package com.gh.rgiaviti.opendata.extractors.data.censo.municipios

import com.gh.rgiaviti.opendata.extractors.common.services.FileService
import com.gh.rgiaviti.opendata.extractors.common.services.JSONService
import com.gh.rgiaviti.opendata.extractors.core.AppProperties
import org.springframework.stereotype.Service

@Service
class MunicipioService(
        val appProperties: AppProperties,
        val municipioProperties: MunicipioProperties,
        val jsonService: JSONService,
        val fileService: FileService
) {
}