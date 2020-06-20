package com.gh.rgiaviti.ods.b3ce.services

import com.gh.rgiaviti.ods.b3ce.configs.Config.Key.*
import com.gh.rgiaviti.ods.b3ce.configs.Config.getConfig
import com.gh.rgiaviti.ods.b3ce.domains.MetaInfo
import java.time.LocalDate

object MetaInfoService {

    private const val FONTES_DELIMITER = ";"
    private const val STATUS = "ATIVO"

    fun metaInfo(): MetaInfo {
        return MetaInfo(
            STATUS,
            LocalDate.now(),
            version(),
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