package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ListaFundoImobiliarioRes(

        @JsonProperty("page")
        val paginacao: PaginacaoRes?,

        @JsonProperty("results")
        val fundosImobiliarios: List<FundoImobiliarioResumoRes>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PaginacaoRes(

        @field: JsonProperty("pageNumber")
        val pageNumber: Int?,

        @field: JsonProperty("pageSize")
        val pageSize: Int?,

        @field: JsonProperty("totalRecords")
        val totalRecords: Int?,

        @field: JsonProperty("totalPages")
        val totalPages: Int?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class FundoImobiliarioResumoRes(

        @field: JsonProperty("segment")
        val segmento: String?,

        @field: JsonProperty("acronym")
        val codigo: String?,

        @field: JsonProperty("fundName")
        val nomePregao: String?,

        @field: JsonProperty("companyName")
        val nomeCompleto: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GetFundoImobiliarioDetalheRes(

        @field: JsonProperty("detailFund")
        val detalhes: FundoImobiliarioDetalheRes?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class FundoImobiliarioDetalheRes(

        @field: JsonProperty("tradingCode")
        val ticker: String?,

        @field: JsonProperty("cnpj")
        val cnpj: String?,

        @field: JsonProperty("webSite")
        val site: String?,

        @field: JsonProperty("codes")
        val codigos: List<String>?
)