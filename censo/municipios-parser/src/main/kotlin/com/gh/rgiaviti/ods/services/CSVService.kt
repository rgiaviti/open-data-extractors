package com.gh.rgiaviti.ods.services

import com.gh.rgiaviti.ods.domains.Municipio
import com.gh.rgiaviti.ods.domains.NotasAdicionais
import com.gh.rgiaviti.ods.services.ConfigService.Key.CSV_FILE_IN
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.File
import java.io.FileReader
import kotlin.system.exitProcess

object CSVService {

    private const val SIGLA_UF_HEADER = "UF"
    private const val CODIGO_IBGE_UF_HEADER = "Código UF"
    private const val CODIGO_IBGE_MUNICIPIO_HEADER = "Código Município"
    private const val NOME_HEADER = "Nome"
    private const val POPULACAO_ESTIMADA_HEADER = "População Estimada"
    private const val NOTAS_ADICIONAIS_HEADER = "Notas Adicionais"

    fun parse(): List<Municipio> {
        val csvFile = File(ConfigService.getConfig(CSV_FILE_IN))
        println("Fazendo parse do arquivo CSV: ${csvFile.absolutePath}")
        checkCSV(csvFile)

        val municipios = mutableListOf<Municipio>()
        val reader = FileReader(File(ConfigService.getConfig(CSV_FILE_IN)))

        reader.use {
            val csvMunicipios: Iterable<CSVRecord> = getCSVFormat().parse(it)
            csvMunicipios.forEach { csvMunicipio ->
                municipios.add(getMunicipio(csvMunicipio))
            }
        }

        return municipios
    }

    private fun getMunicipio(record: CSVRecord): Municipio {
        val notasAdicionais = NotasAdicionais(
                record.get(NOTAS_ADICIONAIS_HEADER),
                emptyList()
        )

        try {
            return Municipio(
                    record.get(SIGLA_UF_HEADER),
                    record.get(CODIGO_IBGE_UF_HEADER),
                    record.get(CODIGO_IBGE_MUNICIPIO_HEADER),
                    record.get(NOME_HEADER),
                    record.get(POPULACAO_ESTIMADA_HEADER).toInt(),
                    notasAdicionais
            )
        } catch (ex: NumberFormatException) {
            println("Erro na conversão da população para número do Municio: ${record.get(NOME_HEADER)} - ${record.get(SIGLA_UF_HEADER)}")
            println("População: ${record.get(POPULACAO_ESTIMADA_HEADER)}")
            ex.printStackTrace()
            exitProcess(1)
        }
    }

    private fun getCSVFormat(): CSVFormat {
        return CSVFormat.RFC4180
                .withAllowMissingColumnNames(false)
                .withIgnoreEmptyLines(true)
                .withAllowMissingColumnNames(false)
                .withSkipHeaderRecord(true)
                .withHeader(
                        SIGLA_UF_HEADER,
                        CODIGO_IBGE_UF_HEADER,
                        CODIGO_IBGE_MUNICIPIO_HEADER,
                        NOME_HEADER,
                        POPULACAO_ESTIMADA_HEADER,
                        NOTAS_ADICIONAIS_HEADER
                )
    }

    private fun checkCSV(csv: File) {
        if (!csv.exists() || !csv.isFile || !csv.canWrite()) {
            throw RuntimeException("arquivo csv não existe ou não é acessivel")
        }
    }
}