package com.gh.rgiaviti.opendata.extractors.data.economia.bacen.domains

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class IndiceComum(

        @field: JsonProperty("data")
        @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        val data: LocalDate,

        @field: JsonProperty("valor")
        val valor: BigDecimal
)