package com.gh.rgiaviti.opendata.extractors.data.economia.bacen.domains

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDate

class TaxaReferencial(
        
        @field: JsonProperty("data")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        val data: LocalDate,

        @field: JsonProperty("dataFim")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        val dataFim: LocalDate,

        @field: JsonProperty("valor")
        val valor: BigDecimal
)