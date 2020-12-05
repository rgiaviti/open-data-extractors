package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient

import com.gh.rgiaviti.opendata.extractors.common.abstracts.AbstractHTMLExtractor
import com.gh.rgiaviti.opendata.extractors.common.constants.LogMessages.LOG_MSG_EXCEPTION
import com.gh.rgiaviti.opendata.extractors.common.constants.LogMessages.LOG_URL_REQUEST
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_ATIVIDADE
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_CNPJ
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_SETORES
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_SITE
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_STOCK_NAME
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_TICKERS
import com.gh.rgiaviti.opendata.extractors.infra.exceptions.HTMLParserException
import mu.KotlinLogging
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.net.SocketTimeoutException
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors

@Component
class B3EmpresaDetailExtractor : AbstractHTMLExtractor() {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        private val REGEX_TICKER = "^[a-zA-Z0-9]{4}\\d{1,2}\$".toRegex()
        private const val TICKER_DELIMITER = " "
        private const val SECTOR_DELIMITER = '/'
        private const val NO_TICKERS = "TICKERS NÃO MAPEADOS"
        private const val NO_SECTORS = "NÃO HÁ SETORES LISTADOS"
        private const val MAX_REQUEST_TRIES = 5
    }


    fun extractEmpresaDetail(empresaResume: EmpresaResumoDTO): EmpresaDTO {
        val requestAttempts = AtomicInteger(0)
        var document: Document? = null

        do {
            try {
                requestAttempts.incrementAndGet()
                document = this.getJsoupDocumet(empresaResume.urlDetalhe)
            } catch (ex: SocketTimeoutException) {
                log.error(":: Erro de timeout ao tentar extrair informações da empresa: {}", empresaResume.nomeEmpresa)
                log.error(":: Tentativa {}/{}", requestAttempts.get(), MAX_REQUEST_TRIES)
            } catch (ex: Exception) {
                log.error(":: Falha na tentativa de request para B3 para obter detalhes de empresa")
                log.error(":: Empresa a detalhar: {}", empresaResume.nomeEmpresa)
                log.error(LOG_URL_REQUEST, empresaResume.urlDetalhe)
                log.error(LOG_MSG_EXCEPTION, ex.message)
                throw HTMLParserException("erro ao parsear detalhes de empresa", ex)
            }
        } while (requestAttempts.get() < MAX_REQUEST_TRIES && document == null)

        if (document == null) {
            log.error(":: Falha geral na tentativa de extrair informações da empresa: {}", empresaResume.nomeEmpresa)
            log.error(":: Abortando extrator")
            throw HTMLParserException("erro na tentativa de extratir informações de empresa")
        }

        return this.extractInformations(empresaResume, document)
    }

    private fun extractInformations(empresaResume: EmpresaResumoDTO, document: Document): EmpresaDTO {
        return EmpresaDTO(
                nome = empresaResume.nomeEmpresa,
                cvm = empresaResume.codigoCvm,
                tickers = this.tickers(document),
                nomePregao = this.nomePregao(document),
                cnpj = this.cnpj(document),
                atividade = this.atividade(document),
                setores = this.setores(document),
                site = this.site(document)
        )
    }

    /**
     * Extrai o nome de pregão da empresa
     */
    private fun nomePregao(html: Document): String {
        return html.select(SELECT_STOCK_NAME).text().trim().toUpperCase()
    }

    /**
     * Extrai o CNPJ da empresa
     */
    private fun cnpj(html: Document): String {
        return html.select(SELECT_CNPJ).text().trim()
    }

    /**
     * Extrai a atividade da empresa
     */
    private fun atividade(html: Document): String {
        return html.select(SELECT_ATIVIDADE).text().trim()
    }

    /**
     * Extra o site da empresa
     */
    private fun site(html: Document): String {
        return html.select(SELECT_SITE).attr("href").trim()
    }

    /**
     * Extrai o array de setores da empresa.
     */
    private fun setores(html: Document): Set<String> {
        val arrSectors = html.select(SELECT_SETORES).text().trim().split(SECTOR_DELIMITER)
        return if (arrSectors.isEmpty()) {
            setOf(NO_SECTORS)
        } else {
            arrSectors.stream()
                    .map { t -> t.trim().toUpperCase() }
                    .collect(Collectors.toSet())
                    .toSortedSet()
        }
    }

    /**
     * Extrai e trata os tickers da empresa. É usado um set para não duplicar tickers.
     *
     * @return set de tickers da empresa
     */
    private fun tickers(html: Document): Set<String> {
        val strTickers = html.select(SELECT_TICKERS).select("a").text()
        val arrTickers = strTickers.split(TICKER_DELIMITER)
        return if (arrTickers.isEmpty()) {
            setOf(NO_TICKERS)
        } else {
            arrTickers.stream()
                    .map { ticker -> ticker.trim().toUpperCase() }
                    .filter { ticker -> ticker.matches(REGEX_TICKER) }
                    .collect(Collectors.toSet())
                    .toSortedSet()
        }
    }
}

data class EmpresaDTO(
        val nome: String,
        val cvm: String,
        val tickers: Set<String>,
        val nomePregao: String,
        val cnpj: String,
        val atividade: String,
        val setores: Set<String>,
        val site: String?
)