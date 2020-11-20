package com.gh.rgiaviti.opendata.extractors.common.domains

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MetaInfo(

        @field: JsonProperty("status")
        val status: String,

        @field: JsonProperty("data-ultima-atualizacao")
        @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        val dataUltimaAtualizacao: LocalDate,

        @field: JsonProperty("data-ultima-referencia")
        @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        val dataReferencia: LocalDate,

        @field: JsonProperty("versao")
        val versao: String,

        @field: JsonProperty("contagem")
        val contagem: Int?,

        @field: JsonProperty("site")
        val website: String,

        @field: JsonProperty("fontes")
        val fontes: List<String>
)
