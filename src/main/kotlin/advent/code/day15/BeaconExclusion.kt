package advent.code.day15

import advent.code.Problem
import advent.code.util.*
import kotlin.math.absoluteValue

val input1 = listOf(
    "Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
    "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
    "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
    "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
    "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
    "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
    "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
    "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
    "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
    "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
    "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
    "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
    "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
    "Sensor at x=20, y=1: closest beacon is at x=15, y=3"
)

fun distance(a: Pair<Int, Int>, b:Pair<Int, Int>) = (a.first - b.first).absoluteValue + (a.second - b.second).absoluteValue

fun findMinXEmpty(distances: List<Pair<Pair<Int, Int>, Int>>): Int {
    return distances.map { (sensor, distance) -> sensor.first - distance }.min()
}

fun findMaxXEmpty(distances: List<Pair<Pair<Int, Int>, Int>>): Int {
    return distances.map { (sensor, distance) -> sensor.first + distance }.max()
}

fun isInsideEmptyZone(p: Pair<Int, Int>, sensor: Pair<Int, Int>, distanceToBeacon: Int):Boolean =
    distance(p, sensor) <= distanceToBeacon

fun isInsideAtLeastOneEmptyZone(p: Pair<Int, Int>, distances: List<Pair<Pair<Int, Int>, Int>>):Boolean {
    for ((sensor, distance) in distances) {
        if (isInsideEmptyZone(p, sensor, distance)) {
            return true
        }
    }
    return false
}

class BeaconExclusion:Problem {

    fun parseLine(line: String): List<Pair<Int, Int>>  =
        Regex("x=(-?\\d*), y=(-?\\d*)").findAll(line)
            .map{ it.groupValues.drop(1) }.map{ it[0].toInt() to it[1].toInt() }.toList()

    override fun part1(lines: List<String>): Any {
        //val input = input1 to 10
        val input = lines to 2000000

        val row = input.second

        val pointToBeacon = input.first.map { parseLine(it) }
        val distances = pointToBeacon.map { it[0] to distance(it[0], it[1])}
        val minXEmpty = findMinXEmpty(distances)
        val maxXEmpty = findMaxXEmpty(distances)
        println("minXEmpty: $minXEmpty; maxXEmpty: $maxXEmpty")

        val allPoints = pointToBeacon.flatten().toSet()
        val minX = findMinX(allPoints)
        val maxX = findMaxX(allPoints)
        val minY = findMinY(allPoints)
        val maxY = findMaxY(allPoints)

        println("minX: $minX, maxX: $maxX, minY: $minY, maxY: $maxY")

        val pointPerRow = allPoints.filter { (x,y) -> y == row }

        val res =  (minXEmpty..maxXEmpty).map { it to row }.map {
            if (pointPerRow.contains(it)) "S"
            else if (isInsideAtLeastOneEmptyZone(it, distances)) "#"
            else "."
        }

        return res.count { it == "#" }
    }

    override fun part2(lines: List<String>): Any {
        /*
        //val input = input1 to 20
        val input = lines to 4000000

        val maxRow = input.second

        val pointToBeacon = input.first.map { parseLine(it) }
        val distances = pointToBeacon.map { it[0] to distance(it[0], it[1])}
        val minXEmpty = findMinXEmpty(distances)
        val maxXEmpty = findMaxXEmpty(distances)
        println("minXEmpty: $minXEmpty; maxXEmpty: $maxXEmpty")

        val allPoints = pointToBeacon.flatten().toSet()
        println(allPoints.size)

        var res = listOf<Int>()

        for(row in 0..maxRow) {
            println("row: $row")
            res = (0..maxRow).map { it to row }.map {
                if (allPoints.contains(it) || isInsideAtLeastOneEmptyZone(it, distances)) 0
                else 1
            }
            val column = res.filter { it > 0 }
            if (column.isNotEmpty()) {
                break
            }
        }

        return res.mapIndexed {index, s -> if (s > 0) index else -1 }.filter { it >= 0 }

         */
        return ""
    }
}