package com.gh.rgiaviti.opendata.extractors.data.censo.ufs.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UnidadeFederativa(

        @field: JsonProperty("unidade-federativa")
        val unidadeFerativa: String,

        @field: JsonProperty("codigo-ibge")
        val codigoIBGE: String,

        @field: JsonProperty("sigla")
        val sigla: String,

        @field: JsonProperty("sede")
        val sede: String,

        @field: JsonProperty("gentilico")
        val gentilico: String,

        @field: JsonProperty("regiao")
        val regiao: String,

        @field: JsonProperty("populacao-estimada")
        val populacaoEstimada: Int,

        @field: JsonProperty("area")
        val area: Int,

        @field: JsonProperty("notas-adicionais")
        val notasAdicionais: NotasAdicionais
)
