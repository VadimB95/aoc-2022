fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    var topElfCalories = 0
    var elfCalories = 0
    input.forEach {
        if (it == "") {
            if (elfCalories > topElfCalories) {
                topElfCalories = elfCalories
            }
            elfCalories = 0
        } else {
            elfCalories += it.toInt()
        }
    }
    return topElfCalories
}

private fun part2(input: List<String>): Int {
    val elvesCalories = mutableListOf<Int>()
    var elfCalories = 0
    input.forEach {
        if (it == "") {
            elvesCalories.add(elfCalories)
            elfCalories = 0
        } else {
            elfCalories += it.toInt()
        }
    }
    return elvesCalories.sortedDescending().take(3).sum()
}
