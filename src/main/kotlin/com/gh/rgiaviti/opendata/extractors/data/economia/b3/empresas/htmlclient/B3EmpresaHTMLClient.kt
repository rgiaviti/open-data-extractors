package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient

import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.domains.Empresa
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_TABELA_EMPRESAS_POR_LETRA
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_TICKERS
import com.gh.rgiaviti.opendata.extractors.infra.AppProperties
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.net.URL
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.stream.Collectors

@Component
class B3EmpresaHTMLClient(
        private val appProperties: AppProperties
) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        private const val LETRAS_INICIO_EMPRESAS = "A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z;0;1;2;3;4;5;6;7;8;9"
        private const val DELIMITADOR_LETRA = ";"
        private const val LISTA_EMPRESAS_URL = "http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/BuscaEmpresaListada.aspx?Letra="
        private const val EMPRESA_DETALHE_URL = "http://bvmf.bmfbovespa.com.br/pt-br/mercados/acoes/empresas/ExecutaAcaoConsultaInfoEmp.asp?CodCVM="
        private const val WAIT_TIME_BETWEEN_REQUEST = 3L
        private const val REQUEST_TIMEOUT = 60000
        private const val SECTOR_DELIMITER = '/'
        private const val NO_SECTORS = "NÃO HÁ SETORES LISTADOS"
        private val REGEX_TICKER = "^[a-zA-Z0-9]{4}\\d{1,2}\$".toRegex()
        private const val TICKER_DELIMITER = " "
        private const val NO_TICKERS = "TICKERS NÃO MAPEADOS"
    }

    private fun extrairDetalhesEmpresa(listaUrlDetalhe: List<URL>): List<Empresa> {
        listaUrlDetalhe.parallelStream().forEach { url ->
            val jsoupDocumet = this.getJsoupDocumet(url.toString())
            jsoupDocumet.s
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
                    .toSortedSet()
        }
    }

    private fun listarUrlsDetalhesEmpresa(listaUrlLetras: List<URL>): List<URL> {
        val listaUrlDetalheEmpresa = mutableListOf<URL>()
        listaUrlLetras.stream().forEach { urlLetra ->
            log.info(":: Parseando URL {}. Timeout de {} segundos", urlLetra.toString(), MILLISECONDS.toSeconds(REQUEST_TIMEOUT.toLong()))
            val jsoupDocumet = this.getJsoupDocumet(urlLetra.toString())

            jsoupDocumet.select(SELECT_TABELA_EMPRESAS_POR_LETRA).forEach { element ->
                val name = element.select("td")[0].text()
                log.info(":: Extraindo Código CVM da Empresa {}", name)

                val codigoCvm = element.select("td > a").attr("href").split('=')[1].trim()
                listaUrlDetalheEmpresa.add(URL(EMPRESA_DETALHE_URL.plus(codigoCvm)))
            }

            log.info(":: Aguardando {} segundos até o próximo request", WAIT_TIME_BETWEEN_REQUEST)
            Thread.sleep(TimeUnit.SECONDS.toMillis(WAIT_TIME_BETWEEN_REQUEST))
        }

        return listaUrlDetalheEmpresa
    }



    private fun listarUrlsPorLetraInicial() = LETRAS_INICIO_EMPRESAS
            .split(DELIMITADOR_LETRA)
            .stream()
            .map { URL(LISTA_EMPRESAS_URL.plus(it)) }
            .collect(Collectors.toList())
}