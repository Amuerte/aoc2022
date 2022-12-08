package advent.code.day3

import advent.code.Problem

class Packer:Problem {

    fun parseLine(line: String): Pair<List<Char>, List<Char>> {
        val nbChars = line.length
        val midIndex = nbChars / 2
        return Pair(line.toList().subList(0, midIndex ), line.toList().subList(midIndex, nbChars))
    }

    fun findCommon(rucksack: Pair<List<Char>, List<Char>>): Char {
        val compartiment1 = rucksack.first.toSet()
        return rucksack.second.filter { compartiment1.contains(it) }.first()
    }

    fun findCommon(line: String) = findCommon(parseLine(line))


    /**
     * Priority should be :
     *
     * - a -> 1
     * - b -> 2
     * - z -> 26
     * - A -> 27
     * - B -> 28
     * - Z -> 52
     *
     * Ascii code
     *
     * - A = 65
     * - Z = 90
     * - a = 97
     * - z = 122
     *
     */
    fun priority(item: Char): Int {
        return (item.code - 'a'.code + 1).mod(58)
    }

    override fun part1(lines: List<String>): Any {
        val priorities = lines.map { findCommon(it) }.map { priority(it) }
        println(priorities.sum())
        return priorities.sum()
    }

    override fun part2(lines: List<String>): Any {
        var i = 0
        var j = 1
        var priorities = mutableListOf<Char>()
        var groupRucksack = mutableListOf<String>()
        lines.forEach {
            if (i != 0 && i.mod(3) == 0) {
                println(groupRucksack)
                print("nbgroup: $j\n")
                j++
                priorities.add(findBadge(groupRucksack))
                groupRucksack = mutableListOf(it)

            } else {
                groupRucksack.add(it)
            }
            i++
        }

        println(groupRucksack)
        print("nbgroup: $j\n")
        j++
        priorities.add(findBadge(groupRucksack))

        println(priorities.size)
        return priorities.map { priority(it)}.sum()
    }

    private fun findBadge(groupRucksack: MutableList<String>): Char {
        val sack1 = groupRucksack[0].toSet()
        val commons = groupRucksack[1].toSet().filter { sack1.contains(it) }
        val common = groupRucksack[2].toSet().filter { commons.contains(it) }

        return common.first()
    }
}