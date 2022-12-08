package advent.code.day1

import advent.code.Problem

data class Elf(val index: Int, val calories: Int) {
    fun addCalories(calories: Int):Elf = Elf(index, this.calories + calories)
}

class CaloriesCounter:Problem {
    fun createElves(lines: List<String>):List<Elf> {
        var index = 1
        val elves = mutableListOf<Elf>();
        var currentElf = Elf(index, 0)
        lines.forEach {
            if (it.isBlank()) {
                index++
                elves.add(currentElf)
                currentElf = Elf(index, 0)
            } else {
                currentElf = currentElf.addCalories(it.toInt())
            }
        }
        return elves;
    }

    fun buildSortedListOfElf(lines: List<String>): List<Elf> {
        val sortedElvesByCalories = lines.run { createElves(this) }
                                        .sortedBy { e -> e.calories }
        println("there are ${sortedElvesByCalories.size} elves")
        return sortedElvesByCalories
    }

    override fun part1(lines: List<String>): Any {
        val sortedElvesByCalories = buildSortedListOfElf(lines)
        val biggestElve = sortedElvesByCalories.last()
        println("the bigger elve is ${biggestElve.index} with ${biggestElve.calories} calories")

        return biggestElve.calories
    }

    override fun part2(lines: List<String>): Any {
        val sortedElvesByCalories = buildSortedListOfElf(lines)
        sortedElvesByCalories.takeLast(3).forEach { println("member of the top : ${it.index} with ${it.calories} calories") }
        val top3Calories = sortedElvesByCalories.takeLast(3).sumOf { it.calories }
        println("Total Calories for the top 3: $top3Calories")

        return top3Calories
    }
}