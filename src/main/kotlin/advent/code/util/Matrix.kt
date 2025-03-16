package advent.code.util

import kotlin.math.max
import kotlin.math.min

fun List<List<String>>.printRaw() {
    this.map { it.joinToString("") }.println()
}

fun List<List<String>>.printRaw(minX:Int, maxX:Int, minY:Int, maxY:Int) {
    this.subList(minY, maxY).map { it.subList(minX, maxX).joinToString("") }.println()
}

fun rangeFrom(a:Int, b:Int) = min(a, b)..max(a,b)

fun findMinX(path: Collection<Pair<Int, Int>>): Int = path.map { it.first }.min()
fun findMaxX(path: Collection<Pair<Int, Int>>): Int = path.map { it.first }.max()

fun findMinY(path: Collection<Pair<Int, Int>>): Int = path.map { it.second }.min()
fun findMaxY(path: Collection<Pair<Int, Int>>): Int = path.map { it.second }.max()