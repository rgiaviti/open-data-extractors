package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient

import com.gh.rgiaviti.opendata.extractors.common.Helper
import com.gh.rgiaviti.opendata.extractors.common.abstracts.AbstractHTMLExtractor
import com.gh.rgiaviti.opendata.extractors.common.constants.LogMessages
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_TABELA_EMPRESAS_POR_LETRA
import com.gh.rgiaviti.opendata.extractors.infra.exceptions.HTMLParserException
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.net.URL
import java.util.stream.Collectors

/**
 * Classe que busca, parseia e extrai informações resumidas de empresas listadas na B3 a partir do próprio site da B3.
 *
 * Como a B3 não disponibiliza um serviço REST para esse próposito, temos que fazer scrap de sua página na web. Com isso,
 * ficamos mais expostos a erros.
 */
@Component
class B3EmpresaResumeExtractor : AbstractHTMLExtractor() {

    /**
     * Constantes e configurações
     */
    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        private const val LETRAS_INICIO_EMPRESAS = "A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z;0;1;2;3;4;5;6;7;8;9"
        private const val DELIMITADOR_LETRA = ";"
        private const val LISTA_EMPRESAS_URL = "http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/BuscaEmpresaListada.aspx?Letra="
        private const val EMPRESA_DETALHE_URL = "http://bvmf.bmfbovespa.com.br/pt-br/mercados/acoes/empresas/ExecutaAcaoConsultaInfoEmp.asp?CodCVM="
        private const val MAX_WAIT_TIME_BETWEEN_REQUESTS = 2 // seconds
    }

    /**
     * Faz o scrap dos dados básicos (nome, cvm e url dos detalhes) da listagem de empresas que
     * há na tabela HTML nessa página. A partir dessa listagem de empresas, nós podemos iniciar
     * o scrap das empresas com mais detalhes.
     */
    fun extractEmpresaResume(): List<EmpresaResumoDTO> {
        try {
            val empresasResumo = mutableListOf<EmpresaResumoDTO>()
            this.splitLetters().forEach { tableUrl ->
                log.info(":: Parseado o resumo de empresas da URL: {}", tableUrl.toString())
                this.getJsoupDocumet(tableUrl).select(SELECT_TABELA_EMPRESAS_POR_LETRA).forEach { element ->
                    val name = element.select("td")[0].text()
                    val cvm = element.select("td > a").attr("href").split('=')[1].trim()
                    empresasResumo.add(EmpresaResumoDTO(cvm, name, URL(EMPRESA_DETALHE_URL.plus(cvm))))
                }

                // Aguarda um tempo até o próximo request
                Helper.waitRandomTime(MAX_WAIT_TIME_BETWEEN_REQUESTS)
            }

            return empresasResumo

        } catch (ex: Exception) {
            log.error(":: Erro ao tentar parsear informações em página HTML")
            log.error(LogMessages.LOG_MSG_EXCEPTION, ex.message)
            throw HTMLParserException("erro em parse do html", ex)
        }
    }

    /**
     * Retorna a lista de URLs completa pronta para ser chamada onde há a listagem de empresas
     * da B3 por letra de início.
     */
    private fun splitLetters() = LETRAS_INICIO_EMPRESAS
            .split(DELIMITADOR_LETRA)
            .stream()
            .map { URL(LISTA_EMPRESAS_URL.plus(it)) }
            .collect(Collectors.toList())
}

/**
 * Objeto temporário onde salvamos os dados extraidos de resumo dos dados da empresa listada na B3.
 */
data class EmpresaResumoDTO(
        val codigoCvm: String,
        val nomeEmpresa: String,
        val urlDetalhe: URL
)