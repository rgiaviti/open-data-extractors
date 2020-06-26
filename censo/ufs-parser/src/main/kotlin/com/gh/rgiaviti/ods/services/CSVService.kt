package com.gh.rgiaviti.ods.services

import com.gh.rgiaviti.ods.domains.NotasAdicionais
import com.gh.rgiaviti.ods.domains.UnidadeFederativa
import com.gh.rgiaviti.ods.services.ConfigService.Key.CSV_FILE_IN
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.File
import java.io.FileReader
import java.lang.RuntimeException
import java.nio.file.Path
import java.nio.file.Paths

object CSVService {

    private const val UF_HEADER = "UF"
    private const val SIGLA_HEADER = "Sigla"
    private const val SEDE_HEADER = "Sede"
    private const val GENTILICO_HEADER = "Gentílico"
    private const val POPULACAO_HEADER = "População"
    private const val REGIAO_HEADER = "Região"
    private const val AREA_HEADER = "Área"
    private const val NOTA_POPULACIONAL = "Nota Populacional"

    fun parse(): List<UnidadeFederativa> {
        val csvFile = File(ConfigService.getConfig(CSV_FILE_IN))

        checkCSV(csvFile)

        val ufs = mutableListOf<UnidadeFederativa>()
        val reader = FileReader(File(ConfigService.getConfig(CSV_FILE_IN)))

        reader.use {
            val csvUfs: Iterable<CSVRecord> = getCSVFormat().parse(it)
            csvUfs.forEach { csvUf ->
                ufs.add(getUF(csvUf))
            }
        }

        return ufs
    }

    private fun getUF(record: CSVRecord): UnidadeFederativa {
        val notasAdicionais = NotasAdicionais(
                record.get(NOTA_POPULACIONAL),
                emptyList()
        )

        return UnidadeFederativa(
                record.get(UF_HEADER),
                record.get(SIGLA_HEADER),
                record.get(SEDE_HEADER),
                record.get(GENTILICO_HEADER),
                record.get(REGIAO_HEADER),
                record.get(POPULACAO_HEADER).toInt(),
                record.get(AREA_HEADER).toFloat(),
                notasAdicionais
        )
    }

    private fun getCSVFormat(): CSVFormat {
        return CSVFormat.RFC4180
                .withAllowMissingColumnNames(false)
                .withIgnoreEmptyLines(true)
                .withAllowMissingColumnNames(false)
                .withSkipHeaderRecord(true)
                .withHeader(
                        UF_HEADER,
                        SIGLA_HEADER,
                        SEDE_HEADER,
                        GENTILICO_HEADER,
                        POPULACAO_HEADER,
                        REGIAO_HEADER,
                        AREA_HEADER,
                        NOTA_POPULACIONAL
                )
    }

    private fun checkCSV(csv: File) {
        if (!csv.exists() || !csv.isFile || !csv.canWrite()) {
            throw RuntimeException("arquivo csv não existe ou não é acessivel")
        }
    }
}