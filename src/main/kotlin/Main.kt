import advent.code.Problem
import advent.code.day1.CaloriesCounter
import advent.code.day2.Tournament
import advent.code.day3.Packer
import advent.code.day4.Cleanup
import advent.code.day5.CratesCane
import advent.code.day6.CommunicationDevice
import advent.code.day7.Filesystem
import advent.code.day8.TreeHouse

fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")

    if (args.size < 1) {
        println("Must indicate day number argument")
    }

    val day = args[0].toInt()
    println("This is day $day")

    val problem: Problem? = when (day) {
        1 -> CaloriesCounter()
        2 -> Tournament()
        3 -> Packer()
        4 -> Cleanup()
        5 -> CratesCane()
        6 -> CommunicationDevice()
        7 -> Filesystem()
        8 -> TreeHouse()
        else -> null
    }

    problem?.run(args) ?: println("Problem for day $day not implemented yet")
}
