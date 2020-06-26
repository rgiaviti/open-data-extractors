package com.gh.rgiaviti.ods.services

import com.gh.rgiaviti.ods.domains.MetaInfo
import com.gh.rgiaviti.ods.services.ConfigService.Key.METAINFO_FONTES
import com.gh.rgiaviti.ods.services.ConfigService.Key.METAINFO_REFERENCIA
import java.time.LocalDate

object MetaInfoService {

    private const val STATUS = "ATIVO"

    fun metaInfo(contagem: Int): MetaInfo {
        return MetaInfo(
                STATUS,
                LocalDate.now(),
                dataReferencia(),
                version(),
                contagem,
                site(),
                fontes()
        )
    }

    private fun dataReferencia(): LocalDate {
        return ConfigService.getDate(METAINFO_REFERENCIA)
    }

    private fun fontes(): List<String> {
        return ConfigService.getArray(METAINFO_FONTES)
    }

    private fun version(): String {
        return ConfigService.getConfig(ConfigService.Key.METAINFO_VERSION)
    }

    private fun site(): String {
        return ConfigService.getConfig(ConfigService.Key.METAINFO_SITE)
    }
}