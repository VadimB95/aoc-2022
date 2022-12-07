import java.lang.IllegalArgumentException

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val allCommonItems = mutableListOf<Char>()
    input.forEach { rucksackInput ->
        val itemsCount = rucksackInput.length
        val compartment1 = rucksackInput.substring(0, itemsCount / 2).toSet()
        val compartment2 = rucksackInput.substring(itemsCount / 2, rucksackInput.length).toSet()
        val commonItems = compartment1.intersect(compartment2)
        allCommonItems.addAll(commonItems)
    }
    return allCommonItems.sumOf { it.convertToPriority() }
}

private fun part2(input: List<String>): Int {
    val groups = input.chunked(3)
    val badges = mutableListOf<Char>()
    groups.forEach { group ->
        val badge = group[0].toSet().intersect(group[1].toSet()).intersect(group[2].toSet()).first()
        badges.add(badge)
    }
    return badges.sumOf { it.convertToPriority() }
}

private fun Char.convertToPriority(): Int {
    return when (this) {
        in ('a'..'z') -> code - 96
        in ('A'..'Z') -> code - 38
        else -> throw IllegalArgumentException()
    }
}
