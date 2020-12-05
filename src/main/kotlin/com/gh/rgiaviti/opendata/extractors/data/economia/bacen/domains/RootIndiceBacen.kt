package com.gh.rgiaviti.opendata.extractors.data.economia.bacen.domains

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.gh.rgiaviti.opendata.extractors.common.domains.MetaInfo

@JsonIgnoreProperties(ignoreUnknown = true)
data class RootIndiceBacen(

        @field: JsonProperty("meta-info")
        val metaInfo: MetaInfo,

        @field: JsonProperty("indices")
        val indices: List<Any>
)