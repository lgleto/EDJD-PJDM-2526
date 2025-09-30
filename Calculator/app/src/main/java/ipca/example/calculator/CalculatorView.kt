package ipca.example.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ipca.example.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorView(
    modifier: Modifier = Modifier
){

    var textDisplay by remember { mutableStateOf("0") }
    var calculatorBrain by remember { mutableStateOf(CalculatorBrain()) }

    var userIsTypingNumbers by remember { mutableStateOf(true) }

    val onNumPressed : (String) -> Unit = { num->
        if (userIsTypingNumbers) {
            if (!(num == "." && textDisplay.contains("."))) {
                if (textDisplay == "0") {
                    if (num == ".") {
                        textDisplay = "0."
                    } else {
                        textDisplay = num
                    }
                } else {
                    textDisplay += num
                }
            }
        }else{
            textDisplay = num
        }

        userIsTypingNumbers = true
    }

    val onOperatorPressed : (String) -> Unit = { operator ->

        calculatorBrain.doOperation(textDisplay.toDouble())
        calculatorBrain.operator =  CalculatorBrain.Operation.parse(operator)

        if (calculatorBrain.acumulator % 1.0 == 0.0){
            textDisplay = calculatorBrain.acumulator.toInt().toString()
        }else {
            textDisplay = calculatorBrain.acumulator.toString()
        }

        userIsTypingNumbers = false
    }

    Column (
        modifier = modifier.fillMaxSize()
    ){
        Text(textDisplay,
            fontSize = TextUnit(80f, TextUnitType.Sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Right
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CalculatorButton("7", onClick = onNumPressed)
            CalculatorButton("8", onClick = onNumPressed)
            CalculatorButton("9", onClick = onNumPressed)
            CalculatorButton("+", isOperator = true, onClick = onOperatorPressed)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CalculatorButton("4", onClick = onNumPressed)
            CalculatorButton("5", onClick = onNumPressed)
            CalculatorButton("6", onClick = onNumPressed)
            CalculatorButton("-", isOperator = true, onClick = onOperatorPressed)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CalculatorButton("1", onClick = onNumPressed)
            CalculatorButton("2", onClick = onNumPressed)
            CalculatorButton("3", onClick = onNumPressed)
            CalculatorButton("×", isOperator = true, onClick = onOperatorPressed)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CalculatorButton("0", onClick = onNumPressed)
            CalculatorButton(".", onClick = onNumPressed)
            CalculatorButton("=", isOperator = true, onClick = onOperatorPressed)
            CalculatorButton("÷", isOperator = true, onClick = onOperatorPressed)
        }



    }

}

@Preview(showBackground = true)
@Composable
fun CalculatorViewPreview(){
    CalculatorTheme {
        CalculatorView()
    }
}