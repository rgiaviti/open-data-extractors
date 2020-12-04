package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas

import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.B3EmpresaDetailExtractor
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.B3EmpresaResumeExtractor
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class EmpresaService(
        private val empresaResumeExtractor: B3EmpresaResumeExtractor,
        private val empresaDetailExtractor: B3EmpresaDetailExtractor) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
    }

    fun extrairEmpresas() {
       
    }
}