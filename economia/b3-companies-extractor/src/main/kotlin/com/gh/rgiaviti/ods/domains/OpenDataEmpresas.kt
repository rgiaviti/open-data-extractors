package com.gh.rgiaviti.ods.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OpenDataEmpresas (

    @JsonProperty("meta-info")
    val metaInfo: MetaInfo,

    @JsonProperty("empresas")
    val empresas: List<Empresa>
)