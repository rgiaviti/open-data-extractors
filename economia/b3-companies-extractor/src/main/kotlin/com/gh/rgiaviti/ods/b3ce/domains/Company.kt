package com.gh.rgiaviti.ods.b3ce.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Company(

        @JsonProperty("nome")
        val nome: String,

        @JsonProperty("codigo-cvm")
        val cvm: String,

        @JsonProperty("tickers")
        val tickers: Set<String>,

        @JsonProperty("nome-pregao")
        val nomePregao: String,

        @JsonProperty("cnpj")
        val cnpj: String,

        @JsonProperty("ramo-atividade")
        val atividade: String,

        @JsonProperty("setores")
        val setores: Set<String>,

        @JsonProperty("site")
        val site: String?
)