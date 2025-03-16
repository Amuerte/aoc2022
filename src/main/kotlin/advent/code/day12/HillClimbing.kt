package advent.code.day12

import advent.code.Problem
import kotlin.math.absoluteValue
import kotlin.streams.toList

val input1 = listOf(
    "Sabqponm",
    "abcryxxl",
    "accszExk",
    "acctuvwj",
    "abdefghi"
)


val startPoint = 'S'.code
val targetPoint = 'E'.code

val minHeight = 'a'.code
val maxHeight = 'z'.code

data class Node(val x: Int, val y: Int, val height: Int, var from: Node? = null) {

    fun isAccessible(node: Node) = from != node &&
            (translate(node.height) <= translate(this.height) || distance(node.height, this.height) == 1)

    fun neighbours(map: List<List<Int>>): List<Node> {
        val neighbours = mutableListOf<Pair<Int, Int>>()

        //up
        if (x > 0) {
            neighbours.add(x - 1 to y)
        }

        //left
        if (y > 0) {
            neighbours.add(x to y - 1)
        }

        //bottom
        if (x < map.size - 1) {
            neighbours.add(x + 1 to y)
        }

        //right
        if (y < map[0].size - 1) {
            neighbours.add(x to y + 1)
        }

        return neighbours.map { (x, y) -> Node(x, y, map[x][y], this) }.filter { isAccessible(it) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "Node(x=$x, y=$y, height=${height.toChar()})"
    }


}

fun parseLines(lines: List<String>): List<List<Int>> {
    var map = mutableListOf<List<Int>>()
    lines.forEach { map.add(it.chars().toList()) }
    return map
}

fun distance(height1: Int, height2: Int) = (translate(height2) - translate(height1)).absoluteValue

fun findPosition(height: Int, map: List<List<Int>>): MutableList<Node> {
    var allPositions = mutableListOf<Node>()
    for (i in map.indices) {
        for (j in map[i].indices) {
            if (map[i][j] == height) allPositions.add(Node(i, j, height))
        }
    }

    return allPositions
}


fun translate(height: Int): Int = when (height) {
    startPoint -> minHeight
    targetPoint -> maxHeight
    else -> height
}

fun traverseGraph(start: Node, target: Node, map: List<List<Int>>): Node? {
    val explored = mutableSetOf<Node>()
    val currentLevel = ArrayDeque<Node>()
    currentLevel.addFirst(start)
    while (currentLevel.isNotEmpty()) {
        var currentNode = currentLevel.removeLast()
        if (currentNode == target) {
            return currentNode
        }
        if (!explored.contains(currentNode)) {
            currentNode.neighbours(map).filter { !explored.contains(it) }.forEach { currentLevel.addFirst(it) }
            explored.add(currentNode)
        }
    }

    return null
}

fun printPath(map: List<List<Int>>, path: List<Node>) {
    var nodes = path.map { it.x to it.y }.toSet()
    for (i in map.indices) {
        var buffer = StringBuilder()
        map[i].forEachIndexed { j, _ ->
            if (nodes.contains(i to j)) {
                buffer.append("X")
            } else {
                buffer.append(".")
            }
        }
        println(buffer.toString())
    }
}

fun findPathToStart(endNode: Node): List<Node> {
    var target = endNode
    val path = mutableListOf<Node>()
    while (target.from != null) {
        path.add(target)
        target = target.from!!
    }
    path.add(target)

    return path
}

class HillClimbing : Problem {
    override fun part1(lines: List<String>): Any {
        val map = parseLines(lines)
        var target = traverseGraph(findPosition(startPoint, map).first(), findPosition(targetPoint, map).first(), map)
        val path = findPathToStart(target!!)
        printPath(map, path.reversed())

        return path.reversed().size - 1
    }

    override fun part2(lines: List<String>): Any {
        val map = parseLines(lines)
        val targetNode = findPosition(targetPoint, map).first()

        val startPositions = findPosition(minHeight, map)
        startPositions.addAll(findPosition(startPoint, map))

        return startPositions.map { traverseGraph(it, targetNode, map) }.filterNotNull()
            .map{ findPathToStart(it) }
            .map { it.size - 1 }.min()
    }
}