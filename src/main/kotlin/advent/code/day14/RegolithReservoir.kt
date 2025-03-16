package advent.code.day14

import advent.code.Problem
import advent.code.util.*


val input1 = listOf(
    "498,4 -> 498,6 -> 496,6",
    "503,4 -> 502,4 -> 502,9 -> 494,9"
)

fun List<List<String>>.isAir(x:Int, y:Int):Boolean = (this[y][x] == ".")

class RegolithReservoir : Problem {

    fun parseLine(line: String): List<Pair<Int, Int>> =
        line.split(" -> ").map { it.split(",") }.map { (x, y) -> x.toInt() to y.toInt() }

    fun fillRockPath(rocks: List<Pair<Int, Int>>, grid: List<MutableList<String>>) {
        var previousRock = -1 to -1
        rocks.forEach { (x, y) ->
            grid[y][x] = "X"

            if (previousRock.first == x && previousRock.second >= 0) {
                rangeFrom(previousRock.second, y).forEach { grid[it][x] = "X" }
            }
            if (previousRock.second == y && previousRock.first >= 0) {
                rangeFrom(previousRock.first, x).forEach { grid[y][it] = "X" }
            }

            previousRock = x to y
        }
    }

    fun fillWithSand(grid: List<MutableList<String>>, start: Pair<Int, Int>): Pair<Int, Int> {
        var currentX = start.first
        var currentY = start.second
        //println("x: $currentX, y: $currentY")
        while(currentY < grid.size - 1 && grid.isAir(currentX, currentY + 1)) {
            currentY++
        }

        if (currentY == grid.size - 1) {
            grid[currentY][currentX] = "E"
            return currentX to currentY
        }

        return if (grid.isAir(currentX - 1, currentY + 1)) {
            fillWithSand(grid, currentX - 1 to currentY)
        } else if (currentX  < grid.first().size - 1 && grid.isAir(currentX + 1, currentY + 1)) {
            fillWithSand(grid, currentX + 1 to currentY)
        } else {
            grid[currentY][currentX] = "O"
            currentX to currentY
        }
    }

    override fun part1(lines: List<String>): Any {
        // Removing duplicate path
        val paths = lines.toSet().map { parseLine(it) }

        val allRocks = paths.flatten()
        val minX = findMinX(allRocks) + 1
        val maxX = findMaxX(allRocks) + 1
        val minY = findMinY(allRocks) + 1
        val maxY = findMaxY(allRocks) + 1
        println("minX: $minX, maxX: $maxX, minY: $minY, maxY: $maxY")

        val grid: List<MutableList<String>> = List(maxY + 2) { MutableList(maxX) { "." }}

        paths.forEach { fillRockPath(it, grid) }
        val start =  500 to 0
        grid[start.second][start.first] = "+"

        var lastY = -1
        while (lastY < maxY) {
            var (x, y) = fillWithSand(grid, start)
            lastY = y
        }

        grid.printRaw(490, maxX, 0, maxY + 2)
        return grid.flatten().filter { it == "O" }.count()
    }

    override fun part2(lines: List<String>): Any {
        // Removing duplicate path
        val paths = lines.toSet().map { parseLine(it) }

        val allRocks = paths.flatten()
        val minX = findMinX(allRocks) + 1
        val maxX = findMaxX(allRocks) + 1
        val minY = findMinY(allRocks) + 1
        val maxY = findMaxY(allRocks) + 1

        println("minX: $minX, maxX: $maxX, minY: $minY, maxY: $maxY")

        val grid: List<MutableList<String>> = List(maxY + 2) { MutableList(maxX + 200) { "." }}
        (0 until grid.first().size).forEach { grid[grid.size - 1][it] = "X" }

        paths.forEach { fillRockPath(it, grid) }
        val start =  500 to 0
        grid[start.second][start.first] = "+"

        var isStartReached = false
        while (!isStartReached) {
            var last = fillWithSand(grid, start)
            println("last $last")
            isStartReached = (last.first == start.first && last.second == start.second)
        }

        grid.printRaw(0, maxX + 200, 0, maxY + 2)
        return grid.flatten().filter { it == "O" }.count()
    }
}