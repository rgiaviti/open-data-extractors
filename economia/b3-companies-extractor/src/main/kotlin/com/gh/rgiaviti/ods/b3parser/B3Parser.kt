package com.gh.rgiaviti.ods.b3parser

import com.gh.rgiaviti.ods.b3parser.Selector.COMPANY_TABLE_LIST
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import java.net.URL

object B3Parser {

    private const val REQUEST_TIMEOUT = 3000
    private const val WAIT_BEFORE_REQUEST = 500
    private const val DETAIL_URL = "http://bvmf.bmfbovespa.com.br/pt-br/mercados/acoes/empresas/ExecutaAcaoConsultaInfoEmp.asp?CodCVM="
    private const val LETTER_URL = "http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/BuscaEmpresaListada.aspx?Letra="
    private val START_LETTERS = arrayOf("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9")

    private val log = LoggerFactory.getLogger(javaClass)

    fun scrap() {
        lettersUrl().forEach { t: URL -> scrapCodigosCVM(t) }
    }

    fun scrapCodigosCVM(letterUrl: URL): List<ResumoEmpresa> {
        val resumoEmpresas = mutableListOf<ResumoEmpresa>()
        val html = Jsoup.parse(letterUrl, REQUEST_TIMEOUT)
        val htmlCompaniesList = html.select(COMPANY_TABLE_LIST.selector)

        htmlCompaniesList.forEach { e ->
            Thread.sleep(500)
            val codigoCvm = e.select("td:nth-child(1) > a").attr("href").split("=")[1]
            val nome = e.select("td")[0].text()

            log.info("Empresa: {}. Cod CVM: {}", nome, codigoCvm)

            resumoEmpresas.add(ResumoEmpresa(nome, codigoCvm))
        }

        return resumoEmpresas
    }

    private fun lettersUrl(): List<URL> {
        val lettersUrl = mutableListOf<URL>()

        START_LETTERS.forEach { letter ->
            lettersUrl.add(URL(LETTER_URL + letter))
        }

        return lettersUrl;
    }
}

data class ResumoEmpresa(
    val nome: String,
    val codigoCVM: String
)