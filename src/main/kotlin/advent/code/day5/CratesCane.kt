package advent.code.day5

import advent.code.Problem
import advent.code.util.println

data class Move(val crates: Int, val from:Int, val to: Int)

class CratesCane:Problem {

    fun readCratesAndMoves(lines:List<String>):Pair<List<String>, List<String>> {
        var crates = mutableListOf<String>()
        var moves = mutableListOf<String>()
        var isMap = true
        lines.forEach {
            if (it == "") {
                isMap = false
            }
            if (isMap) {
                crates.add(it)
            }
            if (!isMap && it != "") {
                moves.add(it)
            }
        }
        return crates.reversed() to moves
    }

    fun parseCrates(crates : List<String>): List<ArrayDeque<String>> {
        val nbColumm = parseCratesLine(crates.first()).size
        val cratesMap = List(nbColumm) { ArrayDeque<String>() }

        with(crates) {
            drop(1).map { parseCratesLine(it)}.forEach {
                for (i in 0 until nbColumm) {
                    if (it[i].isNotBlank() ) cratesMap[i].addLast(it[i])
                }
            }
        }
        return cratesMap
    }
    fun parseCratesLine(line: String): List<String> = line.chunked(4) {
        it.trim().replace(Regex("\\["), "").replace(Regex("\\]"), "") }

    override fun part1(lines: List<String>): Any {
        val (crates, moves) = readCratesAndMoves(lines)
        val cratesMap = parseCrates(crates)

        moves.map { parseMove(it) }.forEach {
            for (i in 0 until it.crates) {
                cratesMap[it.to -1].addLast(cratesMap[it.from - 1].removeLast())
            }
        }

        return cratesMap.joinToString(separator = "") { it.last() }
    }

    fun parseMove(move:String): Move {
        val res = Regex("move (\\d*) from (\\d*) to (\\d*)").matchEntire(move)?.destructured
        return Move(res?.component1()?.toInt() ?:0, res?.component2()?.toInt() ?:0, res?.component3()?.toInt() ?:0)
    }

    override fun part2(lines: List<String>): Any {
        val (crates, moves) = readCratesAndMoves(lines)
        val cratesMap = parseCrates(crates)

        moves.map { parseMove(it) }.forEach {
            cratesMap[it.to -1].addAll(cratesMap[it.from -1].takeLast(it.crates))
            for (i in 0 until it.crates) {
                cratesMap[it.from -1].removeLast()
            }
        }

        return cratesMap.joinToString(separator = "") { it.last() }
    }
}