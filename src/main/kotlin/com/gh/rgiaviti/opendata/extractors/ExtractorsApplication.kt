package com.gh.rgiaviti.opendata.extractors

import com.gh.rgiaviti.opendata.extractors.common.services.ExtractorsService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExtractorsApplication

fun main(args: Array<String>) {
    val context = runApplication<ExtractorsApplication>(*args)
    context.getBean(ExtractorsService::class.java).extractInformations()
}
