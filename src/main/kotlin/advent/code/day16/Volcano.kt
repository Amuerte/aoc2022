package advent.code.day16

import advent.code.Problem

val input1 = listOf(
    "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB",
    "Valve BB has flow rate=13; tunnels lead to valves CC, AA",
    "Valve CC has flow rate=2; tunnels lead to valves DD, BB",
    "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE",
    "Valve EE has flow rate=3; tunnels lead to valves FF, DD",
    "Valve FF has flow rate=0; tunnels lead to valves EE, GG",
    "Valve GG has flow rate=0; tunnels lead to valves FF, HH",
    "Valve HH has flow rate=22; tunnel leads to valve GG",
    "Valve II has flow rate=0; tunnels lead to valves AA, JJ",
    "Valve JJ has flow rate=21; tunnel leads to valve II"
)

data class Valve(val name: String, val rate: Int) {
    val neighbourgs = mutableListOf<Valve>()
    var open = false

    fun add(node: Valve) {
        neighbourgs.add(node)
    }

    fun addAll(nodes: Collection<Valve>): Valve {
        neighbourgs.addAll(nodes)
        return this
    }

    fun open() = run { open = true }

    override fun toString(): String {
        return "Valve(name='$name', rate=$rate, open=$open, neighbourgs=${neighbourgs.map { it.name }})"
    }
}

fun parseNode(valveInfo: String): Valve = Regex("Valve ([A-Z]{2}) has flow rate=(\\d*)")
    .matchEntire(valveInfo)!!.groupValues.let { Valve(it[1], it[2].toInt()) }

fun parseNeighbours(neighbours: String): Set<String> = Regex("([A-Z]{2}(,\\s)?)+")
    .find(neighbours)!!.value.split(", ").toSet()

fun parseLine(line: String): Pair<Valve, Set<String>> =
    line.split(";").let { parseNode(it[0]) to parseNeighbours(it[1]) }

fun parseLines(lines: List<String>): List<Valve> {
    val parsedNodes = input1.map { parseLine(it) }

    val valves = parsedNodes.map { it.first }
    val valveByLabel = valves.groupBy { it.name }
    val neighbours = parsedNodes.map { it.second }.map { it.map { name -> valveByLabel[name]!!.first() } }


    return valves.zip(neighbours).map { (v, nodes) -> v.addAll(nodes) }

}

class Volcano : Problem {
    override fun part1(lines: List<String>): Any {
        val valves = parseLines(lines)
        valves.first().open()
        return valves.first()
    }

    override fun part2(lines: List<String>): Any {
        return ""
    }
}
