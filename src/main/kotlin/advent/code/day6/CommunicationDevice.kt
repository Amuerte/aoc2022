package advent.code.day6

import advent.code.Problem


class CommunicationDevice:Problem {
    val stream1 = "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 7
    val stream2 = "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5
    val stream3 = "nppdvjthqldpwncqszvftbrmjlhg" to 6
    val stream4 = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10
    val stream5 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11

    val stream6 = "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19
    val stream7 = "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23
    val stream8 = "nppdvjthqldpwncqszvftbrmjlhg" to 23
    val stream9 = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29
    val stream10 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26

    fun readStream(datastream: String, size:Int):List<String> = datastream.windowed(size, 1, true)

    fun areUniqueLetters(word:String, size: Int):Boolean = (word.toSet().size == size)

    fun findStartSeqPos(datastream: String, size: Int): Int =
        readStream(datastream, size).indexOfFirst { areUniqueLetters(it, size) } + size

    override fun part1(lines: List<String>): Any {
        return findStartSeqPos(lines.first(), 4)
    }

    override fun part2(lines: List<String>): Any {
        return findStartSeqPos(lines.first(), 14)
    }
}