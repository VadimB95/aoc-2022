import java.util.*

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsString("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInputAsString("Day06")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: String): Int {
    return scanForMarker(input, 4)
}

private fun part2(input: String): Int {
    return scanForMarker(input, 14)
}

private fun scanForMarker(input: String, markerCharactersCount: Int): Int {
    input.scanIndexed(LinkedList<Char>()) { index, acc, c ->
        acc.add(c)
        if (acc.size > markerCharactersCount) {
            acc.removeFirst()
        }
        if (acc.size == markerCharactersCount && acc.distinct().size == markerCharactersCount) {
            return@scanForMarker index + 1
        }
        acc
    }
    return 0
}
