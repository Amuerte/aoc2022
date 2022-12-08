package advent.code.day7

import advent.code.Problem

abstract class Node(val name:String, val size: Int, val parent: Node?, val leaves:MutableList<Node> = mutableListOf()) {

    fun isDirectory():Boolean = (size == 0)

    fun computeSize():Int = size + leaves.sumOf { it.computeSize() }

    fun findRootAndDepth():Pair<Node,Int> {
        if (parent == null) this to 0

        var root:Node? = parent
        var depth = 1
        while(root?.parent != null) {
            root = root?.parent
            depth++
        }
        return root!! to depth
    }

    fun goToChildLeave(name: String):Node = leaves.first { it.name == name }

    open fun println() {
        val prefix = "\t".repeat(findRootAndDepth().second)
        println("$prefix- $this")
        leaves.sortedBy { name }.forEach { it.println() }
    }
}

class File(name: String, size: Int, parent:Node):Node(name, size, parent) {
    override fun toString(): String  = "$name (file, size=$size)"
}

open class Directory(name: String, parent:Node?):Node(name, 0, parent)  {
    override fun toString() = "$name (dir)\""
}

class Tree:Directory(name="/", null)

fun parseNode(line: String, current: Node): Node = line.split(" ")
        .let { it[0] to it[1] }
        .let {
            val node = when (it.first) {
                "dir" -> Directory(it.second, current)
                else -> File(it.second, it.first.toInt(), current)
            }
            current.leaves.add(node)
            return current
        }

fun parseLine(line:String, current: Node):Node =
    when(line.first()) {
        '$' -> parseCommand(line, current)
        else -> parseNode(line, current)
    }


fun parseCommand(line: String, current: Node): Node =
    line.substring(2).split(" ").let {
        if (it.size < 2) return current
        val cmd = it[0]
        val arg = it[1]
        return when(cmd) {
            "cd" -> changeDirectory(current, arg)
            else -> throw Exception("Command Not Implemented")
        }
    }

fun changeDirectory(current: Node, arg: String):Node =
    when(arg) {
        ".." -> current.parent ?: current
        "/" -> current.findRootAndDepth().first
        else -> current.goToChildLeave(arg)
    }

fun findAllDir(current: Node): List<Node> {
    val dirs = mutableListOf<Node>()
    if (current.isDirectory()) {
        dirs.add(current)
        dirs.addAll(current.leaves.flatMap { findAllDir(it) })
    }

    return dirs
}

fun buildTree(lines: List<String>): Tree {
    val root = Tree()
    var current: Node = root
    lines.forEach {
        current = parseLine(it, current)
    }
    return root
}

class Filesystem:Problem {

    override fun part1(lines: List<String>): Any {
        var root = buildTree(lines)
        return findAllDir(root).map { it.computeSize() }.filter { it <= 100000 }.sum()
    }

    override fun part2(lines: List<String>): Any {
        var root = buildTree(lines)

        val available = 70000000
        val needed = 30000000

        val totalUsed = root.computeSize()
        val totalLeft = available - totalUsed
        val toDelete = needed - totalLeft

        return findAllDir(root).map { it.computeSize() }.filter { it >= toDelete }.minOrNull()!!
    }

}