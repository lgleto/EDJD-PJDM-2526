package ipca.example.calculator

class CalculatorBrain {

    enum class Operation(op:String) {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("x"),
        DIVISION("/"),
        EQUALS("=")
    }

    var operand : Double = 0.0
    var operator : Operation? = null
}