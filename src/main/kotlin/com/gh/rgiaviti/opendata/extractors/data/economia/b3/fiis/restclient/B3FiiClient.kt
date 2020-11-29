package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component

@Component
class B3FiiClient(
        private val httpClient: OkHttpClient,
        private val mapper: ObjectMapper
) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        const val LISTAR_FII_URL = "https://sistemaswebb3-listados.b3.com.br/fundsProxy/fundsCall/GetListedFundsSIG/eyJ0eXBlRnVuZCI6NywicGFnZU51bWJlciI6MSwicGFnZVNpemUiOjUwMCwia2V5d29yZCI6IiJ9";
        const val DETALHAR_FII_URL = "https://sistemaswebb3-listados.b3.com.br/fundsProxy/fundsCall/GetDetailFundSIG/"
        const val DETALHAR_FII_QUERY_PARAM = "{'typeFund':7,'cnpj':'','identifierFund':'{0}'}"
    }

    fun listarFundosImobiliarios() {
        val request = Request.Builder()
                .get()
                .url(LISTAR_FII_URL)
                .build()

        val response = this.httpClient.newCall(request).execute()

        if (response.body != null) {
            val listaFiis = mapper.readValue(response.body!!.string(), ListaFundoImobiliarioRes::class.java)
            listaFiis.fundosImobiliarios!!.forEach { log.info(it.nomeCompleto) }
        }
    }

    fun getDetalhe(codigo: String) {

    }
}