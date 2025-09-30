package ipca.example.calculator

class CalculatorBrain {

    enum class Operation(op: String) {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("×"),
        DIVISION("÷"),
        EQUALS("=");

        companion object {

            fun parse(string: String): Operation {
                return when (string) {
                    "+" -> ADDITION
                    "-" -> SUBTRACTION
                    "×" -> MULTIPLICATION
                    "÷" -> DIVISION
                    "=" -> EQUALS
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
            else -> acumulator = operand
        }
    }

}