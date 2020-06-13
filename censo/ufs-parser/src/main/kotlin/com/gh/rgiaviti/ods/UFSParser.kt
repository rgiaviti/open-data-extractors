package com.gh.rgiaviti.ods

import com.gh.rgiaviti.ods.csv.CSVParser.parse
import com.gh.rgiaviti.ods.domains.MetaInfo
import com.gh.rgiaviti.ods.domains.OpenDataUF
import com.gh.rgiaviti.ods.json.JSONSerialize
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

object UFSParser {

    private const val WEBSITE = "https://github.com/rgiaviti/open-data/tree/master/censo"
    private val FONTES = listOf("IBGE", "Constituição Federal do Brasil")
    private val dateParser = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @JvmStatic
    fun main(args: Array<String>) {
        // Checa a presença dos argumentos
        checkArguments(args)

        val path = args[0]
        val version = args[1]
        val dataUltimaReferencia = LocalDate.parse(args[2], dateParser)

        // Parses
        val ufs = parse(File(path))
        val metaInfo = metaInfo(dataUltimaReferencia, version)
        val openDataUF = OpenDataUF(metaInfo, ufs)

        // Serializa para JSON
        val jsonUfs = JSONSerialize().toJSON(openDataUF)

        // Saída para arquivo
        if (args.size == 4 && args[3].isNotEmpty()) {
            File(args[3]).writeText(jsonUfs)
        }

        // Saída para console
        println(jsonUfs)
    }

    private fun metaInfo(dataUltimaReferencia: LocalDate, versao: String): MetaInfo {
        return MetaInfo(
            LocalDate.now(),
            dataUltimaReferencia,
            versao,
            WEBSITE,
            FONTES
        )
    }

    private fun checkArguments(args: Array<String>) {
        if (args.size < 3) {
            println("Erro - Número de argumentos inválido.")
            println("Necessário pelo menos 3 argumentos")
            println("Exemplo: <path csv> <versao ufs> <data de ref. yyyy-MM-dd> [opcional - arquivo saida]")
            exitProcess(1)
        }

        val planilha = args[0]
        if (!File(planilha).isFile || !File(planilha).canRead()) {
            println("O CSV passado não é arquivo ou não permite leitura")
        }
    }
}