package advent.code

import advent.code.util.Parser

interface Problem {

    fun parseFile(day: Int): List<String> = Parser().parseFile("day$day.input")

    fun part1(lines: List<String>): Any

    fun part2(lines: List<String>): Any

    fun run(args: Array<String>) {
        val day = args[0].toInt()
        val lines = parseFile(day)
        println("Part 1")
        val resultPart1 = part1(lines)
        println("\tPart 1 result: $resultPart1")

        println("Part 2")
        val resultPart2 = part2(lines)
        println("\tPart 2 result: $resultPart2")
    }
}