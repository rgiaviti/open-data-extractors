package com.gh.rgiaviti.ods

import com.gh.rgiaviti.ods.csv.CSVParser
import com.gh.rgiaviti.ods.csv.CSVParser.parse
import java.io.File

object UFSParser {

    private const val PATH = "/home/rgiaviti/Development/Projects/Personal/Sources/open-data-scripts/censo/ufs-parser/extras/examplo_datasource_uf.csv"

    @JvmStatic
    fun main(args: Array<String>){
        val ufs = parse(File(PATH))
        ufs.forEach{ uf -> println(uf)}
    }
}