package com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis

import com.gh.rgiaviti.opendata.extractors.data.economia.b3.fiis.restclient.B3FiiRestClient
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class FundoImobiliarioService(private val b3FiiRestClient: B3FiiRestClient) {

    companion object {
        private val log by lazy { KotlinLogging.logger {} }
    }

}