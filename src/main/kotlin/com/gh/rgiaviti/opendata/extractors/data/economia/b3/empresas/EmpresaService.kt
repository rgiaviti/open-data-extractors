package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas

import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient.B3EmpresaHTMLClient
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class EmpresaService(private val empresaHTMLClient: B3EmpresaHTMLClient) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
    }

    fun extrairEmpresas() {
        print(this.empresaHTMLClient.detalhar())
    }
}