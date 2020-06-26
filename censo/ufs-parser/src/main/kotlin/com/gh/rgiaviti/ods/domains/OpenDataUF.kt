package com.gh.rgiaviti.ods.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OpenDataUF(

        @JsonProperty("meta-info")
        val metaInfo: MetaInfo,

        @JsonProperty("unidades-federativas")
        val unidadesFederativas: List<UnidadeFederativa>
)