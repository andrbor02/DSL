package `Tinkoff tasks`


import kotlin.math.ceil
import java.util.*
import java.util.Collections.swap

fun main(args: Array<String>) {
    task2()
}

fun task1() {
    val input = Scanner(System.`in`)

    val A = input.nextInt()
    val B = input.nextInt()
    val N = input.nextInt()

    val minB = ceil((B.toDouble() / N.toDouble()))

    if (A > minB) {
        print("Yes")
    } else {
        print("No")
    }
}

fun task3() {
    val input = Scanner(System.`in`)

    var X = input.nextInt()

    while (X % 10 == 0) {
        X /= 10
    }

    var counter = 0
    while (X / 10 > 0) {
        if (X % 10 == 0) {
            counter++
        }
        X /= 10
    }

    print(counter)
}

fun task4() {
    val input = Scanner(System.`in`)

    val n = input.nextInt()
    val table: Array<Array<Char>> = Array(n, { Array(n, { '?' }) })
    var symbol = 98
    val middle = ceil(n.toDouble() / 2.0).toInt() - 1

    for (i in 0..n - 1) {
        table[i][i] = 'a'
        table[i][n - 1 - i] = 'a'
    }

    for (x in 1..middle) {
        var i = 0
        var j = x
        do {
            table[i][j] = symbol.toChar()
            if (n % 2 == 0 && j == middle) {
                j++
                continue
            }
            if (j < middle) {
                i++
            } else {
                i--
            }
            j++
        } while (i >= 0)
        if (symbol < 122) {
            symbol++
        } else {
            symbol = 97
        }
    }

    var start = 1
    var end = n - 1
    for (i in 0 until middle) {
        for (j in start until end) {
            table[j][n - i - 1] = table[i][j]
            table[n - i - 1][n - j - 1] = table[i][j]
            table[j][i] = table[i][j]
        }
        start++
        end--
    }

    for (row in table) {
        for (cell in row) {
            print("$cell")
        }
        println()
    }
}

fun task2() {
    val input = Scanner(System.`in`)

    val ab = input.nextLine()
    val ac = input.nextLine()
    val bc = input.nextLine()

    val first = mutableListOf("a", "b", "c")
    val second = MutableList(1, { "" })
    val third = MutableList(1, { "" })

    val arr = mapOf(1 to first, 2 to second, 3 to third)

    if (ab == ">") {
        moveTo(arr, "a", 1, 2)
    } else if (ab == "<") {
        moveTo(arr, "b", 1, 2)
    }

    if (ac == ">") {
        if (arr[1]?.contains("a") == true) {
            moveTo(arr, "a", 1, 2)
        } else {
            moveTo(arr, "a", 2, 3)
        }
    } else if (ac == "<") {
        moveTo(arr, "c", 1, 2)
    }

    if (bc == ">") {
        if (arr[1]?.contains("b") == true) {
            moveTo(arr, "b", 1, 2)
        } else {
            moveTo(arr, "b", 2, 3)
        }
    } else if (bc == "<") {
        if (arr[1]?.contains("c") == true) {
            moveTo(arr, "c", 1, 2)
        } else {
            moveTo(arr, "c", 2, 3)
        }
    }

    arr[2]?.sort()

    val finalList = MutableList<String>(1, { "" })
    for (permutation1 in arr[1]?.permutations()!!) {
        for (permutation2 in arr[2]?.permutations() ?: listOf(listOf(""))) {
            finalList.add(permutation1.joinToString("") + permutation2.joinToString("") + (arr[3]?.get(0) ?: ""))
        }
    }

    finalList.remove("")
    print(finalList.sorted().joinToString("\n"))

}

fun moveTo(arr: Map<Int, MutableList<String>>, elem: String, key1: Int, key2: Int) {
    arr[key2]?.remove("")
    arr[key1]?.remove(elem)
    arr[key2]?.add(elem)
}

fun <V> MutableList<V>.permutations(): List<List<V>> {
    val permutList: MutableList<List<V>> = mutableListOf()

    fun generate(k: Int, list: List<V>) {
        if (k == 1) {
            permutList.add(list.toList())
        } else {
            for (i in 0 until k) {
                generate(k - 1, list)
                if (k % 2 == 0) {
                    swap(list, i, k - 1)
                } else {
                    swap(list, 0, k - 1)
                }
            }
        }
    }

    generate(this.count(), this.toList())
    return permutList
}

fun task6() {
    val input = Scanner(System.`in`)

    val n = input.nextInt()
    val h = input.nextInt()
    val m = input.nextInt()
    val k = input.nextInt()

    repeat(n) {

    }

    // h * m + m 1500
    // h * m + m 1800
    // 1678
}