package com.gh.rgiaviti.opendata.extractors.data.economia.bacen

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class BacenProperties(

        @Value("\${app.configs.bacen.ipca.metainfos.fontes}")
        val ipcaFontes: List<String>,

        @Value("\${app.configs.bacen.ipca.metainfos.site}")
        val ipcaSite: String,

        @Value("\${app.configs.bacen.inpc.metainfos.fontes}")
        val inpcFontes: List<String>,

        @Value("\${app.configs.bacen.inpc.metainfos.site}")
        val inpcSite: String,

        @Value("\${app.configs.bacen.igpm.metainfos.fontes}")
        val igpmFontes: List<String>,

        @Value("\${app.configs.bacen.igpm.metainfos.site}")
        val igpmSite: String,

        @Value("\${app.configs.bacen.selic.metainfos.fontes}")
        val selicFontes: List<String>,

        @Value("\${app.configs.bacen.selic.metainfos.site}")
        val selicSite: String,

        @Value("\${app.configs.bacen.cdi.metainfos.fontes}")
        val cdiFontes: List<String>,

        @Value("\${app.configs.bacen.cdi.metainfos.site}")
        val cdiSite: String,

        @Value("\${app.configs.bacen.tr.metainfos.fontes}")
        val trFontes: List<String>,

        @Value("\${app.configs.bacen.tr.metainfos.site}")
        val trSite: String
)