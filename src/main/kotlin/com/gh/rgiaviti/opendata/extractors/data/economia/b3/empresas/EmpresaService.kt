package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas

import com.fasterxml.jackson.databind.ObjectMapper
import com.gh.rgiaviti.opendata.extractors.common.Helper
import com.gh.rgiaviti.opendata.extractors.common.constants.FileNames.SAIDA_B3_EMPRESAS
import com.gh.rgiaviti.opendata.extractors.common.constants.FileNames.SAIDA_B3_FIIS
import com.gh.rgiaviti.opendata.extractors.common.domains.MetaInfo
import com.gh.rgiaviti.opendata.extractors.common.services.FileService
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.domains.Empresa
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.domains.RootEmpresa
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.B3EmpresaDetailExtractor
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.B3EmpresaResumeExtractor
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.EmpresaDTO
import com.gh.rgiaviti.opendata.extractors.infra.AppProperties
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class EmpresaService(
        private val empresaResumeExtractor: B3EmpresaResumeExtractor,
        private val empresaDetailExtractor: B3EmpresaDetailExtractor,
        private val fileService: FileService,
        private val mapper: ObjectMapper,
        private val empresaProperties: EmpresaProperties,
        private val appProperties: AppProperties) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        private const val TIPO_EXECUCAO = "parallelStream" // stream ou parallelStream
        private val WAIT_TIME_BETWEEN_REQUESTS = 1L..4L //segundos
    }

    fun extractEmpresas() {
        val empresas = mutableListOf<Empresa>()
        log.info(":: Buscando o resumo das empresas listadas na B3...")

        // Buscamos o resumo das empresas pela página da B3 na web
        val empresasResumes = this.empresaResumeExtractor.extractEmpresaResume()
        log.info(":: Encontramos {} empresas listadas.", empresasResumes.size)
        log.info(":: Vamos buscar os detalhes delas uma a uma. Pode demorar um pouco")

        //-----------------------------------------------------------------------------
        // Decide o modo de streaming do response. Se stream o parallelStreal
        //-----------------------------------------------------------------------------
        val empresasStream = when (TIPO_EXECUCAO) {
            "stream" -> empresasResumes.stream()
            "parallelStream" -> empresasResumes.parallelStream()
            else -> {
                log.error(":: Tipo de execução incorreta {}. Assumindo a default 'stream()'", TIPO_EXECUCAO)
                empresasResumes.stream()
            }
        }

        //-----------------------------------------------------------------------------
        // Para cada empresa retornada no response é buscado os detalhes dela, então
        // criado o objeto e adicionado na lista de empresas.
        //-----------------------------------------------------------------------------
        empresasStream.forEach { empresaResume ->
            log.info(":: Buscando detalhes da empresa: {}", empresaResume.nomeEmpresa)
            val empresaDTO = this.empresaDetailExtractor.extractEmpresaDetail(empresaResume)
            empresas.add(this.newEmpresa(empresaDTO))
            Helper.waitRandomTime(WAIT_TIME_BETWEEN_REQUESTS)
        }

        // Salva o JSON em arquivo e ja era
        this.saveFile(empresas)
    }

    /**
     * Método que monta o JSON final e salva o arquivo em disco para depois subirmos checarmos e subirmos
     * manual no github
     */
    private fun saveFile(empresas: List<Empresa>) {
        val metaInfo = this.newMetaInfo(empresas.size)
        val rootEmpresa = RootEmpresa(metaInfo, empresas.sortedBy { it.nomePregao })

        log.info(":: Salvando JSON com as Empresas em {}", this.appProperties.dirSaidaArquivos.plus(SAIDA_B3_EMPRESAS))
        this.fileService.save(mapper.writeValueAsString(rootEmpresa), this.appProperties.dirSaidaArquivos.plus(SAIDA_B3_EMPRESAS))
    }

    /**
     * Instancia e retorna um novo fundo imobiliário com base no resumo e detalhes vindo pelos parâmetros
     */
    private fun newEmpresa(empresaDTO: EmpresaDTO): Empresa {
        return Empresa(
                nome = empresaDTO.nome,
                cvm = empresaDTO.cvm,
                tickers = empresaDTO.tickers,
                nomePregao = empresaDTO.nomePregao,
                cnpj = empresaDTO.cnpj,
                atividade = empresaDTO.atividade,
                setores = empresaDTO.setores,
                site = empresaDTO.site
        )
    }

    /**
     * Retorna o meta-info das empresas preenchido com os dados necessários para colocar
     * no JSON.
     */
    private fun newMetaInfo(contagemEmpresas: Int): MetaInfo {
        return MetaInfo(
                status = "ATIVO",
                dataUltimaAtualizacao = LocalDate.now(),
                dataReferencia = LocalDate.now(),
                versao = this.appProperties.versao,
                contagem = contagemEmpresas,
                website = empresaProperties.site,
                fontes = empresaProperties.fontes
        )
    }
}