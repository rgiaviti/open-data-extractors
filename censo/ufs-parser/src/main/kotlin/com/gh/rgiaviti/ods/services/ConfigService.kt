package com.gh.rgiaviti.ods.services

import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object ConfigService {

    private const val SEPARATOR_CONFIG = ';'
    private const val DATE_PATTERN = "yyyy-MM-dd"
    private const val PROPERTIES_FILE = "configs.properties"


    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private val configs: Properties
        get() {
            javaClass.classLoader.getResourceAsStream(PROPERTIES_FILE).use {
                return Properties().apply { load(InputStreamReader(it, Charsets.UTF_8)) }
            }
        }

    fun getConfig(key: Key): String {
        return configs.getProperty(key.toString())
    }

    fun getArray(key: Key): List<String> {
        return configs.getProperty(key.toString()).split(SEPARATOR_CONFIG).toList()
    }

    fun getDate(key: Key): LocalDate {
        val strConfig = configs.getProperty(key.toString())
        return LocalDate.parse(strConfig, DateTimeFormatter.ofPattern(DATE_PATTERN))
    }

    enum class Key(private val key: String) {
        CSV_FILE_IN("csv-file-in"),
        JSON_FILE_OUT("json-file-out"),
        METAINFO_REFERENCIA("metainfo-data-referencia"),
        METAINFO_VERSION("metainfo-version"),
        METAINFO_SITE("metainfo-site"),
        METAINFO_FONTES("metainfo-fontes");


        override fun toString(): String {
            return key;
        }
    }
}