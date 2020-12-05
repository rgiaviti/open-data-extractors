package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class EmpresaProperties(

        @Value("\${app.configs.empresas-b3.metainfos.site}")
        val site: String,

        @Value("\${app.configs.empresas-b3.metainfos.fontes}")
        val fontes: List<String>
)