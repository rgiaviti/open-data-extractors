package com.gh.rgiaviti.opendata.extractors.common.services

import com.gh.rgiaviti.opendata.extractors.data.censo.municipios.MunicipioService
import com.gh.rgiaviti.opendata.extractors.data.censo.ufs.UFService
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.EmpresaService
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.FundoImobiliarioService
import com.gh.rgiaviti.opendata.extractors.data.economia.bacen.BacenService
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ExtractorsService(
        private val fiiService: FundoImobiliarioService,
        private val empresaService: EmpresaService,
        private val ufService: UFService,
        private val municipioService: MunicipioService,
        private val bacenService: BacenService
) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
        private const val EXTRACT_B3_EMPRESA = false
        private const val EXTRACT_B3_FII = false
        private const val EXTRACT_CENSO_MUNICIPIOS = true
        private const val EXTRACT_CENSO_UFS = true
        private const val EXTRACT_BACEN_IPCA = true
        private const val EXTRACT_BACEN_INPC = true
        private const val EXTRACT_BACEN_IGPM = true
        private const val EXTRACT_BACEN_CDI = true
        private const val EXTRACT_BACEN_SELIC = true
        private const val EXTRACT_BACEN_TR = true
    }

    fun extractInformations() {
        this.extractInformationB3()
        this.extractInformationBacen()
    }

    private fun extractInformationBacen() {
        if (EXTRACT_BACEN_IPCA) {
            this.bacenService.extractIPCA()
        }

        if (EXTRACT_BACEN_INPC) {
            this.bacenService.extractINPC()
        }

        if (EXTRACT_BACEN_IGPM) {
            this.bacenService.extractIGPM()
        }

        if (EXTRACT_BACEN_CDI) {
            this.bacenService.extractCDI()
        }

        if (EXTRACT_BACEN_SELIC) {
            this.bacenService.extractSelic()
        }

        if (EXTRACT_BACEN_TR) {
            this.bacenService.extractTRs()
        }
    }

    private fun extractInformationB3() {
        if (EXTRACT_B3_EMPRESA) {
            this.empresaService.extractEmpresas()
        }

        if (EXTRACT_B3_FII) {
            this.fiiService.extractFundosImobiliarios()
        }
    }
}