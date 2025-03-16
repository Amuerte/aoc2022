package advent.code.day18

import advent.code.Problem
import advent.code.util.println

val input1 = listOf(
    "2,2,2",
    "1,2,2",
    "3,2,2",
    "2,1,2",
    "2,3,2",
    "2,2,1",
    "2,2,3",
    "2,2,4",
    "2,2,6",
    "1,2,5",
    "3,2,5",
    "2,1,5",
    "2,3,5")

val input2 = listOf(
    "1,1,1",
    "1,2,1",
    "2,1,1",
    "2,3,1",
    "3,1,1",
    "3,2,1")

fun parseLine(line:String): List<Int> = line.split(",").map { it.toInt() }

fun isAdjacent(p1: List<Int>, p2: List<Int>):Boolean = (distance(p1, p2) == 1)

fun isDistantOf2(p1: List<Int>, p2: List<Int>):Boolean = (distance(p1, p2) == 4)

fun distance(p1: List<Int>, p2: List<Int>):Int = (0..2).sumOf { (p1[it] - p2[it]) * (p1[it] - p2[it]) }

fun findMax(points: List<List<Int>>, idx: Int) = points.map { it[idx] }.max()
fun findMin(points: List<List<Int>>, idx: Int) = points.map { it[idx] }.min()

fun calcVector(p1: List<Int>, p2: List<Int>): List<Int> = (0..2).map { (p1[it] - p2[it])/2 }
fun add(p1: List<Int>, p2: List<Int>): List<Int> = (0..2).map { p1[it] + p2[it] }

object pointComparator: Comparator<List<Int>> {
    override fun compare(o1: List<Int>, o2: List<Int>): Int {
        return if (o1[0] == o2[0]) {
            if (o1[1] == o2[1]) {
                o1[2].compareTo(o2[2])
            } else {
                o1[1].compareTo(o2[1])
            }
        } else {
            o1[0].compareTo(o2[0])
        }
    }
}

class BoilingBoulders:Problem {
    override fun part1(lines: List<String>): Any {

        val points = lines.map { parseLine(it) }.sortedWith(pointComparator)

        points.println()

        var sides = MutableList(points.size) {6}

        for (i in points.indices) {
            for(j in i+1 until points.size) {
                if (isDistantOf2(points[i], points[j])) {
                    sides[i]--
                    sides[j]--
                }
            }
        }

        var distantOf2 = mutableSetOf<Pair<List<Int>, List<Int>>>()

        for (i in points.indices) {
            for(j in i+1 until points.size) {
                if (isDistantOf2(points[i], points[j])) {
                    distantOf2.add(points[i] to points[j])
                }
            }
        }

        //println(distantOf2)

        val emptyCells = distantOf2.map { (x, y) -> add(y, calcVector(x, y)) }.filter { !points.contains(it) }.toSet()
        println(emptyCells)
        //println(sides)

        return sides.sum() - 6*emptyCells.size
    }

    override fun part2(lines: List<String>): Any {
        return ""
    }
}