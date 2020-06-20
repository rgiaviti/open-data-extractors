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

    /**
     * Logger
     */
    private val log = LoggerFactory.getLogger(javaClass)

    private const val SEED_DELAY = 2000L
    private const val SECTOR_DELIMITER = '/'
    private const val TICKER_DELIMITER = " "
    private const val NO_SECTORS = "NÃO HÁ SETORES LISTADOS"
    private const val NO_TICKERS = "TICKERS NÃO MAPEADOS"
    private val REGEX_TICKER = "^[a-zA-Z]{4}\\d{1,2}\$".toRegex()

    private const val SELECT_STOCK_NAME = "#accordionDados > table > tbody > tr:nth-child(1) > td:nth-child(2)"
    private const val SELECT_TICKERS = "#accordionDados > table > tbody > tr:nth-child(2) > td:nth-child(2)"
    private const val SELECT_CNPJ = "#accordionDados > table > tbody > tr:nth-child(3) > td:nth-child(2)"
    private const val SELECT_ATIVIDADE = "#accordionDados > table > tbody > tr:nth-child(4) > td:nth-child(2)"
    private const val SELECT_SETORES = "#accordionDados > table > tbody > tr:nth-child(5) > td:nth-child(2)"
    private const val SELECT_SITE = "#accordionDados > table > tbody > tr:nth-child(6) > td:nth-child(2) > a"

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
                stockName(html),
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

    private fun stockName(html: Document): String {
        return html.select(SELECT_STOCK_NAME).text().trim().toUpperCase()
    }

    private fun cnpj(html: Document): String {
        return html.select(SELECT_CNPJ).text().trim()
    }

    private fun atividade(html: Document): String {
        return html.select(SELECT_ATIVIDADE).text().trim()
    }

    private fun site(html: Document): String {
        return html.select(SELECT_SITE).attr("href").trim()
    }

    private fun setores(html: Document): Set<String> {
        val arrSectors = html.select(SELECT_SETORES).text().trim().split(SECTOR_DELIMITER)
        return if (arrSectors.isEmpty()) {
            setOf(NO_SECTORS)
        } else {
            arrSectors.stream().map { t -> t.trim().toUpperCase() }.collect(Collectors.toSet())
        }
    }

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

    private fun randomDelay(): Long {
        return nextLong(getConfig(TIME_BETWEEN_DETAILS).toLong(), SEED_DELAY)
    }
}