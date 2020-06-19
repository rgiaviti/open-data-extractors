package com.gh.rgiaviti.ods.b3ce.extractors

import com.gh.rgiaviti.ods.b3ce.configs.Config.Key.*
import com.gh.rgiaviti.ods.b3ce.configs.Config.getConfig
import com.gh.rgiaviti.ods.b3ce.extractors.dtos.CompanyResume
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import java.net.URL

object CompanyResumeExtractor {

    private val log = LoggerFactory.getLogger(javaClass)

    private const val SPLIT_LETTER_CHAR = ';'
    private const val SELECTOR_TABLE =
        "#ctl00_contentPlaceHolderConteudo_BuscaNomeEmpresa1_grdEmpresa_ctl01 > tbody > tr"

    fun getCompaniesResume(): List<CompanyResume> {
        return mutableListOf();
    }

    fun extractResumes(): List<CompanyResume> {
        val companiesListUrlByLetter = this.companiesListByLetter();
        val timeBetweenLetters = getConfig(TIME_BETWEEN_LETTERS).toLong()
        val companies = mutableListOf<CompanyResume>()

        companiesListUrlByLetter.stream().forEach { url ->
            log.info("Parsing --> {}", url)
            Thread.sleep(timeBetweenLetters)
            companies.addAll(parseCompanyList(url))
        }

        companies.sortBy { companyResume -> companyResume.name }
        return companies
    }


    /**
     * Faz o scrap dos dados básicos (nome, cvm e url dos detalhes) da listagem de empresas que
     * há na tabela HTML nessa página. A partir dessa listagem de empresas, nós podemos iniciar
     * o scrap das empresas com mais detalhes.
     */
    private fun parseCompanyList(url: String): List<CompanyResume> {
        val companiesResume = mutableListOf<CompanyResume>()
        val detailBaseUrl = getConfig(COMPANY_DETAIL_URL)

        this.html(url).select(SELECTOR_TABLE).forEach { element ->
            val name = element.select("td")[0].text();
            val cvm = element.select("td > a").attr("href").split('=')[1].trim()
            val detailUrl = URL(detailBaseUrl + cvm)
            companiesResume.add(CompanyResume(name, cvm, detailUrl))
        }

        return companiesResume
    }

    /**
     * Retorna a lista de URLs completa pronta para ser chamada onde há a listagem de empresas
     * da B3 por letra de início.
     */
    private fun companiesListByLetter(): List<String> {
        val urlList = mutableListOf<String>()
        val letters = getConfig(START_LETTERS).split(SPLIT_LETTER_CHAR)
        val resumeUrl = getConfig(COMPANIES_RESUME_URL)
        letters.forEach { letter -> urlList.add(resumeUrl + letter) }
        return urlList
    }

    private fun html(url: String): Document {
        val userAgent = getConfig(BROWSER_USER_AGENT)
        val timeout = getConfig(CONNECTION_TIMEOUT).toInt()

        return Jsoup
            .connect(url)
            .userAgent(userAgent)
            .timeout(timeout)
            .followRedirects(true)
            .get()
    }
}