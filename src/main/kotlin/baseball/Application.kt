package baseball

import camp.nextstep.edu.missionutils.Console
import camp.nextstep.edu.missionutils.Randoms
import kotlin.collections.mutableListOf

data class GuessResult(
    val ballNumber: Int,
    val strikeNumber: Int
)

fun makeSecretNumber(): String {
    val computer: MutableList<Int> = mutableListOf()
    for (it in 0 until 3) {
        val randomNumber = Randoms.pickNumberInRange(1, 9)
        if (!computer.contains(randomNumber)) {
            computer.add(randomNumber)
        }
    }
    return computer.joinToString("")
}

fun isValidGuessNumber(input: String): Boolean {
    return input.length == 3 && input.toSet().size == 3 && input.all { isNumber(it) }
}

fun isValidContinueNumber(input: String): Boolean {
    return input.length == 1 && input[0] in '1'..'2'
}

fun isNumber(input: Char) = input in '0'..'9'

fun getGuessResult(secretNumber: String, guessNumber: String): GuessResult {
    val ball = countBall(secretNumber, guessNumber)
    val strike = countStrike(secretNumber, guessNumber)
    return GuessResult(ball, strike)
}

fun countBall(secretNumber: String, guessNumber: String): Int {
    var ball = 0
    for (secretIt in 0 until 3) {
        for (guessIt in 0 until 3) {
            if (secretIt == guessIt) {
                continue
            }
            if (secretNumber[secretIt] == guessNumber[guessIt]) {
                ball++
            }
        }
    }
    return ball
}

fun countStrike(secretNumber: String, guessNumber: String): Int {
    var strike = 0
    for (it in 0 until 3) {
        if (secretNumber[it] == guessNumber[it]) {
            strike++
        }
    }
    return strike
}

fun printGuessResult(result: GuessResult) {
    if(result.ballNumber == 0 && result.strikeNumber != 0) {
        println("${result.strikeNumber}스트라이크")
    }else if(result.ballNumber != 0 && result.strikeNumber == 0) {
        println("${result.ballNumber}볼")
    }else if(result.ballNumber == 0) {
        println("낫싱")
    }else {
        println("${result.ballNumber}볼 ${result.strikeNumber}스트라이크")
    }
}


fun main() {
    println("숫자 야구 게임을 시작합니다.")
    while (true) {
        val secretNumber = makeSecretNumber()
        while(true) {
            print("숫자를 입력해주세요 : ")
            val guessNumber = Console.readLine()
            if (!isValidGuessNumber(guessNumber)) {
                throw IllegalArgumentException("Wrong guessNumber $guessNumber")
            }
            val guessResult = getGuessResult(secretNumber, guessNumber)
            printGuessResult(guessResult)
            if (guessResult.strikeNumber == 3) {
                println("3개의 숫자를 모두 맞히셨습니다! 게임 종료")
                break
            }
        }
        println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.")
        val continueNumber = Console.readLine()
        if (!isValidContinueNumber(continueNumber)) {
            throw IllegalArgumentException("Wrong continueNumber $continueNumber")
        }
        if (continueNumber[0] == '2') {
            break
        }
    }
}
