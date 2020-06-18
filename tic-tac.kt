package main


var board = mutableListOf<Int>(0,0,0,
                        0,0,0,
                        0,0,0)
fun getIndex(x:Int, y: Int):Int{
    return if(x in 0..2 && y in 0..2) y*3+x
           else -1
}
fun place(figure:Int, x:Int, y:Int):Boolean{
    val index = getIndex(x,y)
    if(index in 0..8){
        if(board[index] !== 0){
            return false
        } else{
            board[index] = figure
            return true
        }
    }else{
        return false
    }
}
fun winner(board: List<Int>):Int{
    //Check vertical
    for(i in 0..2){
        val arr = listOf(board[i], board[i+3], board[i+6])
        if(arr.max() === arr.min()){
            if(board[i] > 0) return board[i]
        }
    }
    //Check horizontal
    for(i in 0..2){
        val arr = listOf(board[i*3], board[i*3+1], board[i*3+2])
        if(arr.max() === arr.min()){
            if(board[i*3] > 0) return board[i*3]
        }
    }
    //Check diagonal
    val f_check  = listOf(board[0], board[4],  board[8])
    if(f_check.max() === f_check.min()){
        if(board[0] > 0) return board[0]
    }
    val s_check = listOf(board[2], board[4], board[6])
    if(s_check.max() === s_check.min()){
        if(board[0] > 0) return board[2]
    }
    //Is draw?
    if(board.min() != 0) return -1

    return 0
}

fun draw_board(board: List<Int>){
    val vls = listOf<String>("  ", " X", " O")

    println("---------")
    for(i in 0..8 step 3){
        println("|"+vls[board[i]]+vls[board[i+1]]+vls[board[i+2]]+" |")
    }
    println("---------")
}

fun main(args: Array<String>) {
    val vls = listOf<String>("  ", " X", " O")

    println("Throwing coin... ")
    var motion = (1..2).random()

    println("${vls[motion]} is starting game")

    var playable = true
    while (playable) {
        draw_board(board)
        print("${vls[motion]} your turn! Enter coordinates (x y): ")
        var input = readLine()
        if (input != null) {
            if (input.length == 3) {
                val coordinates = input.split(" ")
                try {
                    val x = coordinates[0].toInt()
                    val y = coordinates[1].toInt()
                    if (place(motion,x, y)){
                        println("Successfully placed!")
                        when(winner(board)){
                            0 -> {
                                motion = if (motion == 1) 2
                                else 1
                            }
                            else -> playable = false
                        }

                    }else{
                        println("Error! Not correct ceil, or ceil already placed")
                    }
                }catch (e: NumberFormatException){
                    println("Not correct input!")
                }


            } else println("Not correct input!")
        }

    }
    draw_board(board)
    when(winner(board)){
        -1 -> println("Draw!")
        1 -> println("X is winner!")
        2 -> println("O is winner!")
    }
}


