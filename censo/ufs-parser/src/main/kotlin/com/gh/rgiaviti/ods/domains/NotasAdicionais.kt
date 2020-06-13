package com.gh.rgiaviti.ods.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class NotasAdicionais(

    @JsonProperty("nota-populacional")
    val notaPopulacional: String,

    @JsonProperty("mensagens")
    val mensagens: List<String>
)