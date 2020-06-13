package com.gh.rgiaviti.ods

import com.gh.rgiaviti.ods.b3parser.B3Parser
import org.slf4j.LoggerFactory
import java.net.URL

object B3CompaniesExtractor {

    private val log = LoggerFactory.getLogger(javaClass)

    @JvmStatic
    fun main(args: Array<String>) {
        B3Parser.scrap()
    }
}