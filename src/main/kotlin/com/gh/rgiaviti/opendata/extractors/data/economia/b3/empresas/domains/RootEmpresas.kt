package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.gh.rgiaviti.opendata.extractors.common.domains.MetaInfo

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RootEmpresas(

        @field: JsonProperty("meta-info")
        val metaInfo: MetaInfo,

        @field: JsonProperty("empresas")
        val empresas: List<Empresa>
)