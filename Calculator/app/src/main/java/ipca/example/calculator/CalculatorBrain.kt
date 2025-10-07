package ipca.example.calculator

class CalculatorBrain {

    enum class Operation(op: String) {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("×"),
        DIVISION("÷"),
        EQUALS("="),
        CLEAR("AC"),
        CLEAR_ENTRY("C"),
        SQRT("√"),
        PERCENTAGE("%");

        companion object {

            fun parse(string: String): Operation {
                return when (string) {
                    "+" -> ADDITION
                    "-" -> SUBTRACTION
                    "×" -> MULTIPLICATION
                    "÷" -> DIVISION
                    "=" -> EQUALS
                    "AC" -> CLEAR
                    "C" -> CLEAR_ENTRY
                    "√" -> SQRT
                    "%" -> PERCENTAGE
                    else -> throw IllegalArgumentException("Invalid operation")
                }
            }
        }
    }

    var acumulator : Double = 0.0
    var operator : Operation? = null

    fun doOperation(operand : Double){
        when(operator){
            Operation.ADDITION -> acumulator += operand
            Operation.SUBTRACTION -> acumulator -= operand
            Operation.MULTIPLICATION -> acumulator *= operand
            Operation.DIVISION -> acumulator /= operand
            Operation.EQUALS -> return
            Operation.CLEAR ->  {
                acumulator = 0.0
                operator = null
            }
            Operation.CLEAR_ENTRY -> acumulator = 0.0
            Operation.SQRT -> acumulator = kotlin.math.sqrt(operand)
            Operation.PERCENTAGE -> acumulator = operand / 100
            else -> acumulator = operand
        }
    }

}