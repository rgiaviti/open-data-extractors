package com.gh.rgiaviti.opendata.extractors.data.economia.bacen

import com.fasterxml.jackson.databind.ObjectMapper
import com.gh.rgiaviti.opendata.extractors.common.constants.FileNames.SAIDA_BC_INFLACAO_CDI
import com.gh.rgiaviti.opendata.extractors.common.constants.FileNames.SAIDA_BC_INFLACAO_IGPM
import com.gh.rgiaviti.opendata.extractors.common.constants.FileNames.SAIDA_BC_INFLACAO_INPC
import com.gh.rgiaviti.opendata.extractors.common.constants.FileNames.SAIDA_BC_INFLACAO_IPCA
import com.gh.rgiaviti.opendata.extractors.common.constants.FileNames.SAIDA_BC_INFLACAO_SELIC
import com.gh.rgiaviti.opendata.extractors.common.constants.FileNames.SAIDA_BC_INFLACAO_TR
import com.gh.rgiaviti.opendata.extractors.common.domains.MetaInfo
import com.gh.rgiaviti.opendata.extractors.common.services.FileService
import com.gh.rgiaviti.opendata.extractors.data.economia.bacen.domains.IndiceComum
import com.gh.rgiaviti.opendata.extractors.data.economia.bacen.domains.RootIndiceBacen
import com.gh.rgiaviti.opendata.extractors.data.economia.bacen.domains.TaxaReferencial
import com.gh.rgiaviti.opendata.extractors.data.economia.bacen.restclient.BacenRestClient
import com.gh.rgiaviti.opendata.extractors.infra.AppProperties
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BacenService(
        private val appProperties: AppProperties,
        private val bacenProperties: BacenProperties,
        private val bacenRestClient: BacenRestClient,
        private val fileService: FileService,
        private val mapper: ObjectMapper
) {
    companion object {
        private val log by lazy { KotlinLogging.logger {} }
    }

    fun extractIPCA() {
        log.info { "Extraindo IPCA do BACEN..." }
        val ipcas = this.bacenRestClient.getIndiceComum(IndiceBacen.IPCA).sortedByDescending { it.data }
        val ipcasMetaInfo = this.newIPCAMetaInfo(ipcas.size, ipcas.first())
        val rootIpcaBacen = RootIndiceBacen(metaInfo = ipcasMetaInfo, indices = ipcas)
        this.fileService.save(this.mapper.writeValueAsString(rootIpcaBacen), out().plus(SAIDA_BC_INFLACAO_IPCA))
    }

    fun extractINPC() {
        log.info { "Extraindo INPC do BACEN..." }
        val inpcs = this.bacenRestClient.getIndiceComum(IndiceBacen.INPC).sortedByDescending { it.data }
        val inpcsMetaInfo = this.newINPCMetaInfo(inpcs.size, inpcs.first())
        val rootInpcBacen = RootIndiceBacen(metaInfo = inpcsMetaInfo, indices = inpcs)
        this.fileService.save(this.mapper.writeValueAsString(rootInpcBacen), out().plus(SAIDA_BC_INFLACAO_INPC))
    }

    fun extractIGPM() {
        log.info { "Extraindo IGPM do BACEN..." }
        val igpms = this.bacenRestClient.getIndiceComum(IndiceBacen.IGPM).sortedByDescending { it.data }
        val igpmsMetaInfo = this.newIGPMMetaInfo(igpms.size, igpms.first())
        val rootIgpmBacen = RootIndiceBacen(metaInfo = igpmsMetaInfo, indices = igpms)
        this.fileService.save(this.mapper.writeValueAsString(rootIgpmBacen), out().plus(SAIDA_BC_INFLACAO_IGPM))
    }

    fun extractCDI() {
        log.info { "Extraindo CDI do BACEN..." }
        val cdis = this.bacenRestClient.getIndiceComum(IndiceBacen.CDI).sortedByDescending { it.data }
        val cdisMetaInfo = this.newCDIMetaInfo(cdis.size, cdis.first())
        val rootCdiBacen = RootIndiceBacen(metaInfo = cdisMetaInfo, indices = cdis)
        this.fileService.save(this.mapper.writeValueAsString(rootCdiBacen), out().plus(SAIDA_BC_INFLACAO_CDI))
    }

    fun extractSelic() {
        log.info { "Extraindo SELIC do BACEN..." }
        val selics = this.bacenRestClient.getIndiceComum(IndiceBacen.SELIC).sortedByDescending { it.data }
        val selicsMetaInfo = this.newSELICMetaInfo(selics.size, selics.first())
        val rootSelicBacen = RootIndiceBacen(metaInfo = selicsMetaInfo, indices = selics)
        this.fileService.save(this.mapper.writeValueAsString(rootSelicBacen), out().plus(SAIDA_BC_INFLACAO_SELIC))
    }

    fun extractTRs() {
        log.info { "Extraindo TR do BACEN..." }
        val trs = this.bacenRestClient.getTaxaReferencial().sortedByDescending { it.data }
        val trsMetaInfo = this.newTRMetaInfo(trs.size, trs.first())
        val rootTrsBacen = RootIndiceBacen(metaInfo = trsMetaInfo, indices = trs)
        this.fileService.save(this.mapper.writeValueAsString(rootTrsBacen), out().plus(SAIDA_BC_INFLACAO_TR))

    }

    /**
     * Retorna o meta-info dos fundos imobiliários preenchido com os dados necessários para colocar
     * no JSON.
     */
    private fun newIPCAMetaInfo(contagem: Int, indice: IndiceComum): MetaInfo {
        return MetaInfo(
                status = "ATIVO",
                dataUltimaAtualizacao = LocalDate.now(),
                dataReferencia = indice.data,
                versao = this.appProperties.versao,
                contagem = contagem,
                website = bacenProperties.ipcaSite,
                fontes = bacenProperties.ipcaFontes
        )
    }

    private fun newINPCMetaInfo(contagem: Int, indice: IndiceComum): MetaInfo {
        return MetaInfo(
                status = "ATIVO",
                dataUltimaAtualizacao = LocalDate.now(),
                dataReferencia = indice.data,
                versao = this.appProperties.versao,
                contagem = contagem,
                website = bacenProperties.inpcSite,
                fontes = bacenProperties.inpcFontes
        )
    }

    private fun newIGPMMetaInfo(contagem: Int, indice: IndiceComum): MetaInfo {
        return MetaInfo(
                status = "ATIVO",
                dataUltimaAtualizacao = LocalDate.now(),
                dataReferencia = indice.data,
                versao = this.appProperties.versao,
                contagem = contagem,
                website = bacenProperties.igpmSite,
                fontes = bacenProperties.igpmFontes
        )
    }

    private fun newCDIMetaInfo(contagem: Int, indice: IndiceComum): MetaInfo {
        return MetaInfo(
                status = "ATIVO",
                dataUltimaAtualizacao = LocalDate.now(),
                dataReferencia = indice.data,
                versao = this.appProperties.versao,
                contagem = contagem,
                website = bacenProperties.cdiSite,
                fontes = bacenProperties.cdiFontes
        )
    }

    private fun newSELICMetaInfo(contagem: Int, indice: IndiceComum): MetaInfo {
        return MetaInfo(
                status = "ATIVO",
                dataUltimaAtualizacao = LocalDate.now(),
                dataReferencia = indice.data,
                versao = this.appProperties.versao,
                contagem = contagem,
                website = bacenProperties.selicSite,
                fontes = bacenProperties.selicFontes
        )
    }

    private fun newTRMetaInfo(contagem: Int, indice: TaxaReferencial): MetaInfo {
        return MetaInfo(
                status = "ATIVO",
                dataUltimaAtualizacao = LocalDate.now(),
                dataReferencia = indice.dataFim,
                versao = this.appProperties.versao,
                contagem = contagem,
                website = bacenProperties.trSite,
                fontes = bacenProperties.trFontes
        )
    }

    private fun out(): String = this.appProperties.dirSaidaArquivos
}