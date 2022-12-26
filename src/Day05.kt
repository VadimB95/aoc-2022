import java.util.*

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): String {
    val stacksNumberLineIndex = input.indexOfFirst { it.startsWith(" 1") }
    val startStack = parseStartStack(input, stacksNumberLineIndex)
    val moves = parseMoves(stacksNumberLineIndex, input)

    moves.forEach { move ->
        repeat(move.count) {
            startStack[move.to - 1].push(startStack[move.from - 1].pop())
        }
    }
    val answer = startStack.joinToString(separator = "") { it.peek().toString() }
    return answer
}

private fun part2(input: List<String>): String {
    val stacksNumberLineIndex = input.indexOfFirst { it.startsWith(" 1") }
    val startStack = parseStartStack(input, stacksNumberLineIndex)
    val moves = parseMoves(stacksNumberLineIndex, input)

    moves.forEach { move ->
        val cratesToMove = startStack[move.from - 1].subList(0, move.count)
        startStack[move.to - 1].addAll(0, cratesToMove)
        cratesToMove.clear()
    }
    val answer = startStack.joinToString(separator = "") { it.peek().toString() }
    return answer
}

private fun parseStartStack(input: List<String>, stacksNumberLineIndex: Int): List<LinkedList<Char>> {
    val startStack = mutableListOf<LinkedList<Char>>()
    input[stacksNumberLineIndex].forEachIndexed { index, c ->
        if (c.isDigit()) {
            val stack = LinkedList<Char>()
            for (i in stacksNumberLineIndex - 1 downTo 0) {
                val crateMark = input[i][index]
                if (crateMark.isLetter()) {
                    stack.addFirst(crateMark)
                }
            }
            startStack.add(stack)
        }
    }
    return startStack
}

private fun parseMoves(
    stacksNumberLineIndex: Int,
    input: List<String>,
): List<Move> {
    val moves = mutableListOf<Move>()
    for (i in stacksNumberLineIndex + 2..input.lastIndex) {
        val inputSplit = input[i].split("move ", " from ", " to ").filter { it.isNotEmpty() }.map(String::toInt)
        moves.add(Move(inputSplit[0], inputSplit[1], inputSplit[2]))
    }
    return moves
}

private data class Move(
    val count: Int,
    val from: Int,
    val to: Int
)