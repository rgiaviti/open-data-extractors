package com.gh.rgiaviti.opendata.extractors.data.censo.ufs

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class UFProperties(
        @Value("\${app.configs.municipios.metainfos.site}")
        val site: String,

        @Value("\${app.configs.municipios.metainfos.fontes}")
        val fontes: Array<String>,

        @Value("\${app.configs.municipios.metainfos.data-referencia}")
        val dataReferncia: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UFProperties

        if (site != other.site) return false
        if (!fontes.contentEquals(other.fontes)) return false
        if (dataReferncia != other.dataReferncia) return false

        return true
    }

    override fun hashCode(): Int {
        var result = site.hashCode()
        result = 31 * result + fontes.contentHashCode()
        result = 31 * result + dataReferncia.hashCode()
        return result
    }
}
