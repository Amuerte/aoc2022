package advent.code.util

import java.io.File
import java.nio.charset.StandardCharsets

class Parser {
    fun getFile(fileName: String):File {
        val filepath = Parser::class.java.classLoader.getResource(fileName).path
        return File(filepath)
    }

    fun parseFile(fileName: String):List<String> =
        getFile(fileName).readLines(StandardCharsets.UTF_8)

    fun <R> parseFile(fileName: String, action: (lines: List<String>) -> R): R =
        parseFile(fileName).run { action(this) }
}

fun <E> List<E>.println() {
    this.forEach { println(it) }
}




