package cinema

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()

    println("Enter the number of seats in each row:")
    val seats = readln().toInt()

    val cinema = MutableList(rows) { MutableList(seats) { 'S' } }

    while (true) {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")

        val input = readln().toInt()
        when (input) {
            1 -> printCinema(seats, cinema)
            2 -> buyTicket(rows, seats, cinema)
            3 -> printStatistics(rows, seats, cinema)
            0 -> return
        }
    }
}

private fun buyTicket(rows: Int, seats: Int, cinema: MutableList<MutableList<Char>>) {
    try {
        println("Enter a row number:")
        val row = readln().toInt()
        if (row > rows) throw Exception("Wrong input!")

        println("Enter a seat number in that row:")
        val seat = readln().toInt()
        if (seat > seats) throw Exception("Wrong input!")

        val price = if (rows * seats <= 60 || row <= rows / 2) 10 else 8

        println("Ticket price: \$$price")
        println()

        if (cinema[row - 1][seat - 1] == 'B') throw Exception("That ticket has already been purchased!")
        cinema[row - 1][seat - 1] = 'B'
    } catch (e: Exception) {
        println(e.message)
        buyTicket(rows, seats, cinema)
    }
}

private fun printCinema(seats: Int, cinema: MutableList<MutableList<Char>>) {
    println()
    println("Cinema:")
    print(" ")
    for (seat in 1..seats) {
        print(" $seat")
    }
    println()
    for (index in cinema.indices) {
        println("${index + 1} ${cinema[index].joinToString(" ")}")
    }
}

private fun printStatistics(rows: Int, seats: Int, cinema: MutableList<MutableList<Char>>) {
    val numberOfPurchasedTickets = cinema.sumOf { row -> row.count { it == 'B' } }
    val percentage = numberOfPurchasedTickets.toDouble() / (rows * seats) * 100
    val currentIncome = getCurrentIncome(rows, seats, cinema)
    val totalIncome = if (rows * seats <= 60) rows * seats * 10 else (rows / 2 * 10 + (rows - rows / 2) * 8) * seats

    println("Number of purchased tickets: $numberOfPurchasedTickets")
    println("Percentage: ${String.format("%.2f", percentage)}%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}

private fun getCurrentIncome(rows: Int, seats: Int, cinema: MutableList<MutableList<Char>>): Int {
    var income = 0

    for (index in cinema.indices) {
        for (seat in cinema[index]) {
            if (seat == 'B') {
                val price = if (rows * seats <= 60 || (index + 1) <= rows / 2) 10 else 8
                income += price
            }
        }
    }

    return income
}