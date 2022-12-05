import java.lang.IllegalArgumentException

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    var totalScore = 0
    input.forEach {
        val roundInput = it.split(" ").map(String::mapToShape)
        val outcome = roundInput[1].round(roundInput[0])
        totalScore += roundInput[1].score + outcome.score
    }
    return totalScore
}

private fun part2(input: List<String>): Int {
    var totalScore = 0
    input.forEach {
        val roundInput = it.split(" ")
        val opponentShape = roundInput[0].mapToShape()
        val expectedOutcome = roundInput[1].mapToOutcome()
        val yourShape = when (expectedOutcome) {
            Outcome.LOSS -> opponentShape.wins
            Outcome.DRAW -> opponentShape
            Outcome.WIN -> opponentShape.loses
        }
        totalScore += yourShape.score + expectedOutcome.score
    }
    return totalScore
}

private enum class Shape(val representations: List<String>, val score: Int) {
    ROCK(listOf("A", "X"), 1),
    PAPER(listOf("B", "Y"), 2),
    SCISSORS(listOf("C", "Z"), 3)
}

private val Shape.wins: Shape
    get() = when (this) {
        Shape.ROCK -> Shape.SCISSORS
        Shape.PAPER -> Shape.ROCK
        Shape.SCISSORS -> Shape.PAPER
    }

private val Shape.loses: Shape
    get() = when (this) {
        Shape.ROCK -> Shape.PAPER
        Shape.PAPER -> Shape.SCISSORS
        Shape.SCISSORS -> Shape.ROCK
    }

private enum class Outcome(val representation: String, val score: Int) {
    LOSS("X", 0),
    DRAW("Y", 3),
    WIN("Z", 6)
}

private fun String.mapToShape(): Shape {
    return Shape.values().first { it.representations.contains(this) }
}

private fun String.mapToOutcome(): Outcome {
    return Outcome.values().first { it.representation == this }
}

private fun Shape.round(otherShape: Shape): Outcome {
    return when {
        this == otherShape -> Outcome.DRAW
        wins == otherShape -> Outcome.WIN
        loses == otherShape -> Outcome.LOSS
        else -> throw IllegalArgumentException()
    }
}
