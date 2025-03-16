package advent.code.day10

import advent.code.Problem


val input1 = listOf(
    "addx 15",
    "addx -11",
    "addx 6",
    "addx -3",
    "addx 5",
    "addx -1",
    "addx -8",
    "addx 13",
    "addx 4",
    "noop",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx -35",
    "addx 1",
    "addx 24",
    "addx -19",
    "addx 1",
    "addx 16",
    "addx -11",
    "noop",
    "noop",
    "addx 21",
    "addx -15",
    "noop",
    "noop",
    "addx -3",
    "addx 9",
    "addx 1",
    "addx -3",
    "addx 8",
    "addx 1",
    "addx 5",
    "noop",
    "noop",
    "noop",
    "noop",
    "noop",
    "addx -36",
    "noop",
    "addx 1",
    "addx 7",
    "noop",
    "noop",
    "noop",
    "addx 2",
    "addx 6",
    "noop",
    "noop",
    "noop",
    "noop",
    "noop",
    "addx 1",
    "noop",
    "noop",
    "addx 7",
    "addx 1",
    "noop",
    "addx -13",
    "addx 13",
    "addx 7",
    "noop",
    "addx 1",
    "addx -33",
    "noop",
    "noop",
    "noop",
    "addx 2",
    "noop",
    "noop",
    "noop",
    "addx 8",
    "noop",
    "addx -1",
    "addx 2",
    "addx 1",
    "noop",
    "addx 17",
    "addx -9",
    "addx 1",
    "addx 1",
    "addx -3",
    "addx 11",
    "noop",
    "noop",
    "addx 1",
    "noop",
    "addx 1",
    "noop",
    "noop",
    "addx -13",
    "addx -19",
    "addx 1",
    "addx 3",
    "addx 26",
    "addx -30",
    "addx 12",
    "addx -1",
    "addx 3",
    "addx 1",
    "noop",
    "noop",
    "noop",
    "addx -9",
    "addx 18",
    "addx 1",
    "addx 2",
    "noop",
    "noop",
    "addx 9",
    "noop",
    "noop",
    "noop",
    "addx -1",
    "addx 2",
    "addx -37",
    "addx 1",
    "addx 3",
    "noop",
    "addx 15",
    "addx -21",
    "addx 22",
    "addx -6",
    "addx 1",
    "noop",
    "addx 2",
    "addx 1",
    "noop",
    "addx -10",
    "noop",
    "noop",
    "addx 20",
    "addx 1",
    "addx 2",
    "addx 2",
    "addx -6",
    "addx -11",
    "noop",
    "noop",
    "noop")

class CathodeRayTube:Problem {

    val rows = 6
    val columns = 40

    fun parseLine(line: String): Pair<String, Int> {
        return line.split(" ").let { it[0] to (if (it.size > 1) it[1].toInt() else 0) }
    }

    override fun part1(lines: List<String>): Any {
        var x = 1
        val cycles = mutableListOf<Int>()
        lines.map { parseLine(it) }.forEach {
            cycles.add(x)
            when(it.first) {
                "noop" -> x
                "addx" -> {
                    cycles.add(x)
                    x += it.second
                }
                else -> throw Exception("Not implemented")
            }
        }

        cycles.forEachIndexed { index, i -> println("$index -> $i") }

        return listOf(20, 60, 100, 140, 180, 220).sumOf { it * cycles[it - 1] }
    }

    override fun part2(lines: List<String>): Any {
        var x = 1
        val cycles = mutableListOf<Int>()
        lines.map { parseLine(it) }.forEach {
            cycles.add(x)
            when(it.first) {
                "noop" -> x
                "addx" -> {
                    cycles.add(x)
                    x += it.second
                }
                else -> throw Exception("Not implemented")
            }
        }


        var line = StringBuilder()
        cycles.forEachIndexed { index, i -> run {
            val crtPosition = index % 40
            if (crtPosition == 0) {
                println(line.toString())
                line = StringBuilder()
            }

            if (crtPosition in i-1 .. i+1) {
                line.append("#")
            } else {
                line.append(".")
            }
            //println("$crtPosition $i")
            //println(i-1 .. i+1)
            //println(crtPosition in i-1 .. i+1)


        } }
        println(line)

        return ""
    }
}