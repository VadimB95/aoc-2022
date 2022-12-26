fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val (directories) = scanFs(input)
    return directories.map { it.getSize() }.filter { it <= 100000 }.sum().toInt()
}

private fun part2(input: List<String>): Int {
    val (directories, root) = scanFs(input)
    val freeSpace = 70000000 - root.getSize()
    val spaceNeeded = 30000000 - freeSpace
    val directoryToDeleteSize = directories.map { it.getSize() }.sorted().first { it >= spaceNeeded }
    return directoryToDeleteSize.toInt()
}

private fun scanFs(
    input: List<String>
): Pair<List<FsEntry.Directory>, FsEntry.Directory> {
    val directories = mutableListOf<FsEntry.Directory>()
    val rootDirectory = FsEntry.Directory("/", null)
    var head: FsEntry.Directory = rootDirectory
    directories.add(rootDirectory)
    input.forEach {
        when (val inputParsed = parseInput(it)) {
            is Input.Command.Cd.In -> {
                head = head.entries.filterIsInstance<FsEntry.Directory>().find { directory ->
                    directory.name == inputParsed.directory
                }!!
            }

            Input.Command.Cd.Out -> {
                head = head.parentDirectory!!
            }

            Input.Command.Cd.Root -> head = rootDirectory
            Input.Command.Ls -> Unit
            is Input.LsEntry.Directory -> {
                val newDirectory = FsEntry.Directory(inputParsed.name, head)
                head.entries.add(newDirectory)
                directories.add(newDirectory)
            }

            is Input.LsEntry.File -> {
                head.entries.add(FsEntry.File(inputParsed.name, head, inputParsed.size))
            }
        }
    }
    return directories to rootDirectory
}

private fun parseInput(inputString: String): Input {
    val inputSegments = inputString.split(" ")
    return when {
        inputSegments[0] == "\$" && inputSegments[1] == "cd" -> {
            when (inputSegments[2]) {
                "/" -> Input.Command.Cd.Root
                ".." -> Input.Command.Cd.Out
                else -> Input.Command.Cd.In(inputSegments[2])
            }
        }

        inputSegments[0] == "\$" && inputSegments[1] == "ls" -> Input.Command.Ls
        inputSegments[0] == "dir" -> Input.LsEntry.Directory(inputSegments[1])
        else -> Input.LsEntry.File(inputSegments[1], inputSegments[0].toLong())
    }
}

private sealed interface FsEntry {
    val name: String
    val parentDirectory: Directory?

    fun getSize(): Long

    data class Directory(
        override val name: String,
        override val parentDirectory: Directory?
    ) : FsEntry {
        val entries = mutableListOf<FsEntry>()
        override fun getSize(): Long = entries.sumOf { it.getSize() }
    }

    data class File(
        override val name: String,
        override val parentDirectory: Directory,
        val fileSize: Long
    ) : FsEntry {
        override fun getSize(): Long = fileSize
    }
}

private sealed class Input {
    sealed class Command : Input() {
        sealed class Cd : Command() {
            object Root : Cd()
            object Out : Cd()
            data class In(val directory: String) : Cd()
        }

        object Ls : Command()
    }

    sealed class LsEntry : Input() {
        abstract val name: String

        data class Directory(override val name: String) : LsEntry()
        data class File(
            override val name: String,
            val size: Long
        ) : LsEntry()
    }
}