package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis

import com.fasterxml.jackson.databind.ObjectMapper
import com.gh.rgiaviti.opendata.extractors.common.domains.MetaInfo
import com.gh.rgiaviti.opendata.extractors.common.services.FileService
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.domains.FundoImobiliario
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.domains.RootFundoImobiliario
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient.B3FIIRestClient
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient.FundoImobiliarioDetailRes
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient.FundoImobiliarioResumeRes
import com.gh.rgiaviti.opendata.extractors.infra.AppProperties
import com.gh.rgiaviti.opendata.extractors.common.constants.FileNames.SAIDA_B3_FIIS
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.concurrent.TimeUnit

/**
 * Classe de serviço dos Fundos Imobiliários responsável por orquestrar a obtenção automática dos dados a respeito dos fundos imobiliários,
 * serializa-los para JSON e salvar em arquivo.
 *
 * @since 1.0.0
 * @author Ricardo Giaviti
 */
@Service
class FundoImobiliarioService(
        private val b3FIIRestClient: B3FIIRestClient,
        private val fileService: FileService,
        private val appProperties: AppProperties,
        private val fiiProperties: FundoImobiliariosProperties,
        private val mapper: ObjectMapper) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        private const val TIPO_EXECUCAO = "stream" // stream ou parallelStream
        private const val WAIT_TIME_BETWEEN_REQUESTS = 3L //segundos
    }

    fun extractFundosImobiliarios() {
        val fiis = mutableListOf<FundoImobiliario>()

        log.info(":: Buscando a listagem de Fundos Imobiliários na B3...")
        val listaFiisRes = this.b3FIIRestClient.listFundosImobiliarios()

        log.info(":: Encontrados {} Fundos Imobiliários", listaFiisRes.fundosImobiliarios!!.size)
        log.info(":: Buscando detalhes dos FIIs um a um...")

        val listaFiisResStream = when (TIPO_EXECUCAO) {
            "stream" -> listaFiisRes.fundosImobiliarios.stream()
            "parallelStream" -> listaFiisRes.fundosImobiliarios.parallelStream()
            else -> {
                log.error(":: Tipo de execução incorreta {}. Assumindo a default stream()", TIPO_EXECUCAO)
                listaFiisRes.fundosImobiliarios.stream()
            }
        }

        listaFiisResStream.forEach { fiiRes ->
            log.info(":: Buscando detalhes dos Fundo {}", fiiRes.codigo!!.toUpperCase())
            val detalheFiiRes = this.b3FIIRestClient.getFundoImobiliarioDetail(fiiRes.codigo)

            if (detalheFiiRes.detalhes?.ticker != null) {
                fiis.add(this.newFundoImobiliario(fiiRes, detalheFiiRes.detalhes))
            } else {
                log.info(":: FII {} não foi adicionado por não ter código de negociação (ticker)", fiiRes.nomePregao)
            }

            log.info(":: Aguardando {} segundos até buscar próximo fundo", WAIT_TIME_BETWEEN_REQUESTS)
            Thread.sleep(TimeUnit.SECONDS.toMillis(WAIT_TIME_BETWEEN_REQUESTS))
        }

        val metaInfo = this.newMetaInfo(fiis.size)
        val rootFundoImobiliario = RootFundoImobiliario(metaInfo, fiis)

        log.info(":: Salvando JSON com os Fundos Imobiliários em {}", this.appProperties.dirSaidaArquivos.plus(SAIDA_B3_FIIS))
        this.fileService.save(mapper.writeValueAsString(rootFundoImobiliario), this.appProperties.dirSaidaArquivos.plus(SAIDA_B3_FIIS))
    }

    /**
     * Instancia e retorna um novo fundo imobiliário com base no resumo e detalhes vindo pelos parâmetros
     */
    private fun newFundoImobiliario(fiiResume: FundoImobiliarioResumeRes, fiiDetail: FundoImobiliarioDetailRes): FundoImobiliario {
        return FundoImobiliario(
                nome = fiiResume.nomeCompleto.orEmpty(),
                nomePregao = fiiResume.nomePregao.orEmpty(),
                ticker = fiiDetail.ticker.orEmpty(),
                codigos = fiiDetail.codigos.orEmpty(),
                cnpj = fiiDetail.cnpj.orEmpty(),
                site = fiiDetail.site.orEmpty()
        )
    }

    /**
     * Retorna o meta-info dos fundos imobiliários preenchido com os dados necessários para colocar
     * no JSON.
     */
    private fun newMetaInfo(contagemFiis: Int): MetaInfo {
        return MetaInfo(
                status = "ATIVO",
                dataUltimaAtualizacao = LocalDate.now(),
                dataReferencia = LocalDate.now(),
                versao = this.appProperties.versao,
                contagem = contagemFiis,
                website = fiiProperties.site,
                fontes = fiiProperties.fontes
        )
    }
}