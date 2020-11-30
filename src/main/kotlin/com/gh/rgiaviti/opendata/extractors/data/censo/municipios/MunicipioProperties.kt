package com.gh.rgiaviti.opendata.extractors.data.censo.municipios

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class MunicipioProperties(

        @Value("\${app.configs.municipios.metainfos.site}")
        val site: String,

        @Value("\${app.configs.municipios.metainfos.fontes}")
        val fontes: List<String>,

        @Value("\${app.configs.municipios.metainfos.data-referencia}")
        val dataReferncia: String
)
