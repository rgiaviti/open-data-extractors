package com.gh.rgiaviti.opendata.extractors.infra

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class AppProperties(

        @Value("\${app.configs.comum.dir-saida-arquivos}")
        val dirSaidaArquivos: String,

        @Value("\${app.configs.comum.metainfos.versao}")
        val versao: String
)