package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FundoImobiliario(
        @field: JsonProperty("nome")
        val nome: String,

        @field: JsonProperty("ticker")
        val ticker: String,

        @field: JsonProperty("codigos")
        val codigos: List<String> = mutableListOf(),

        @field: JsonProperty("nome-pregao")
        val nomePregao: String,

        @field: JsonProperty("cnpj")
        val cnpj: String,

        @field: JsonProperty("site")
        val site: String
)