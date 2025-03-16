package advent.code.day19

import advent.code.Problem

data class Blueprint(val id: Int, val ore:Int)

class OreCollect:Problem {
    override fun part1(lines: List<String>): Any {
        return ""
    }

    override fun part2(lines: List<String>): Any {

        return listOf(82,67).map { x -> Char(x) }.joinToString("")
    }
}