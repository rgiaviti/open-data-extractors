package com.gh.rgiaviti.ods.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Empresa (

    @JsonProperty("nome")
    val nome: String,

    @JsonProperty("codigo-cvm")
    val codigoCvm: Int,

    @JsonProperty("nome-pregao")
    val nomePregao: String,

    @JsonProperty("cnpj")
    val cnpj: String,

    @JsonProperty("atividade")
    val atividade: String,

    @JsonProperty("site")
    val site: String,

    @JsonProperty("setores")
    val setores: Set<String>
)