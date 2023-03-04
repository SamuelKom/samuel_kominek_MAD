/**
 * MAD LD_01
 * Samuel Kominek
 */
// for the implementation chars are used to allow leading '0' e.g. 0297

// function to generate random guessing number
fun randomNum() : CharArray {
    val numList = mutableListOf('0', '1', '2', '3', '4', '5', '6', '7' ,'8' ,'9')
    val numCombination = charArrayOf('0', '0', '0', '0')

    for(i in 0..3) {
        numCombination[i] = numList.random()
        numList.remove(numCombination[i])
    }

    return numCombination
}

fun main() {
    val numberArray = randomNum() // get random number array to guess
    var guessArray = charArrayOf('0', '0', '0', '0')


    while (!numberArray.contentEquals(guessArray)) { // check if correct combination has been guessed
        var isValid = true
        print("Enter a 4-digit number to guess: ")
        val guess = readLine() // read user input

        // check for invalid input
        if(guess != null && guess.length == 4) {
            guessArray = guess.toCharArray() // convert input into charArray for easier use
            for (i in guessArray) {
                if (i < 48.toChar() || i > 57.toChar()) {
                    isValid = false
                }
            }
        }else{
            isValid = false
        }

        // if input valid calculate correct positions and numbers guessed
        if (isValid) {
            var correctNum = 0; var correctPos = 0

            for(i in 0..3) {
                for(j in 0..3) {
                    if(numberArray[i] == guessArray[j]){
                        correctNum++
                    }
                }
                if(numberArray[i] == guessArray[i]){
                    correctPos++
                }
            }
            println("$correctNum:$correctPos")
        }
    }

    print("You guessed correct! The number was ")
    for(i in numberArray){
        print(i)
    }
    println()
}