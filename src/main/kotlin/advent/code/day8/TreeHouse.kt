package advent.code.day8

import advent.code.Problem

fun parseLine(line: String): List<Int> = line.toList().map { it.toString().toInt() }

fun isEdge(i: Int, j: Int, nbRow: Int, nbColumn: Int) = (i == 0 || i == nbRow - 1 || j == 0 || j == nbColumn - 1)

fun checkUp(i: Int, j: Int, trees: List<List<Int>>): List<Int> {
    return (i-1 downTo 0).map { trees[it][j] }
}

fun checkDown(i: Int, j: Int, trees: List<List<Int>>, nbRow: Int): List<Int> {
    return (i + 1 until nbRow).map { trees[it][j] }
}

fun checkLeft(i: Int, j: Int, trees: List<List<Int>>): List<Int> {
    return (j-1 downTo 0).map { trees[i][it] }
}

fun checkRight(i: Int, j: Int, trees: List<List<Int>>, nbColumn: Int): List<Int> {
    return (j + 1 until nbColumn).map { trees[i][it] }
}

fun lengthPathToMaxUp(i: Int, j: Int, trees: List<List<Int>>): Int {
    val length = checkUp(i, j, trees).takeWhile { it < trees[i][j] }.size
    // check if we can see until edge
    return if (length == i) length else length + 1
}

fun lengthPathToMaxDown(i: Int, j: Int, trees: List<List<Int>>, nbRow: Int): Int {
    val length = checkDown(i, j, trees, nbRow).takeWhile { it < trees[i][j] }.size
    // check if we can see until edge
    return if (length == (nbRow - i - 1)) length else length + 1
}

fun lengthPathToMaxLeft(i: Int, j: Int, trees: List<List<Int>>): Int {
    val length = checkLeft(i, j, trees).takeWhile { it < trees[i][j] }.size
    // check if we can see until edge
    return if (length == j) length else length + 1
}

fun lengthPathToMaxRight(i: Int, j: Int, trees: List<List<Int>>, nbColumn: Int): Int {
    val length = checkRight(i, j, trees, nbColumn).takeWhile { it < trees[i][j] }.size
    // check if we can see until edge
    return if (length == (nbColumn - j -1)) length else length + 1
}

class TreeHouse : Problem {
    override fun part1(lines: List<String>): Any {
        val trees: List<List<Int>> = lines.map { parseLine(it) }

        var nbEdge = 0
        var nbInside = 0

        val nbRow = trees.size
        val nbColumn = trees[0].size


        for (i in 0 until nbRow) {
            for (j in 0 until nbColumn) {
                var currentVal = trees[i][j]

                if (isEdge(i, j, nbRow, nbColumn)) {
                    nbEdge++
                } else {
                    if (checkUp(i, j, trees).max() < currentVal ||
                        checkDown(i, j, trees, nbRow).max() < currentVal ||
                        checkLeft(i, j, trees).max() < currentVal ||
                        checkRight(i, j, trees, nbColumn).max() < currentVal
                    ) {
                        nbInside++
                    }
                }
            }
        }

        println("edge: $nbEdge; inside: $nbInside")
        return nbEdge + nbInside
    }

    override fun part2(lines: List<String>): Any {
        val trees: List<List<Int>> = lines.map { parseLine(it) }

        var maxScenic = 0

        val nbRow = trees.size
        val nbColumn = trees[0].size

        for (i in 0 until nbRow) {
            for (j in 0 until nbColumn) {
                var currentScenicScore = 1

                if (i > 0) {
                    currentScenicScore *= lengthPathToMaxUp(i, j, trees)
                }
                if (i < nbRow - 1) {
                    currentScenicScore *= lengthPathToMaxDown(i, j, trees, nbRow)
                }
                if (j > 0) {
                    currentScenicScore *= lengthPathToMaxLeft(i, j, trees)
                }
                if (j < nbColumn - 1) {
                    currentScenicScore *= lengthPathToMaxRight(i, j, trees, nbColumn)
                }

                if (currentScenicScore > maxScenic) {
                    maxScenic = currentScenicScore
                }
            }
        }
        return maxScenic
    }
}