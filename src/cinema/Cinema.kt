package cinema

const val TICKETS_NORMAL_VALUE = 10
const val TICKETS_BACKSEATS_VALUE = 8

typealias CinemaSeats = MutableList<MutableList<String>>

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()
    println()
    val cinemaSeats = MutableList(rows) { MutableList(seatsPerRow) { "S" } }
    showMenu(cinemaSeats)
}

private fun showMenu(cinemaSeats: CinemaSeats) {
    var currentIncome = 0
    do {
        println(
            """
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
            """.trimIndent()
        )
        var shouldExitMenu = false
        val option = readln().toInt()
        println()
        when (option) {
            1 -> printSeats(cinemaSeats)
            2 -> {
                val ticketValue = chooseSeatAndReturnPrice(cinemaSeats)
                currentIncome += ticketValue
            }
            3 -> printStatistics(cinemaSeats, currentIncome)
            0 -> shouldExitMenu = true
        }
        println()
    } while (!shouldExitMenu)
}

private fun printSeats(cinemaSeats: CinemaSeats) {
    println("Cinema:")
    print("  ")
    for (i in 1..cinemaSeats[0].size) {
        print("$i ")
    }
    println()

    for (i in 0 until cinemaSeats.size) {
        print("${i + 1} ")
        println((cinemaSeats[i]).joinToString(" "))
    }
}

private fun chooseSeatAndReturnPrice(cinemaSeats: CinemaSeats): Int {
    var ticketValue: Int? = null
    do {
        println()
        println("Enter a row number:")
        val chosenRow = readln().toInt()
        println("Enter a seat number in that row:")
        val chosenSeat = readln().toInt()

        val rows = cinemaSeats.size
        val seatsPerRow = cinemaSeats[0].size
        if (chosenRow > rows || chosenSeat > seatsPerRow) {
            println("Wrong input!")
        } else if (cinemaSeats[chosenRow - 1][chosenSeat - 1] == "B") {
            println("That ticket has already been purchased!")
        } else {
            cinemaSeats[chosenRow - 1][chosenSeat - 1] = "B"
            val totalSeats = calculateTotalSeats(rows, seatsPerRow)
            ticketValue = calculateTicketValue(chosenRow, totalSeats, rows)
            println("Ticket price: $$ticketValue")
        }
    } while (ticketValue == null)
    return ticketValue
}

private fun calculateTotalSeats(rows: Int, seatsPerRow: Int): Int = seatsPerRow * rows

fun calculateTicketValue(chosenRow: Int, totalSeats: Int, rows: Int): Int {
    return if (totalSeats < 60 || chosenRow <= rows / 2) {
        TICKETS_NORMAL_VALUE
    } else {
        TICKETS_BACKSEATS_VALUE
    }
}

private fun printStatistics(cinemaSeats: CinemaSeats, currentIncome: Int) {
    val purchasedSeats = cinemaSeats.calculatePurchasedSeats()
    val rows = cinemaSeats.size
    val seatsPerRow = cinemaSeats[0].size
    println("Number of purchased tickets: $purchasedSeats")

    val totalSeats = calculateTotalSeats(rows, seatsPerRow)
    val percentage = (100.0 * purchasedSeats) / totalSeats
    println("Percentage: %.2f%%".format(percentage))

    println("Current income: $$currentIncome")
    val totalIncome = calculateTotalIncome(rows, seatsPerRow)
    println("Total income: $$totalIncome")
}

private fun CinemaSeats.calculatePurchasedSeats(): Int {
    var bookedSeats = 0
    for (row in this) {
        for (seat in row) {
            if (seat == "B") bookedSeats++
        }
    }
    return bookedSeats
}

private fun calculateTotalIncome(rows: Int, seatsPerRow: Int): Int {
    val totalSeats = calculateTotalSeats(rows, seatsPerRow)
    return if (totalSeats < 60) {
        totalSeats * TICKETS_NORMAL_VALUE
    } else {
        val totalRowsFrontHalf = rows / 2
        val totalRowsBackHalf = rows - rows / 2
        val frontHalfSeats = seatsPerRow * totalRowsFrontHalf
        val backHalfSeats = seatsPerRow * totalRowsBackHalf
        (frontHalfSeats * TICKETS_NORMAL_VALUE) + (backHalfSeats * TICKETS_BACKSEATS_VALUE)
    }
}


