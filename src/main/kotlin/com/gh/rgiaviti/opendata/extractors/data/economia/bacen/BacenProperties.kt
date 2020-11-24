package com.gh.rgiaviti.opendata.extractors.data.economia.bacen

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class BacenProperties(
        @Value("\${app.configs.bacen.ipca.metainfos.fontes}")
        val ipcaFontes: Array<String>,

        @Value("\${app.configs.bacen.ipca.metainfos.site}")
        val ipcaSite: String,

        @Value("\${app.configs.bacen.inpc.metainfos.fontes}")
        val inpcFontes: Array<String>,

        @Value("\${app.configs.bacen.inpc.metainfos.site}")
        val inpcSite: String,

        @Value("\${app.configs.bacen.igpm.metainfos.fontes}")
        val igpmFontes: Array<String>,

        @Value("\${app.configs.bacen.igpm.metainfos.site}")
        val igpmSite: String,

        @Value("\${app.configs.bacen.selic.metainfos.fontes}")
        val selicFontes: Array<String>,

        @Value("\${app.configs.bacen.selic.metainfos.site}")
        val selicSite: String,

        @Value("\${app.configs.bacen.cdi.metainfos.fontes}")
        val cdiFontes: Array<String>,

        @Value("\${app.configs.bacen.cdi.metainfos.site}")
        val cdiSite: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BacenProperties

        if (!ipcaFontes.contentEquals(other.ipcaFontes)) return false
        if (ipcaSite != other.ipcaSite) return false
        if (!inpcFontes.contentEquals(other.inpcFontes)) return false
        if (inpcSite != other.inpcSite) return false
        if (!igpmFontes.contentEquals(other.igpmFontes)) return false
        if (igpmSite != other.igpmSite) return false
        if (!selicFontes.contentEquals(other.selicFontes)) return false
        if (selicSite != other.selicSite) return false
        if (!cdiFontes.contentEquals(other.cdiFontes)) return false
        if (cdiSite != other.cdiSite) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ipcaFontes.contentHashCode()
        result = 31 * result + ipcaSite.hashCode()
        result = 31 * result + inpcFontes.contentHashCode()
        result = 31 * result + inpcSite.hashCode()
        result = 31 * result + igpmFontes.contentHashCode()
        result = 31 * result + igpmSite.hashCode()
        result = 31 * result + selicFontes.contentHashCode()
        result = 31 * result + selicSite.hashCode()
        result = 31 * result + cdiFontes.contentHashCode()
        result = 31 * result + cdiSite.hashCode()
        return result
    }
}