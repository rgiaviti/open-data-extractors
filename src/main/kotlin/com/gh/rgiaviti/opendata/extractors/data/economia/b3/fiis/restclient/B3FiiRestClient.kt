package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient

import com.fasterxml.jackson.databind.ObjectMapper
import com.gh.rgiaviti.opendata.extractors.infra.LogMessages.LOG_HTTP_STATUS_CODE
import com.gh.rgiaviti.opendata.extractors.infra.LogMessages.LOG_MSG_EXCEPTION
import com.gh.rgiaviti.opendata.extractors.infra.LogMessages.LOG_URL_REQUEST
import com.gh.rgiaviti.opendata.extractors.infra.exceptions.RestClientRequestException
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component
import java.util.*

@Component
class B3FiiRestClient(
        private val httpClient: OkHttpClient,
        private val mapper: ObjectMapper
) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        const val LISTAR_FII_URL = "https://sistemaswebb3-listados.b3.com.br/fundsProxy/fundsCall/GetListedFundsSIG/eyJ0eXBlRnVuZCI6NywicGFnZU51bWJlciI6MSwicGFnZVNpemUiOjUwMCwia2V5d29yZCI6IiJ9"
        const val DETALHAR_FII_URL = "https://sistemaswebb3-listados.b3.com.br/fundsProxy/fundsCall/GetDetailFundSIG/"
        const val DETALHAR_FII_QUERY_PARAM = "{'typeFund':7,'cnpj':'','identifierFund':'[codigo]'}"
    }

    fun listarFundosImobiliarios(): ListaFundoImobiliarioRes {
        val request = Request.Builder().get().url(LISTAR_FII_URL).build()

        try {
            val response = this.httpClient.newCall(request).execute()

            if (response.isSuccessful && response.body != null) {
                return mapper.readValue(response.body!!.string(), ListaFundoImobiliarioRes::class.java)
            } else {
                log.error(":: O request foi executado na B3 para obtenção de FIIs, porém o body é nulo ou status é diferente de 200")
                log.error(LOG_URL_REQUEST, LISTAR_FII_URL)
                log.error(LOG_HTTP_STATUS_CODE, response.code)
                throw RestClientRequestException("response da b3 com erro")
            }
        } catch (ex: Exception) {
            log.error(":: Falha na tentativa de request para B3 para obter a listagem de FIIs")
            log.error(LOG_URL_REQUEST, LISTAR_FII_URL)
            log.error(LOG_MSG_EXCEPTION, ex.message)
            throw RestClientRequestException("falha no request para a b3")
        }
    }

    fun getDetalhe(codigo: String): GetFundoImobiliarioDetalheRes {
        val param = DETALHAR_FII_QUERY_PARAM.replace("[codigo]", codigo)
        val paramInBase64 = Base64.getEncoder().encodeToString(param.toByteArray())

        val request = Request.Builder().get().url(url = DETALHAR_FII_URL.plus(paramInBase64)).build()

        try {
            val response = this.httpClient.newCall(request).execute()

            if (response.isSuccessful && response.body != null) {
                return mapper.readValue(response.body!!.string(), GetFundoImobiliarioDetalheRes::class.java)
            } else {
                log.error(":: O request foi executado na B3 para obtenção dos detalhes, porém o body é nulo ou status é diferente de 200")
                log.error(":: FII a detalhar: {}", codigo)
                log.error(LOG_URL_REQUEST, LISTAR_FII_URL)
                log.error(LOG_HTTP_STATUS_CODE, response.code)
                throw RestClientRequestException("response da b3 com erro")
            }
        } catch (ex: Exception) {
            log.error(":: Falha na tentativa de request para B3 para obter detalhes de FII")
            log.error(":: FII a detalhar: {}", codigo)
            log.error(LOG_URL_REQUEST, LISTAR_FII_URL)
            log.error(LOG_MSG_EXCEPTION, ex.message)
            throw RestClientRequestException("falha no request para a b3")
        }
    }
}