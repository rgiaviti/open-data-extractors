package com.gh.rgiaviti.ods.b3ce.extractors

import com.gh.rgiaviti.ods.b3ce.services.ConfigService
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import java.net.SocketTimeoutException
import kotlin.random.Random

abstract class Extractor {

    //--------------------------------------------------
    // Logger
    //--------------------------------------------------
    private val log = LoggerFactory.getLogger(javaClass)

    private val seedDelay = 2000L

    /**
     * Efetua o request para a URL do parâmetro e retorna o objeto Document que representa
     * a página retornada em HTML pronta para ser parseada.
     *
     * @param url é a url que será feito o request
     * @return documento html para parsing
     */
    protected fun html(url: String): Document {
        val userAgent = ConfigService.getConfig(ConfigService.Key.BROWSER_USER_AGENT)
        val timeout = ConfigService.getConfig(ConfigService.Key.CONNECTION_TIMEOUT).toInt()
        val maxTries = ConfigService.getConfig(ConfigService.Key.MAX_TRIES_REQUEST).toInt()
        var attempt = 0

        do {
            try {
                attempt++

                return Jsoup.connect(url)
                        .userAgent(userAgent)
                        .timeout(timeout)
                        .ignoreHttpErrors(false)
                        .followRedirects(true)
                        .get()

            } catch (e: SocketTimeoutException) {
                log.warn("Houve um timeout.")
            }
        } while (attempt < maxTries)

        log.error("Máximo de tentativas atingido para -> {}", url)
        log.error("Tentativas: {}/{}", attempt, maxTries)
        log.error("Interrompendo a execução")

        throw SocketTimeoutException()
    }

    /**
     * Gera um tempo aleatório baseado no na configuração de delay que está nos properties. Isso
     * é para não sobrecarregar de requests a página da B3.
     */
    protected fun randomDelay(time: Long): Long {
        return Random.nextLong(time, time + seedDelay)
    }
}