package com.gh.rgiaviti.ods.b3ce.extractors

import com.gh.rgiaviti.ods.b3ce.configs.Config.Key.*
import com.gh.rgiaviti.ods.b3ce.configs.Config.getConfig
import com.gh.rgiaviti.ods.b3ce.extractors.dtos.CompanyResume
import org.slf4j.LoggerFactory

object CompanyResumeExtractor : Extractor() {

    /**
     * Logger
     */
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Caracter que separa as letras iniciais de cada empresa
     */
    private const val SPLIT_LETTER_CHAR = ';'

    /**
     * CSS selector da tabela de listagem de empresa
     */
    private const val SELECT_TABLE = "#ctl00_contentPlaceHolderConteudo_BuscaNomeEmpresa1_grdEmpresa_ctl01 > tbody > tr"

    /**
     * Extrai um "resumo" das empresas listadas na B3. Aqui é retornada uma lista com
     * o resumo dessas empresas. A partir desse resumo, que contém a URL com os detalhes
     * dela, é que é possível obter todos os detalhes referentes a essa empresa.
     *
     * @return lista com o resumo das empresas
     */
    fun extractResumes(): List<CompanyResume> {
        val companiesListUrlByLetter = this.companiesListByLetter();
        val companies = mutableListOf<CompanyResume>()
        val timeBetweenRequests = timeBetweenRequests()

        companiesListUrlByLetter.parallelStream().forEach { url ->
            log.info("Parsing -> {}", url)
            Thread.sleep(randomDelay(timeBetweenRequests))
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

        this.html(url).select(SELECT_TABLE).forEach { element ->
            val name = element.select("td")[0].text();
            val cvm = element.select("td > a").attr("href").split('=')[1].trim()
            companiesResume.add(CompanyResume(name, cvm, detailBaseUrl + cvm))
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

    private fun timeBetweenRequests(): Long {
        return getConfig(TIME_BETWEEN_LETTERS).toLong()
    }
}