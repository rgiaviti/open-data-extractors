package com.gh.rgiaviti.opendata.extractors.data.censo.municipios.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class NotasAdicionais(

        @field: JsonProperty("nota-populacional")
        val notaPopulacional: String,

        @field: JsonProperty("mensagens")
        val mensagens: List<String>
)