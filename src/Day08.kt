fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val inputMatrix = parseInput(input)
    var visibleTreesCount = 0
    for (i in input.indices) {
        for (j in input.indices) {
            val isVisibleLeft = isVisible(inputMatrix[i].sliceArray(0..j))
            val isVisibleRight = isVisible(inputMatrix[i].sliceArray(j..input.lastIndex).reversedArray())
            val topHeights = mutableListOf<Int>()
            for (k in 0..i) {
                topHeights.add(inputMatrix[k][j])
            }
            val isVisibleTop = isVisible(topHeights.toIntArray())
            val bottomHeights = mutableListOf<Int>()
            for (k in i..input.lastIndex) {
                bottomHeights.add(inputMatrix[k][j])
            }
            val isVisibleBottom = isVisible(bottomHeights.toIntArray().reversedArray())
            val isTreeVisible = isVisibleLeft || isVisibleRight || isVisibleTop || isVisibleBottom
            if (isTreeVisible) visibleTreesCount++
        }
    }
    return visibleTreesCount
}

private fun parseInput(input: List<String>): List<IntArray> =
    input.map { it.split("").filter(String::isNotBlank).map(String::toInt).toIntArray() }

private fun isVisible(heights: IntArray): Boolean {
    if (heights.size <= 1) return true
    var topHeight = 0
    val currentTreeHeight = heights.last()
    for (i in 0 until heights.lastIndex) {
        val height = heights[i]
        if (height > topHeight) {
            topHeight = height
        }
    }
    return topHeight < currentTreeHeight
}

private fun part2(input: List<String>): Int {
    val inputMatrix = parseInput(input)
    var highestScenicScore = 0
    for (i in input.indices) {
        for (j in input.indices) {
            val visibilityLeft = getVisibilityDistance(inputMatrix[i].sliceArray(0..j).reversedArray())
            val visibilityRight = getVisibilityDistance(inputMatrix[i].sliceArray(j..input.lastIndex))
            val topHeights = mutableListOf<Int>()
            for (k in i downTo 0) {
                topHeights.add(inputMatrix[k][j])
            }
            val visibilityTop = getVisibilityDistance(topHeights.toIntArray())
            val bottomHeights = mutableListOf<Int>()
            for (k in i..input.lastIndex) {
                bottomHeights.add(inputMatrix[k][j])
            }
            val visibilityBottom = getVisibilityDistance(bottomHeights.toIntArray())
            val scenicScore = visibilityLeft * visibilityRight * visibilityTop * visibilityBottom
            if (scenicScore > highestScenicScore) {
                highestScenicScore = scenicScore
            }
        }
    }
    return highestScenicScore
}

private fun getVisibilityDistance(input: IntArray): Int {
    if (input.size <= 1) return 0
    val currentTreeHeight = input.first()
    var visibilityDistance = 0
    for (i in 1..input.lastIndex) {
        visibilityDistance++
        if (input[i] >= currentTreeHeight) break
    }
    return visibilityDistance
}
