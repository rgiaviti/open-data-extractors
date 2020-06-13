package com.gh.rgiaviti.ods.domains

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MetaInfo (

    @JsonProperty("data-ultima-atualizacao")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val dataUltimaAtualizacao: LocalDate,

    @JsonProperty("versao")
    val versao: String,

    @JsonProperty("quantidade-empresas")
    val quantidadeEmpresas: Int,

    @JsonProperty("website")
    val website: String,

    @JsonProperty("fontes")
    val fontes: List<String>
)