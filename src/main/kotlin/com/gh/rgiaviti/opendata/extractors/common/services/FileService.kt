package com.gh.rgiaviti.opendata.extractors.common.services

import com.gh.rgiaviti.opendata.extractors.infra.exceptions.ErroSalvarArquivoException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.File
import java.nio.charset.Charset

@Service
class FileService {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        const val DEFAULT_CHARSET = "UTF-8"
    }

    fun save(content: String, charset: Charset, path: String) {
        this.save(content.toByteArray(charset), File(path))
    }

    fun save(content: String, path: String) {
        this.save(content.toByteArray(Charset.forName(DEFAULT_CHARSET)), File(path))
    }

    fun save(content: ByteArray, file: File) {
        log.info("::: Salvando arquivo em: {}", file.absolutePath)

        if (file.exists()) { file.delete() }
        file.parentFile.mkdirs()
        file.createNewFile()
        this.checkFile(content, file)
        file.writeBytes(content)
    }

    private fun checkFile(content: ByteArray, file: File) {
        if (content.isEmpty()) {
            log.error(":: Impossível salvar arquivo. Conteúdo vazio!")
            throw ErroSalvarArquivoException("conteúdo vazio")
        }

        if (!file.canWrite()) {
            log.error(":: Impossível salvar arquivo. Sem permissão de escrita!")
            throw ErroSalvarArquivoException("sem permissão de escrita")
        }

        if (!file.isFile) {
            log.error(":: Impossível salvar arquivo. O caminho especificado não é um arquivo")
            throw ErroSalvarArquivoException("o caminho especificado não é um arquivo")
        }
    }
}