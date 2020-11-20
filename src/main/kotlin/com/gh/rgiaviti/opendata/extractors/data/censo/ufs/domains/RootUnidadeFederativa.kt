package com.gh.rgiaviti.opendata.extractors.data.censo.ufs.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.gh.rgiaviti.opendata.extractors.common.domains.MetaInfo

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RootUnidadeFederativa(

        @field: JsonProperty("meta-info")
        val metaInfo: MetaInfo,

        @field: JsonProperty("unidades-federativas")
        val unidadesFederativas: List<UnidadeFederativa>
)