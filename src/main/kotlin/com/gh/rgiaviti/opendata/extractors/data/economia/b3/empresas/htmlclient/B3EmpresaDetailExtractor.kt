package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient

import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_ATIVIDADE
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_CNPJ
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_SETORES
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_SITE
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_STOCK_NAME
import mu.KotlinLogging
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class B3EmpresaDetailExtractor {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        private val REGEX_TICKER = "^[a-zA-Z0-9]{4}\\d{1,2}\$".toRegex()
        private const val EMPRESA_DETAIL_URL = "http://bvmf.bmfbovespa.com.br/pt-br/mercados/acoes/empresas/ExecutaAcaoConsultaInfoEmp.asp?CodCVM="
        private const val TICKER_DELIMITER = " "
        private const val SECTOR_DELIMITER = '/'
        private const val NO_TICKERS = "TICKERS NÃO MAPEADOS"
        private const val NO_SECTORS = "NÃO HÁ SETORES LISTADOS"
        private const val MIN_WAIT_TIME_BETWEEN_REQUESTS = 3
        private const val MAX_WAIT_TIME_BETWEEN_REQUESTS = 6
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
}