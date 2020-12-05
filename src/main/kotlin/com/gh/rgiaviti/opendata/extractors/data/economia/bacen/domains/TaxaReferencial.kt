package com.gh.rgiaviti.opendata.extractors.data.economia.bacen.domains

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaxaReferencial(
        
        @field: JsonProperty("data")
        @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        val data: LocalDate,

        @field: JsonProperty("datafim")
        @field: JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        val dataFim: LocalDate,

        @field: JsonProperty("valor")
        val valor: BigDecimal
)