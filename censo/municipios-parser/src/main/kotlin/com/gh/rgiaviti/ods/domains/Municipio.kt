package com.gh.rgiaviti.ods.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Municipio(

        @JsonProperty("sigla-uf")
        val uf: String,

        @JsonProperty("codigo-ibge-uf")
        val codigoIBGEUF: String,

        @JsonProperty("codigo-ibge-municipio")
        val codigoIBGEMunicipio: String,

        @JsonProperty("nome")
        val nome: String,

        @JsonProperty("populacao-estimada")
        val populacaoEstimada: Int,

        @JsonProperty("notas-adicionais")
        val notasAdicionais: NotasAdicionais

)