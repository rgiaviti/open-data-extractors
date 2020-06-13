package com.gh.rgiaviti.ods

import com.gh.rgiaviti.ods.csv.CSVParser.parse
import com.gh.rgiaviti.ods.domains.MetaInfo
import com.gh.rgiaviti.ods.domains.OpenDataUF
import com.gh.rgiaviti.ods.json.JSONSerialize.toJSON
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object UFSParser {

    private const val WEBSITE = "https://github.com/rgiaviti/open-data"
    private val FONTES = listOf("IBGE", "Constituição Federal do Brasil")

    @JvmStatic
    fun main(args: Array<String>) {
        val path = args[0]
        val version = args[1]
        val dataUltimaReferencia = SimpleDateFormat("yyyy-MM-dd").parse(args[2])

        val ufs = parse(File(path))
        val metaInfo = metaInfo(dataUltimaReferencia, version)
        val openDataUF = OpenDataUF(metaInfo, ufs)

        // Serializa para JSON
        val jsonUfs = toJSON(openDataUF)

        // Saída para arquivo
        if (args.size == 4 && args[3].isNotEmpty()) {
            File(args[3]).writeText(jsonUfs)
        }

        // Saída para console
        println(jsonUfs)
    }

    private fun metaInfo(dataUltimaReferencia: Date, versao: String): MetaInfo {
        return MetaInfo(
            Date(),
            dataUltimaReferencia,
            versao,
            WEBSITE,
            FONTES
        )
    }
}