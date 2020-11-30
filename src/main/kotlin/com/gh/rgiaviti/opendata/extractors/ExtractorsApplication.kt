package com.gh.rgiaviti.opendata.extractors

import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.FundoImobiliarioService
import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient.B3FiiRestClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExtractorsApplication

fun main(args: Array<String>) {
	val runApplication = runApplication<ExtractorsApplication>(*args)
	//runApplication.getBean(B3FiiClient::class.java).listarFundosImobiliarios()
	//runApplication.getBean(B3FiiRestClient::class.java).getDetalhe("alzr")
	runApplication.getBean(FundoImobiliarioService::class.java).extrairFundosImobiliarios()
}
