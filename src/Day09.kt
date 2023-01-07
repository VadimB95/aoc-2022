import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    return getVisitedCount(input, 2)
}

private fun part2(input: List<String>): Int {
    return getVisitedCount(input, 10)
}

private fun getVisitedCount(input: List<String>, knotsCount: Int): Int {
    val startCoordinates = Coordinates(0, 0)
    val knotsCoordinates = List(knotsCount) { startCoordinates }.toMutableList()
    val visited = HashSet<Coordinates>()
    visited.add(startCoordinates)
    for (line in input) {
        val command = parseCommand(line)
        repeat(command.moves) {
            var headX = knotsCoordinates[0].x
            var headY = knotsCoordinates[0].y
            when (command.direction) {
                Direction.R -> headX++
                Direction.L -> headX--
                Direction.U -> headY++
                Direction.D -> headY--
            }
            knotsCoordinates[0] = Coordinates(headX, headY)
            for (i in 1..knotsCoordinates.lastIndex) {
                knotsCoordinates[i] = getNewKnotCoordinates(knotsCoordinates[i], knotsCoordinates[i - 1])
            }
            visited.add(knotsCoordinates.last())
        }
    }
    return visited.size
}

private fun getNewKnotCoordinates(oldKnotCoordinates: Coordinates, leadingKnotCoordinates: Coordinates): Coordinates {
    val isUnchanged = (leadingKnotCoordinates.x - oldKnotCoordinates.x).absoluteValue <= 1 &&
            (leadingKnotCoordinates.y - oldKnotCoordinates.y).absoluteValue <= 1
    if (isUnchanged) return oldKnotCoordinates
    var x = oldKnotCoordinates.x
    var y = oldKnotCoordinates.y
    x += (leadingKnotCoordinates.x - oldKnotCoordinates.x).sign
    y += (leadingKnotCoordinates.y - oldKnotCoordinates.y).sign
    return Coordinates(x, y)
}

private fun parseCommand(line: String): Command {
    val split = line.split(" ")
    return Command(Direction.valueOf(split[0]), split[1].toInt())
}

data class Command(
    val direction: Direction,
    val moves: Int
)

enum class Direction {
    R, L, U, D
}

data class Coordinates(val x: Int, val y: Int)