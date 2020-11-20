package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Empresa(

        @field: JsonProperty("nome")
        val nome: String,

        @field: JsonProperty("codigo-cvm")
        val cvm: String,

        @field: JsonProperty("tickers")
        val tickers: Set<String>,

        @field: JsonProperty("nome-pregao")
        val nomePregao: String,

        @field: JsonProperty("cnpj")
        val cnpj: String,

        @field: JsonProperty("ramo-atividade")
        val atividade: String,

        @field: JsonProperty("setores")
        val setores: Set<String>,

        @field: JsonProperty("site")
        val site: String?
)