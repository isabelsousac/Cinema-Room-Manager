package cinema

const val TICKETS_NORMAL_VALUE = 10
const val TICKETS_BACKSEATS_VALUE = 8

typealias CinemaSeats = MutableList<MutableList<String>>

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()
    val cinemaSeats = MutableList(rows) { MutableList(seatsPerRow) { "S" } }

    do {
        val shouldExitMenu = showMenu(cinemaSeats)
        println()
    } while (!shouldExitMenu)
}

private fun showMenu(cinemaSeats: CinemaSeats): Boolean {
    println(
        """
        1. Show the seats
        2. Buy a ticket
        0. Exit
        """.trimIndent()
    )
    when (readln().toInt()) {
        1 -> printSeats(cinemaSeats)
        2 -> chooseSeat(cinemaSeats)
        0 -> return true
    }
    return false
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

private fun chooseSeat(cinemaSeats: CinemaSeats) {
    println("Enter a row number:")
    val chosenRow = readln().toInt()
    println("Enter a seat number in that row:")
    val chosenSeat = readln().toInt()

    val rows = cinemaSeats.size
    val seatsPerRow = cinemaSeats[0].size
    if (chosenRow > rows || chosenSeat > seatsPerRow) {
        println("Choose a valid seat")
        return
    }
    cinemaSeats[chosenRow - 1][chosenSeat - 1] = "B"
    val totalSeats = calculateTotalSeats(rows, seatsPerRow)
    val calculateTicketValue = calculateTicketValue(chosenRow, totalSeats, rows)
    println("Ticket price: $$calculateTicketValue")

}

private fun calculateTotalSeats(rows: Int, seatsPerRow: Int): Int = seatsPerRow * rows

fun calculateTicketValue(chosenRow: Int, totalSeats: Int, rows: Int): Int {
    return if (totalSeats < 60 || chosenRow <= rows / 2) {
        TICKETS_NORMAL_VALUE
    } else {
        TICKETS_BACKSEATS_VALUE
    }
}

private fun calculateIncome(rows: Int, seatsPerRow: Int) {
    val totalSeats = calculateTotalSeats(rows, seatsPerRow)
    val totalIncome = if (totalSeats < 60) {
        totalSeats * TICKETS_NORMAL_VALUE
    } else {
        val totalRowsFrontHalf = rows / 2
        val totalRowsBackHalf = rows - rows / 2
        val frontHalfSeats = seatsPerRow * totalRowsFrontHalf
        val backHalfSeats = seatsPerRow * totalRowsBackHalf
        (frontHalfSeats * TICKETS_NORMAL_VALUE) + (backHalfSeats * TICKETS_BACKSEATS_VALUE)
    }
    println("Total income:")
    println("$$totalIncome")
}

