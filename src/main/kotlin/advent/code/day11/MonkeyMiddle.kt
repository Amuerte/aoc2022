package advent.code.day11

import advent.code.Problem
import advent.code.util.println
import java.util.concurrent.atomic.AtomicLong


val input1 = listOf(
    "Monkey 0:",
    "  Starting items: 79, 98",
    "  Operation: new = old * 19",
    "  Test: divisible by 23",
    "    If true: throw to monkey 2",
    "    If false: throw to monkey 3",
    "",
    "Monkey 1:",
    "  Starting items: 54, 65, 75, 74",
    "  Operation: new = old + 6",
    "  Test: divisible by 19",
    "    If true: throw to monkey 2",
    "    If false: throw to monkey 0",
    "",
    "Monkey 2:",
    "  Starting items: 79, 60, 97",
    "  Operation: new = old * old",
    "  Test: divisible by 13",
    "    If true: throw to monkey 1",
    "    If false: throw to monkey 3",
    "",
    "Monkey 3:",
    "  Starting items: 74",
    "  Operation: new = old + 3",
    "  Test: divisible by 17",
    "    If true: throw to monkey 0",
    "    If false: throw to monkey 1")

fun pgcd(x: Long, y: Long):Long {
    if (y == 0L) return x

    var a = x
    var b = y

    if (y > x) {
        a = y
        b = x
    }

    return pgcd(y, x % y)
}

fun ppcm(x: Long, y: Long): Long {
    return x * y / pgcd(x, y)
}

fun parseLines(lines: List<String>):MonkeyManager {
    var monkeys = mutableListOf<Monkey>()
    var builder = Monkey.Builder()
    lines.forEach { line ->
        if (line.startsWith("Monkey ")) {
            var idx = line.split(" ")[1].trim(':').toInt()
            builder.idx(idx)
        }
        if (line.startsWith("  Starting items:")) {
            var items = line.split(":")[1].split(",").map { it.trim().toLong() }
            builder.items(ArrayDeque(items))
        }
        if (line.startsWith("  Operation:")) {
            var functionString = line.split(":")[1].split("=")[1].trim()
            val operation = generateOperation(functionString)
            builder.operation(operation)
        }
        if (line.startsWith("  Test:")) {
            var modulo = line.split(" ").last().toInt()
            builder.modulo(modulo)
        }

        if (line.startsWith("    If true:")) {
            var monkeyIdxTrue = line.split(" ").last().toInt()
            builder.monkeyIdxTrue(monkeyIdxTrue)
        }

        if (line.startsWith("    If false:")) {
            var monkeyIdxFalse= line.split(" ").last().toInt()
            builder.monkeyIdxFalse(monkeyIdxFalse)
        }

        if(line == "") {
            monkeys.add(builder.build())
            builder = Monkey.Builder()
        }
    }
    monkeys.add(builder.build())

    val scaleFactor = monkeys.map { it.modulo.toLong() }.reduce { a, b -> ppcm(a, b) }
    println("scale: $scaleFactor")
    println(monkeys.map {it.modulo})
    return MonkeyManager(monkeys, scale = scaleFactor)
}

fun generateOperation(operation: String): (Long) -> Long {
    val operands = operation.split(" ")

    val left = operands[0]
    val right = operands[2]
    val operator = operands[1]

    return if (left == right) {
        when(operator) {
            "+" -> { x:Long -> x + x }
            "*" -> { x:Long -> x * x }
            else -> throw Exception("Not Implemented")
        }
    } else {
        when(operator) {
            "+" -> { x:Long -> x + right.toLong() }
            "*" -> { x:Long -> x * right.toLong() }
            else -> throw Exception("Not Implemented")
        }
    }
}

class Monkey(val idx: Int, val items:ArrayDeque<Long>,
             private val operation: (Long) -> Long,
             val modulo: Int,
             private val monkeyIdxTrue: Int,
             private val monkeyIdxFalse: Int) {

    var nbCheck = 0

    fun inspect(manager: MonkeyManager) {
        items.forEach { inspect(it, manager) }

        nbCheck += items.size
        items.clear()
    }

    private fun inspect(itemWorry: Long, manager: MonkeyManager) {
        var newWorryLevel:Long = manager.scale(operation.invoke(itemWorry))
        val destMonkeyIdx = if ((newWorryLevel.rem(modulo)) == 0L) monkeyIdxTrue else monkeyIdxFalse
        //println("$idx($itemWorry, $newWorryLevel, $destMonkeyIdx)")
        manager.send(newWorryLevel, destMonkeyIdx)
    }

    override fun toString(): String {
        return "Monkey(idx=$idx, items=$items, operation=$operation, modulo=$modulo, monkeyIdxTrue=$monkeyIdxTrue, monkeyIdxFalse=$monkeyIdxFalse)"
    }

    data class Builder(
        var idx: Int? = null,
        var items:ArrayDeque<Long>? = null,
        var operation: ((Long) -> Long)? = null,
        var modulo: Int? = null,
        var monkeyIdxTrue: Int? = null,
        var monkeyIdxFalse: Int? = null) {

        fun idx(idx: Int) = apply { this.idx = idx }
        fun items(items:ArrayDeque<Long>) = apply { this.items = items }
        fun operation(operation: (Long) -> Long) = apply { this.operation = operation }
        fun modulo(modulo: Int) = apply { this.modulo = modulo }
        fun monkeyIdxTrue(monkeyIdxTrue: Int) = apply { this.monkeyIdxTrue = monkeyIdxTrue }
        fun monkeyIdxFalse(monkeyIdxFalse: Int) = apply { this.monkeyIdxFalse = monkeyIdxFalse }
        fun build() = Monkey(idx!!, items!!, operation!!, modulo!!, monkeyIdxTrue!!, monkeyIdxFalse!!)
    }
}



class MonkeyManager(private val monkeys: List<Monkey>, scale: Long = 3) {
    private val scaleFactor = AtomicLong(scale)

    fun inspect(rounds: Int) {
        var round = 0
        repeat(rounds) {
            round++
            monkeys.forEach { it.inspect(this) }
        }
    }

    fun getChecks():List<Int> = monkeys.map { it.nbCheck }

    fun computeMonkeyBusiness():Long = monkeys.map { it.nbCheck }.sortedDescending().take(2).fold(1L) { acc, i -> acc * i.toLong() }

    fun send(x: Long, toMonkeyIdx: Int) {
        monkeys[toMonkeyIdx].items.addLast(x)
    }

    fun scale(number: Long): Long {
        return number % scaleFactor.get()
    }
}

class MonkeyMiddle:Problem {

    override fun part1(lines: List<String>): Any {
        val manager = parseLines(lines)
        manager.inspect(20)
        manager.getChecks().println()

        return manager.computeMonkeyBusiness()
    }

    override fun part2(lines: List<String>): Any {
        val manager = parseLines(lines)
        manager.inspect(10000)
        manager.getChecks().println()

        return manager.computeMonkeyBusiness()
    }
}