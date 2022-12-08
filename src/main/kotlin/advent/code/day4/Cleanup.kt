package advent.code.day4

import advent.code.Problem

data class Section(val start:Int, val end: Int) {
    fun length(): Int = end - start + 1

    fun contains(other: Section): Boolean {
        return (this.start <= other.start && this.end >= other.end)
    }

    fun intersect(other: Section): Boolean {
        return (this.start >= other.start && this.start <= other.end ) || (other.start >= this.start && other.start <= this.end)
    }
}

class Cleanup:Problem {

    fun parseLine(line: String):Pair<Section, Section> {
        val sections = line.split(",")
            .map { it.split("-") }
            .map { Section(it[0].toInt(), it[1].toInt()) }
            .take(2)

        return if (sections[0].length() < sections[1].length()) {
            Pair(sections[0], sections[1])
        } else {
            Pair(sections[1], sections[0])
        }
    }

    override fun part1(lines: List<String>): Any {
        return lines.map { parseLine(it) }.map {
            val lilSection = it.first
            val bigSection = it.second

            bigSection.contains(lilSection)
        }.filter { it }.count()
    }

    override fun part2(lines: List<String>): Any {
        return lines.map { parseLine(it) }.map {
            val lilSection = it.first
            val bigSection = it.second

            bigSection.intersect(lilSection)
        }.filter { it }.count()
    }
}