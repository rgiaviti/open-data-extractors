package com.gh.rgiaviti.ods.b3ce.extractors

import com.gh.rgiaviti.ods.b3ce.configs.Config.Key.TIME_BETWEEN_DETAILS
import com.gh.rgiaviti.ods.b3ce.configs.Config.getConfig
import com.gh.rgiaviti.ods.b3ce.domains.Company
import com.gh.rgiaviti.ods.b3ce.extractors.dtos.CompanyResume
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import java.util.stream.Collectors
import kotlin.random.Random.Default.nextLong


object CompanyDetailExtractor : Extractor() {

    //--------------------------------------------------
    // Logger
    //--------------------------------------------------
    private val log = LoggerFactory.getLogger(javaClass)

    private const val SEED_DELAY = 2000L
    private const val SECTOR_DELIMITER = '/'
    private const val NO_SECTORS = "NÃO HÁ SETORES LISTADOS"
    private val REGEX_TICKER = "^[a-zA-Z]{4}\\d{1,2}\$".toRegex()
    private const val TICKER_DELIMITER = " "
    private const val NO_TICKERS = "TICKERS NÃO MAPEADOS"


    //----------------------------------------------
    // SELECTORS
    //----------------------------------------------
    private const val SELECT_STOCK_NAME = "#accordionDados > table > tbody > tr:nth-child(1) > td:nth-child(2)"
    private const val SELECT_TICKERS = "#accordionDados > table > tbody > tr:nth-child(2) > td:nth-child(2)"
    private const val SELECT_CNPJ = "#accordionDados > table > tbody > tr:nth-child(3) > td:nth-child(2)"
    private const val SELECT_ATIVIDADE = "#accordionDados > table > tbody > tr:nth-child(4) > td:nth-child(2)"
    private const val SELECT_SETORES = "#accordionDados > table > tbody > tr:nth-child(5) > td:nth-child(2)"
    private const val SELECT_SITE = "#accordionDados > table > tbody > tr:nth-child(6) > td:nth-child(2) > a"

    /**
     * Extrai os detalhes das empresas que estão na lista do parâmetro.
     * Para cada empresa que está na lista é consultada a página com detalhes sobre
     * ela e então é extraida as informações.
     *
     * @param resumes a lista com o resumo da empresa
     * @return lista com os detalhes da empresa
     */
    fun extractDetails(resumes: List<CompanyResume>): List<Company> {
        val companies = mutableListOf<Company>()

        resumes.parallelStream().forEach { resume ->
            Thread.sleep(randomDelay())
            log.info("Extracting Details --> {}", resume.name)
            val html = this.html(resume.detailUrl)
            val c = Company(
                resume.name,
                resume.cvm,
                tickers(html),
                nomePregao(html),
                cnpj(html),
                atividade(html),
                setores(html),
                site(html)
            )
            companies.add(c)
        }

        companies.sortBy { company -> company.nome }
        return companies
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
            arrSectors.stream().map { t -> t.trim().toUpperCase() }.collect(Collectors.toSet())
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
        }
    }

    /**
     * Gera um tempo aleatório baseado no na configuração de delay que está nos properties. Isso
     * é para não sobrecarregar de requests a página da B3.
     */
    private fun randomDelay(): Long {
        return nextLong(getConfig(TIME_BETWEEN_DETAILS).toLong(), SEED_DELAY)
    }
}