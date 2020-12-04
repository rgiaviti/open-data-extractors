package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient

import com.gh.rgiaviti.opendata.extractors.common.abstracts.AbstractHTMLExtractor
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaSelectors.SELECT_TABELA_EMPRESAS_POR_LETRA
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.net.URL
import java.util.stream.Collectors

@Component
class B3EmpresaResumoExtractor : AbstractHTMLExtractor() {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        private const val LETRAS_INICIO_EMPRESAS = "A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z;0;1;2;3;4;5;6;7;8;9"
        private const val DELIMITADOR_LETRA = ";"
        private const val LISTA_EMPRESAS_URL = "http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/BuscaEmpresaListada.aspx?Letra="
        private const val EMPRESA_DETALHE_URL = "http://bvmf.bmfbovespa.com.br/pt-br/mercados/acoes/empresas/ExecutaAcaoConsultaInfoEmp.asp?CodCVM="
    }

    /**
     * Faz o scrap dos dados básicos (nome, cvm e url dos detalhes) da listagem de empresas que
     * há na tabela HTML nessa página. A partir dessa listagem de empresas, nós podemos iniciar
     * o scrap das empresas com mais detalhes.
     */
    fun parseLetraUrl(letraUrl: URL): List<EmpresaResumoDTO> {
        val empresasResumo = mutableListOf<EmpresaResumoDTO>()
        this.getJsoupDocumet(letraUrl).select(SELECT_TABELA_EMPRESAS_POR_LETRA).forEach { element ->
            val name = element.select("td")[0].text()
            val cvm = element.select("td > a").attr("href").split('=')[1].trim()
            empresasResumo.add(EmpresaResumoDTO(name, cvm, URL(EMPRESA_DETALHE_URL.plus(cvm))))
        }

        return empresasResumo
    }

    /**
     * Retorna a lista de URLs completa pronta para ser chamada onde há a listagem de empresas
     * da B3 por letra de início.
     */
    private fun letrasUrlsList() = LETRAS_INICIO_EMPRESAS
            .split(DELIMITADOR_LETRA)
            .stream()
            .map { URL(LISTA_EMPRESAS_URL.plus(it)) }
            .collect(Collectors.toList())
}

data class EmpresaResumoDTO(
        val codigoCvm: String,
        val nomeEmpresa: String,
        val urlDetalhe: URL
)