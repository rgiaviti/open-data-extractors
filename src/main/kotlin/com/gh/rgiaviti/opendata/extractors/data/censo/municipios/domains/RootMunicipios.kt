package com.gh.rgiaviti.opendata.extractors.data.censo.municipios.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.gh.rgiaviti.opendata.extractors.common.domains.MetaInfo

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RootMunicipios(
        @JsonProperty("meta-info")
        val metaInfo: MetaInfo,

        @JsonProperty("municipios")
        val municipios: List<Municipio>
)