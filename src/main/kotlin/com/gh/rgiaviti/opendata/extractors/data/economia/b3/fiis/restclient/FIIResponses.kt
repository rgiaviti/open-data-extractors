package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Objeto raiz / pai que encapsula a lista de resumo dos Fundos Imobiliários.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class FundoImobiliarioListRes(

        @JsonProperty("page")
        val pagination: PaginationRes?,

        @JsonProperty("results")
        val fundosImobiliarios: List<FundoImobiliarioResumeRes>?
)

/**
 * Objeto de paginação que vem no body do response dos request da B3 quando o retorno é uma lista, como no resumo dos FIIs.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class PaginationRes(

        @field: JsonProperty("pageNumber")
        val pageNumber: Int?,

        @field: JsonProperty("pageSize")
        val pageSize: Int?,

        @field: JsonProperty("totalRecords")
        val totalRecords: Int?,

        @field: JsonProperty("totalPages")
        val totalPages: Int?
)

/**
 * Dados de resumo do fundo imobiliário que a B3 retorna ao fazer o GET request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class FundoImobiliarioResumeRes(

        @field: JsonProperty("segment")
        val segmento: String?,

        @field: JsonProperty("acronym")
        val codigo: String?,

        @field: JsonProperty("fundName")
        val nomePregao: String?,

        @field: JsonProperty("companyName")
        val nomeCompleto: String?
)

/**
 * Objeto pai / raiz que encapsula o objeto de detalhes do Fundo Imobiliário
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class GetFundoImobiliarioDetailRes(

        @field: JsonProperty("detailFund")
        val detalhes: FundoImobiliarioDetailRes?
)

/**
 * Detalhes de um Fundo Imobiliário que são retornados no body do response do GET request feito a B3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class FundoImobiliarioDetailRes(

        @field: JsonProperty("tradingCode")
        val ticker: String?,

        @field: JsonProperty("cnpj")
        val cnpj: String?,

        @field: JsonProperty("webSite")
        val site: String?,

        @field: JsonProperty("codes")
        val codigos: List<String>?
)