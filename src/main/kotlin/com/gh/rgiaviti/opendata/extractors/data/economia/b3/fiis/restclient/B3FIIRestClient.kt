package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient

import com.fasterxml.jackson.databind.ObjectMapper
import com.gh.rgiaviti.opendata.extractors.common.constants.LogMessages.LOG_HTTP_STATUS_CODE
import com.gh.rgiaviti.opendata.extractors.common.constants.LogMessages.LOG_MSG_EXCEPTION
import com.gh.rgiaviti.opendata.extractors.common.constants.LogMessages.LOG_URL_REQUEST
import com.gh.rgiaviti.opendata.extractors.infra.exceptions.RestClientRequestException
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component
import java.util.*

/**
 * Uma classe simples de comunicação rest usada para executar requests para alguns endpoints públicos da B3 e buscar
 * informações sobre os Fundos Imobiliários que estão listados na própria B3. É usado o okhttp como biblioteca http
 * para a execução dos requests.
 *
 * @author Ricardo Giaviti
 * @since 1.0.0
 */
@Component
class B3FIIRestClient(
        private val httpClient: OkHttpClient,
        private val mapper: ObjectMapper
) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        private const val LISTAR_FII_URL = "https://sistemaswebb3-listados.b3.com.br/fundsProxy/fundsCall/GetListedFundsSIG/eyJ0eXBlRnVuZCI6NywicGFnZU51bWJlciI6MSwicGFnZVNpemUiOjUwMCwia2V5d29yZCI6IiJ9"
        private const val DETALHAR_FII_URL = "https://sistemaswebb3-listados.b3.com.br/fundsProxy/fundsCall/GetDetailFundSIG/"
        private const val DETALHAR_FII_TICKER_PLACEHOLDER = "[ticker]"
        private const val DETALHAR_FII_QUERY_PARAM = "{'typeFund':7,'cnpj':'','identifierFund':'$DETALHAR_FII_TICKER_PLACEHOLDER'}"
    }

    /**
     * Faz um GET request para o sistema da B3 no qual retorna a listagem com todos os Fundos Imobiliários que estão
     * listados atualmente na B3. O response da B3 é um JSON com dados bem resumidos sobre os fundos imobiliários listados.
     *
     * É necessário um outro request em outro endpoint para buscar mais detalhes a respeito dos fundos imobiliários retornados.
     *
     * @return a listagem com resumo dos fundos imobiliários listados na B3
     * @throws RestClientRequestException caso ocorra algum erro no request a B3
     */
    fun listFundosImobiliarios(): FundoImobiliarioListRes {
        val request = Request.Builder().get().url(LISTAR_FII_URL).build()

        try {
            val response = this.httpClient.newCall(request).execute()

            if (response.isSuccessful && response.body != null) {
                return mapper.readValue(response.body!!.string(), FundoImobiliarioListRes::class.java)
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

    /**
     * Executa um GET request para obtenção dos detalhes de um determinado fundo imobiliário baseado em seu ticker.
     *
     * @param ticker o código principal de negociação do Fundo Imobiliário
     */
    fun getFundoImobiliarioDetail(ticker: String): GetFundoImobiliarioDetailRes {
        try {
            val param = DETALHAR_FII_QUERY_PARAM.replace(DETALHAR_FII_TICKER_PLACEHOLDER, ticker)
            val paramInBase64 = Base64.getEncoder().encodeToString(param.toByteArray())
            val request = Request.Builder().get().url(DETALHAR_FII_URL.plus(paramInBase64)).build()
            val response = this.httpClient.newCall(request).execute()

            if (response.isSuccessful && response.body != null) {
                return mapper.readValue(response.body!!.string(), GetFundoImobiliarioDetailRes::class.java)
            } else {
                log.error(":: O request foi executado na B3 para obtenção dos detalhes, porém o body é nulo ou status é diferente de 200")
                log.error(":: FII a detalhar: {}", ticker)
                log.error(LOG_URL_REQUEST, LISTAR_FII_URL)
                log.error(LOG_HTTP_STATUS_CODE, response.code)
                throw RestClientRequestException("response da b3 com erro")
            }
        } catch (ex: Exception) {
            log.error(":: Falha na tentativa de request para B3 para obter detalhes de FII")
            log.error(":: FII a detalhar: {}", ticker)
            log.error(LOG_URL_REQUEST, LISTAR_FII_URL)
            log.error(LOG_MSG_EXCEPTION, ex.message)
            throw RestClientRequestException("falha no request para a b3", ex)
        }
    }
}