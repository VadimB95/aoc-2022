fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    var fullyContainedPairsCount = 0
    input.forEach { lineInput ->
        val parsedInput = lineInput.split(",", "-").map(String::toInt)
        val isFirstContainsSecond = parsedInput[0] <= parsedInput[2] && parsedInput[1] >= parsedInput[3]
        val isSecondContainsFirst = parsedInput[2] <= parsedInput[0] && parsedInput[3] >= parsedInput[1]
        if (isFirstContainsSecond || isSecondContainsFirst) fullyContainedPairsCount++
    }
    return fullyContainedPairsCount
}

private fun part2(input: List<String>): Int {
    var overlappedPairsCount = 0
    input.forEach { lineInput ->
        val parsedInput = lineInput.split(",", "-").map(String::toInt)
        val isNotOverlapped = parsedInput[1] < parsedInput[2] || parsedInput[3] < parsedInput[0]
        if (!isNotOverlapped) overlappedPairsCount++
    }
    return overlappedPairsCount
}
