package com.gh.rgiaviti.ods.b3ce.domains

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MetaInfo(

        @JsonProperty("status")
        val status: String,

        @JsonProperty("data-ultima-atualizacao")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        val dataUltimaAtualizacao: LocalDate,

        @JsonProperty("versao")
        val versao: String,

        @JsonProperty("contagem")
        val contagem: Int,

        @JsonProperty("site")
        val website: String,

        @JsonProperty("fontes")
        val fontes: List<String>
)