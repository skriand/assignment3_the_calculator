package mmcs.assignment3_calculator.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

class CalculatorViewModel : BaseObservable(), Calculator {
    override var display = ObservableField<String>("0")
    override var displayWithOperation = ObservableField<String>()

    private var value: String = "0"
    private var previousValue: String = ""
    private var operation: String = ""

    override fun addDigit(dig: Int) {
        if(value == "0" || value == "NaN" || value == "Infinity" || value == previousValue){
            value = dig.toString()
        } else {
            value += dig.toString()
        }
        display.set(value)
    }

    override fun addPoint() {
        if(!value.contains("."))
            value += "."
        display.set(value)
    }

    override fun addOperation(op: Operation) {
        if (operation != ""){
            compute()
        }
        previousValue = value
        operation = when(op) {
            Operation.ADD -> "+"
            Operation.MUL -> "×"
            Operation.SUB -> "-"
            Operation.DIV -> "÷"
        }
        displayWithOperation.set("$previousValue $operation")
    }

    override fun compute() {
        if (previousValue != "") {
            val digValue = value.toDouble()
            val digPreviousValue = previousValue.toDouble()
            if (operation != "") displayWithOperation.set("$previousValue $operation $value =")
            value = when (operation) {
                "+" -> (digPreviousValue + digValue).toString()
                "-" -> (digPreviousValue - digValue).toString()
                "×" -> (digPreviousValue * digValue).toString()
                "÷" -> (digPreviousValue / digValue).toString()
                else -> value
            }
            previousValue = ""
            operation = ""
            display.set(value)
        }
    }

    override fun clear() {
        value = if (value.length == 1 || value == "NaN" || value == "Infinity") {
            "0"
        } else {
            value.dropLast(1)
        }
        display.set(value)
    }

    override fun reset(): Boolean {
        value = "0"
        previousValue = ""
        operation = ""
        display.set(value)
        displayWithOperation.set(previousValue)
        return false
    }
}