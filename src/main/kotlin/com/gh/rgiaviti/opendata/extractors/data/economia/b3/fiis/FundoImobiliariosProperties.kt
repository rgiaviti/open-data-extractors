package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class FundoImobiliariosProperties(

        @Value("\${app.configs.fiis-b3.metainfos.site}")
        val site: String,

        @Value("\${app.configs.fiis-b3.metainfos.fontes}")
        val fontes: List<String>
)