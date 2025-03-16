package advent.code.day17

import advent.code.Problem
import advent.code.util.printRaw

val input1 = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"

val shapes = listOf(
    "####",
    "",
    ".#.",
    "###",
    ".#.",
    "",
    "..#",
    "..#",
    "###",
    "",
    "#",
    "#",
    "#",
    "#",
    "",
    "##",
    "##")



data class Shape(val mask: List<List<Int>>) {
    var position: Pair<Int, Int> = 2 to 0
}

fun shapes(): List<Shape> =
    listOf(
        Shape(listOf(
                listOf(1, 1, 1, 1)
            )
        ),
        Shape(listOf(
                listOf(0, 1, 0),
                listOf(1, 1, 1),
                listOf(0, 1, 0)
            )
        ),
        Shape(listOf(
                listOf(0, 0, 1),
                listOf(0, 0, 1),
                listOf(1, 1, 1)
            )
        ),
        Shape(listOf(
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(1)
            )
        ),
        Shape(listOf(
            listOf(1, 1),
            listOf(1, 1)
            )
        ),
    )

class PyroclasticFlow:Problem {
    override fun part1(lines: List<String>): Any {
        val grid: List<MutableList<String>> = List(4*2024) { MutableList(7) { "." }}

        var shapeIdx = 0
        var maxRow = grid.size

        return grid.printRaw()
    }

    override fun part2(lines: List<String>): Any {
        return ""
    }
}