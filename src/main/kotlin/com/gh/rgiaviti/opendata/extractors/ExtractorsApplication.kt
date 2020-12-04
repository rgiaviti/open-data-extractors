package com.gh.rgiaviti.opendata.extractors

import com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.EmpresaService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExtractorsApplication

fun main(args: Array<String>) {
	val runApplication = runApplication<ExtractorsApplication>(*args)
	//runApplication.getBean(FundoImobiliarioService::class.java).extrairFundosImobiliarios()
	runApplication.getBean(EmpresaService::class.java).extrairEmpresas()
}
