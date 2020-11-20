package com.gh.rgiaviti.opendata.extractors.data.censo.municipios.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Municipio(

        @field: JsonProperty("sigla-uf")
        val uf: String,

        @field: JsonProperty("codigo-ibge-uf")
        val codigoIBGEUF: String,

        @field: JsonProperty("codigo-ibge-municipio")
        val codigoIBGEMunicipio: String,

        @field: JsonProperty("nome")
        val nome: String,

        @field: JsonProperty("populacao-estimada")
        val populacaoEstimada: Int,

        @field: JsonProperty("notas-adicionais")
        val notasAdicionais: NotasAdicionais

)