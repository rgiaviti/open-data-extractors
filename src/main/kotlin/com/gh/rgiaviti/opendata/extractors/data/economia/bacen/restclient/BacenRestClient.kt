package com.gh.rgiaviti.opendata.extractors.data.economia.bacen.restclient

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.gh.rgiaviti.opendata.extractors.common.constants.LogMessages.LOG_HTTP_STATUS_CODE
import com.gh.rgiaviti.opendata.extractors.common.constants.LogMessages.LOG_MSG_EXCEPTION
import com.gh.rgiaviti.opendata.extractors.common.constants.LogMessages.LOG_URL_REQUEST
import com.gh.rgiaviti.opendata.extractors.data.economia.bacen.IndiceBacen
import com.gh.rgiaviti.opendata.extractors.data.economia.bacen.domains.IndiceComum
import com.gh.rgiaviti.opendata.extractors.data.economia.bacen.domains.TaxaReferencial
import com.gh.rgiaviti.opendata.extractors.infra.exceptions.RestClientRequestException
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component

@Component
class BacenRestClient(
        private val httpClient: OkHttpClient,
        private val mapper: ObjectMapper
) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        private const val BACEN_CODE_PLACEHOLDER = "[codigo]"
        private const val BACEN_SERIES_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.$BACEN_CODE_PLACEHOLDER/dados?formato=json"
    }

    fun getIndiceComum(indice: IndiceBacen): List<IndiceComum> {
        val url = BACEN_SERIES_URL.replace(BACEN_CODE_PLACEHOLDER, indice.codigo)

        try {
            val request = Request.Builder().get().url(url).build()
            val response = this.httpClient.newCall(request).execute()
            if (response.isSuccessful && response.body != null) {
                return mapper.readValue(response.body!!.string())
            } else {
                log.error(":: O request foi executado no BACEN para obtenção dos detalhes, porém o body é nulo ou status é diferente de 200")
                log.error(":: Índice que falhou no request: {}", indice.name)
                log.error(LOG_URL_REQUEST, url)
                log.error(LOG_HTTP_STATUS_CODE, response.code)
                throw RestClientRequestException("response do bacen com erro")
            }
        } catch (ex: Exception) {
            log.error(":: Falha na tentativa de request para o BACEN para obter índice")
            log.error(":: Índice: {}", indice.name)
            log.error(LOG_URL_REQUEST, url)
            log.error(LOG_MSG_EXCEPTION, ex.message)
            throw RestClientRequestException("falha no request para o bacen", ex)
        }
    }

    fun getTaxaReferencial(): List<TaxaReferencial> {
        val url = BACEN_SERIES_URL.replace(BACEN_CODE_PLACEHOLDER, IndiceBacen.TR.codigo)

        try {
            val request = Request.Builder().get().url(url).build()
            val response = this.httpClient.newCall(request).execute()
            if (response.isSuccessful && response.body != null) {
                return mapper.readValue(response.body!!.string())
            } else {
                log.error(":: O request foi executado no BACEN para obtenção dos detalhes, porém o body é nulo ou status é diferente de 200")
                log.error(":: Índice que falhou no request: {}", IndiceBacen.TR.name)
                log.error(LOG_URL_REQUEST, BACEN_SERIES_URL)
                log.error(LOG_HTTP_STATUS_CODE, response.code)
                throw RestClientRequestException("response do bacen com erro")
            }
        } catch (ex: Exception) {
            log.error(":: Falha na tentativa de request para o BACEN para obter índice")
            log.error(":: Índice: {}", IndiceBacen.TR.name)
            log.error(LOG_URL_REQUEST, BACEN_SERIES_URL)
            log.error(LOG_MSG_EXCEPTION, ex.message)
            throw RestClientRequestException("falha no request para o bacen", ex)
        }
    }
}