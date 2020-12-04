package com.gh.rgiaviti.opendata.extractors.common

import mu.KotlinLogging
import java.util.concurrent.TimeUnit

object Helper {

    private val log by lazy { KotlinLogging.logger {} }

    fun waitFixedTime(seconds: Int) {
        log.info(" :: Aguardando por $seconds segundos até o próximo FII")
        Thread.sleep(TimeUnit.SECONDS.toMillis(seconds.toLong()))
    }

    fun waitRandomTime(end: Int) {
        this.waitRandomTime(initial = 0, end)
    }

    fun waitRandomTime(initial: Int, end: Int) {
        val waitTime = (initial..end).random().toLong()
        log.info(" :: Aguardando por $waitTime segundos até o próximo FII")
        Thread.sleep(TimeUnit.SECONDS.toMillis(waitTime))
    }
}