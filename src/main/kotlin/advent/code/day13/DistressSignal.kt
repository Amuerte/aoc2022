package advent.code.day13

import advent.code.Problem
import kotlin.math.sign

val input1 = listOf(
    "[1,1,3,1,1]",
    "[1,1,5,1,1]",
    "",
    "[[1],[2,3,4]]",
    "[[1],4]",
    "",
    "[9]",
    "[[8,7,6]]",
    "",
    "[[4,4],4,4]",
    "[[4,4],4,4,4]",
    "",
    "[7,7,7,7]",
    "[7,7,7]",
    "",
    "[]",
    "[3]",
    "",
    "[[[]]]",
    "[[]]",
    "",
    "[1,[2,[3,[4,[5,6,7]]]],8,9]",
    "[1,[2,[3,[4,[5,6,0]]]],8,9]")

fun parseLine(line: String): List<String> {
    return if (line.indexOfFirst { it == '[' } == 0 && line.indexOfLast { it == ']' } == line.length - 1 ) {
        var openBrackets = 0
        var result= mutableListOf<String>()
        var buffer = StringBuilder()
        line.substring(1, line.length - 1).toList().forEach {
            if (it == '[') {
                openBrackets++
            }
            if (it == ']') {
                openBrackets--
            }

            if (it == ',' && openBrackets == 0) {
                result.add(buffer.toString())
                buffer.clear()
            } else {
                buffer.append(it)
            }
        }
        if (buffer.isNotEmpty()) {
            result.add(buffer.toString())
        }

        return result
    } else {
        listOf(line)
    }
}

fun <E> List<E>.toString2():String {
    if (this.isEmpty()) return "[]"

    val sb = StringBuilder()
    sb.append('[')
    sb.append(this.joinToString(","))
    sb.append(']')
    return sb.toString()
}

class DistressSignal:Problem {

    fun isListElement(element: String):Boolean = element.first() == '['

    fun isIntElement(element: String):Boolean = !isListElement(element)

    fun isInOrder(left: String, right: String): Int {
        //println("left $left")
        //println("right $right")

        if (isIntElement(left) && isIntElement(right)) {
            return (right.toInt() - left.toInt()).sign
        }

        if (isListElement(left) && isListElement(right)) {
            val listLeft = parseLine(left)
            val listRight = parseLine(right)

            if (listLeft.isEmpty() && listRight.isNotEmpty()) {
                return 1
            }
            if (listRight.isEmpty() && listLeft.isNotEmpty()) {
                return -1
            }

            if (listRight.isEmpty() && listLeft.isEmpty()) {
                return 0
            }

            val firstIsInOrder = isInOrder(listLeft.first(), listRight.first())
            return if (firstIsInOrder == 0) {
                isInOrder(listLeft.drop(1).toString2(), listRight.drop(1).toString2())
            } else {
                firstIsInOrder
            }
        }

        return if (isIntElement(left)) {
            isInOrder( "[$left]", right)
        } else {
            isInOrder( left, "[$right]")
        }
    }

    override fun part1(lines: List<String>): Any {
        val inputs = lines.chunked(3).map { it -> it[0] to it[1] }

        return inputs.mapIndexed { index, (left, right) -> if(isInOrder(left, right) > 0) (index + 1) else 0  }.sum()
    }

    override fun part2(lines: List<String>): Any {
        val inputs = lines.filter { it.isNotEmpty() }.toMutableList()
        inputs.add("[[2]]")
        inputs.add("[[6]]")

        return inputs.sortedWith() {a,b -> - isInOrder(a, b)}
            .mapIndexed { index, signal -> if (signal == "[[2]]" || signal == "[[6]]") (index + 1) else 1 }
            .fold(1) {x,y -> x*y}
    }
}