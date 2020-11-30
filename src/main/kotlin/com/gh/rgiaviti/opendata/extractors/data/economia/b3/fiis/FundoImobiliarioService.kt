package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis

import com.fasterxml.jackson.databind.ObjectMapper
import com.gh.rgiaviti.opendata.extractors.common.domains.MetaInfo
import com.gh.rgiaviti.opendata.extractors.common.services.FileService
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.domains.FundoImobiliario
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.domains.RootFundoImobiliario
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient.B3FiiRestClient
import com.gh.rgiaviti.opendata.extractors.infra.AppProperties
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@Service
class FundoImobiliarioService(
        private val b3FiiRestClient: B3FiiRestClient,
        private val fileService: FileService,
        private val appProperties: AppProperties,
        private val fiiProperties: FundoImobiliariosProperties,
        private val mapper: ObjectMapper) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        const val NOME_ARQUIVO = "fundos-imobiliarios-listados.json"
        const val WAIT_TIME_BETWEEN_REQUESTS = 3L //segundos
    }

    fun extrairFundosImobiliarios() {
        val fiis = mutableListOf<FundoImobiliario>()

        log.info(":: Buscando a listagem de Fundos Imobiliários na B3...")
        val listaFiisRes = this.b3FiiRestClient.listarFundosImobiliarios()

        log.info(":: Encontrados {} Fundos Imobiliários", listaFiisRes.fundosImobiliarios!!.size)
        log.info(":: Buscando detalhes dos FIIs um a um...")

        listaFiisRes.fundosImobiliarios.forEach { fiiRes ->
            log.info(":: Buscando detalhes dos Fundo {}", fiiRes.codigo!!.toUpperCase())
            val detalheFiiRes = this.b3FiiRestClient.getDetalhe(fiiRes.codigo)

            fiis.add(FundoImobiliario(
                    nome = fiiRes.nomeCompleto!!,
                    nomePregao = fiiRes.nomePregao!!,
                    ticker = detalheFiiRes.detalhes!!.ticker.orEmpty(),
                    codigos = detalheFiiRes.detalhes.codigos.orEmpty(),
                    cnpj = detalheFiiRes.detalhes.cnpj!!,
                    site = detalheFiiRes.detalhes.site!!
            ))

            log.info(":: Aguardando {} segundos até buscar próximo fundo", WAIT_TIME_BETWEEN_REQUESTS)
            Thread.sleep(TimeUnit.SECONDS.toMillis(WAIT_TIME_BETWEEN_REQUESTS))
        }

        val metaInfo = MetaInfo(
                status = "ATIVO",
                dataUltimaAtualizacao = LocalDate.now(),
                dataReferencia = LocalDate.now(),
                versao = this.appProperties.versao,
                contagem = fiis.size,
                website = fiiProperties.site,
                fontes = fiiProperties.fontes
        )

        val rootFundoImobiliario = RootFundoImobiliario(metaInfo, fiis)

        log.info(":: Salvando JSON com os Fundos Imobiliários em {}", this.appProperties.dirSaidaArquivos.plus(NOME_ARQUIVO))
        this.fileService.save(mapper.writeValueAsString(rootFundoImobiliario), this.appProperties.dirSaidaArquivos.plus(NOME_ARQUIVO))
    }
}