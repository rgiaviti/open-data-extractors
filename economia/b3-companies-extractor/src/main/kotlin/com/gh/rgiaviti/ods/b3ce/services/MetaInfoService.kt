package com.gh.rgiaviti.ods.b3ce.services

import com.gh.rgiaviti.ods.b3ce.domains.MetaInfo
import com.gh.rgiaviti.ods.b3ce.services.ConfigService.Key.*
import com.gh.rgiaviti.ods.b3ce.services.ConfigService.getConfig
import java.time.LocalDate

object MetaInfoService {

    private const val FONTES_DELIMITER = ";"
    private const val STATUS = "ATIVO"

    fun metaInfo(contagem: Int): MetaInfo {
        return MetaInfo(
                STATUS,
                LocalDate.now(),
                version(),
                contagem,
                site(),
                fontes()
        )
    }

    private fun fontes(): List<String> {
        val arrVersions = getConfig(METAINFO_FONTES).split(FONTES_DELIMITER)
        return arrVersions.toList()
    }

    private fun version(): String {
        return getConfig(METAINFO_VERSION)
    }

    private fun site(): String {
        return getConfig(METAINFO_SITE)
    }
}