import advent.code.Problem
import advent.code.day1.CaloriesCounter
import advent.code.day10.CathodeRayTube
import advent.code.day11.MonkeyMiddle
import advent.code.day12.HillClimbing
import advent.code.day13.DistressSignal
import advent.code.day14.RegolithReservoir
import advent.code.day15.BeaconExclusion
import advent.code.day16.Volcano
import advent.code.day17.PyroclasticFlow
import advent.code.day18.BoilingBoulders
import advent.code.day19.OreCollect
import advent.code.day2.Tournament
import advent.code.day3.Packer
import advent.code.day4.Cleanup
import advent.code.day5.CratesCane
import advent.code.day6.CommunicationDevice
import advent.code.day7.Filesystem
import advent.code.day8.TreeHouse
import advent.code.day9.RopeBridge

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
        9 -> RopeBridge()
        10 -> CathodeRayTube()
        11 -> MonkeyMiddle()
        12 -> HillClimbing()
        13 -> DistressSignal()
        14 -> RegolithReservoir()
        15 -> BeaconExclusion()
        16 -> Volcano()
        17 -> PyroclasticFlow()
        18 -> BoilingBoulders()
        19 -> OreCollect()
        else -> null
    }

    problem?.run(args) ?: println("Problem for day $day not implemented yet")
}
