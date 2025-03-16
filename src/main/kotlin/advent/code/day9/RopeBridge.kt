package advent.code.day9

import advent.code.Problem
import kotlin.math.abs
import kotlin.math.sign

class RopeBridge:Problem {

    val input1 = listOf(
        "R 4",
        "U 4",
        "L 3",
        "D 1",
        "R 4",
        "D 1",
        "L 5",
        "R 2")

    val input2 = listOf("R 5",
        "U 8",
        "L 8",
        "D 3",
        "R 17",
        "D 10",
        "L 25",
        "U 20")

    fun parseLine(line: String): Pair<String, Int> = line.split(" ").let { it[0] to it[1].toInt() }

    fun moveLine(point: Pair<Int, Int>, move: Pair<String, Int>): Pair<Int,Int> {
        return when(move.first) {
            "U" -> point.first + 1 to point.second
            "D" -> point.first - 1 to point.second
            "L" -> point.first to point.second - 1
            "R" -> point.first to point.second + 1
            else -> throw Exception("not implemented")
        }
    }

    fun followHead(tail: Pair<Int, Int>, currentHead:Pair<Int, Int>): Pair<Int,Int> {
        var deltaX = sign((currentHead.second - tail.second).toDouble())
        var deltaY = sign((currentHead.first - tail.first).toDouble())

        val newTail = tail.first + deltaY.toInt() to tail.second + deltaX.toInt()
        //println("tail $newTail - head $currentHead")
        return tail.first + deltaY.toInt() to tail.second + deltaX.toInt()
    }

    fun moveTail(tail: Pair<Int, Int>, currentHead: Pair<Int, Int>, move: Pair<String, Int>): Pair<Int,Int> {
        return if (abs(tail.first - currentHead.first) > 1 || abs(tail.second - currentHead.second) > 1) {
            followHead(tail, currentHead)
        } else {
            tail
        }
    }

    override fun part1(lines: List<String>): Any {
        var headPosition = 0 to 0
        var tailPosition = 0 to 0
        var positions = mutableSetOf<Pair<Int, Int>>()

        val moves = lines.map { parseLine(it) }.forEach {
            var move = it
            repeat(it.second) {
                headPosition = moveLine(headPosition, move)
                tailPosition = moveTail(tailPosition, headPosition, move)
                positions.add(tailPosition)
            }

            println(headPosition)
            println(tailPosition)
        }

        println(positions)
        return positions.size
    }




    override fun part2(lines: List<String>): Any {
        var headPosition = 5 to 11
        var position1 = headPosition.first to headPosition.second
        var position2 = headPosition.first to headPosition.second
        var position3 = headPosition.first to headPosition.second
        var position4 = headPosition.first to headPosition.second
        var position5 = headPosition.first to headPosition.second
        var position6 = headPosition.first to headPosition.second
        var position7 = headPosition.first to headPosition.second
        var position8 = headPosition.first to headPosition.second
        var position9 = headPosition.first to headPosition.second

        var positions = mutableSetOf<Pair<Int, Int>>()

        val moves = lines.map { parseLine(it) }.forEach {
            var move = it
            repeat(it.second) {
                headPosition = moveLine(headPosition, move)
                position1 = moveTail(position1, headPosition, move)
                position2 = moveTail(position2, position1, move)
                position3 = moveTail(position3, position2, move)
                position4 = moveTail(position4, position3, move)
                position5 = moveTail(position5, position4, move)
                position6 = moveTail(position6, position5, move)
                position7 = moveTail(position7, position6, move)
                position8 = moveTail(position8, position7, move)
                position9 = moveTail(position9, position8, move)
                positions.add(position9)
            }

            println("head: $headPosition")
            println("1: $position1")
            println("2: $position2")
            println("3: $position3")
            println("4: $position4")
            println("5: $position5")
            println("6: $position6")
            println("7: $position7")
            println("8: $position8")
            println("9: $position9")

        }

        println(positions)
        return positions.size
    }
}