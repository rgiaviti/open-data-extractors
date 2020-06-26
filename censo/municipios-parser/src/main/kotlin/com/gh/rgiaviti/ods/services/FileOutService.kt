package com.gh.rgiaviti.ods.services

import com.gh.rgiaviti.ods.services.ConfigService.Key.JSON_FILE_OUT
import com.gh.rgiaviti.ods.services.ConfigService.getConfig
import org.slf4j.LoggerFactory
import java.io.File

object FileOutService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun save(content: String) {
        val path = path()
        log.info("Saving file in --> {}", path.absolutePath)
        path.writeText(content)
    }

    private fun path(): File {
        return File(getConfig(JSON_FILE_OUT))
    }
}