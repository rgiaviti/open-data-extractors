package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.gh.rgiaviti.opendata.extractors.common.domains.MetaInfo

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RootFundoImobiliario(
        @field: JsonProperty("meta-info")
        val metaInfo: MetaInfo,

        @field: JsonProperty("fundos-imobiliarios")
        val fiis: List<FundoImobiliario>
)