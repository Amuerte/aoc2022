package advent.code.day2

import advent.code.Problem

enum class Shifumi(val point:Int) {
    A(1) {
        override fun looses(other: Shifumi): Boolean {
            return other == Y || other == B
        }
    },
    B(2) {
        override fun looses(other: Shifumi): Boolean {
            return other == Z || other == C
        }
    },
    C(3) {
        override fun looses(other: Shifumi): Boolean {
            return other == X || other == A
        }
    },
    X(1) {
        override fun looses(other: Shifumi): Boolean {
            return other == Y || other == B
        }
    },
    Y(2) {
        override fun looses(other: Shifumi): Boolean {
            return other == Z || other == C
        }
    },
    Z(3) {
        override fun looses(other: Shifumi): Boolean {
            return other == X || other == A
        }
    };

    abstract fun looses(other: Shifumi):Boolean;

}

class Tournament:Problem {
     fun parseLine(line: String):List<String> = line.split(' ')

    private fun computeScore1(player1: Char, player2: Char): Int =
        computeShifumiScore(player2) + computeBattleScore1(player1, player2)

    private fun computeBattleScore1(player1: Char, player2: Char): Int =
        when(player1.toString() + player2.toString()) {
            "AX" -> 3
            "AY" -> 6
            "AZ" -> 0
            "BX" -> 0
            "BY" -> 3
            "BZ" -> 6
            "CX" -> 6
            "CY" -> 0
            "CZ" -> 3
            else -> 0
        }

    private fun computeScore2(player1: Char, player2: Char): Int {
        val symbol = pickCorrectSymbol(player1, player2)
        return computeBattleScore2(player2) + computeShifumiScore2(symbol)
    }

    private fun pickCorrectSymbol(player1: Char, player2: Char): Char =
        when(player1.toString() + player2.toString()) {
            "AX" -> 'C'
            "AY" -> 'A'
            "AZ" -> 'B'
            "BX" -> 'A'
            "BY" -> 'B'
            "BZ" -> 'C'
            "CX" -> 'B'
            "CY" -> 'C'
            "CZ" -> 'A'
            else -> '-'
        }

    private fun computeBattleScore2(player2: Char): Int =
        when(player2.toString()) {
            "X" -> 0
            "Y" -> 3
            "Z" -> 6
            else -> 0
        }

    private fun computeShifumiScore(shifumi: Char): Int =
        when(shifumi) {
            'X' -> 1
            'Y' -> 2
            'Z' -> 3
            else -> 0
        }

    private fun computeShifumiScore2(shifumi: Char): Int =
        when(shifumi) {
            'A' -> 1
            'B' -> 2
            'C' -> 3
            else -> 0
        }

    override fun part1(lines: List<String>): Any {
        val scores = lines.map { parseLine(it) }.map { computeScore1(it[0].first(), it[1].first()) }
        println(scores.sum())

        return scores.sum()
    }

    override fun part2(lines: List<String>): Any {
        val scores = lines.map { parseLine(it) }.map { computeScore2(it[0].first(), it[1].first()) }
        println(scores.sum())

        return scores.sum()
    }
}