package com.gh.rgiaviti.ods.b3ce.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CompanyList (

    @JsonProperty("meta-info")
    val metaInfo: MetaInfo,

    @JsonProperty("company")
    val companies: List<Company>
)