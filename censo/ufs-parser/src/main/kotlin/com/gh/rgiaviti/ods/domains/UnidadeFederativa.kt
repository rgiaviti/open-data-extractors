package com.gh.rgiaviti.ods.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UnidadeFederativa(

    @JsonProperty("unidade-federativa")
    val unidadeFerativa: String,

    @JsonProperty("abreviacao")
    val abreviacao: String,

    @JsonProperty("sede")
    val sede: String,

    @JsonProperty("gentilico")
    val gentilico: String,

    @JsonProperty("regiao")
    val regiao: String,

    @JsonProperty("populacao-estimada")
    val populacaoEstimada: Int,

    @JsonProperty("area")
    val area: Float,

    @JsonProperty("notas-adicionais")
    val notasAdicionais: NotasAdicionais
)