package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                CalculaterScreen()

                }
            }
        }
    }


@Composable
fun CalculaterScreen(modifier: Modifier = Modifier)
{

    var expression by rememberSaveable {
    mutableStateOf("")
    }

    var result by rememberSaveable {
        mutableStateOf("")
    }

   Column(modifier = modifier
       .fillMaxSize()
       .background(MaterialTheme.colorScheme.background))
   {
       Column(modifier = modifier.weight(1f)) {

           Text(text = expression, style = TextStyle(fontSize = 34.sp, color = MaterialTheme.colorScheme.onPrimary), maxLines = 3, textAlign = TextAlign.End)
           Spacer(modifier = modifier.height(8.dp))
           Text(text = result, style = TextStyle(fontSize = 38.sp, color = MaterialTheme.colorScheme.onPrimary),textAlign = TextAlign.End)
       }
       Column() {
           Row(modifier.fillMaxWidth()) {
               CalculaterButton(isFunction = true, modifier = modifier.weight(2f), text = "AC", onClick = {
                   expression = ""
                   result = ""

               })
               CalculaterButton(isFunction = true, modifier = modifier.weight(1f), text = "⌫", onClick = {
                   expression = delchracter(expression)

               })
               CalculaterButton(isFunction = true, modifier = modifier.weight(1f), text = "÷", onClick = {
                   expression+=it

               })

           }
           Row(modifier.fillMaxWidth()) {
               CalculaterButton(isFunction = false, modifier = modifier.weight(1f), text = "7", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = false, modifier = modifier.weight(1f), text = "8", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = false, modifier = modifier.weight(1f), text = "9", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = true, modifier = modifier.weight(1f), text = "×", onClick = {
                   expression+=it

               })
           }
           Row(modifier.fillMaxWidth()) {
               CalculaterButton(isFunction = false, modifier = modifier.weight(1f), text = "4", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = false, modifier = modifier.weight(1f), text = "5", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = false, modifier = modifier.weight(1f), text = "6", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = true, modifier = modifier.weight(1f), text = "+", onClick = {
                   expression+=it

               })
           }
           Row(modifier.fillMaxWidth()) {
               CalculaterButton(isFunction = false, modifier = modifier.weight(1f), text = "1", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = false, modifier = modifier.weight(1f), text = "2", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = false, modifier = modifier.weight(1f), text = "3", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = true, modifier = modifier.weight(1f), text = "-", onClick = {
                   expression+=it

               })
           }
           Row(modifier.fillMaxWidth()) {
               CalculaterButton(isFunction = false, modifier = modifier.weight(2f), text = "0", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = false, modifier = modifier.weight(1f), text = ".", onClick = {
                   expression+=it

               })
               CalculaterButton(isFunction = true, modifier = modifier.weight(1f), text = "=", onClick = {
                   if(expression.isEmpty()) return@CalculaterButton
                   result = solveExpression(expression)

               })
           }
       }

   }
}

fun delchracter(expression: String): String {
    if(expression.isNotEmpty())
    {
        return expression.substring(0,expression.length-1)

    }
    else
    {
        return expression

    }

}
fun solveExpression(expression: String): String {
    var answer = ""

    try {
        val result = Expression(expression
            .replace("÷", "/")
            .replace("×", "*")
        ).calculate()

        // Check for division by zero
        if (result.isNaN() || result.isInfinite()) {
            return "Math Error"
        }

        answer = result.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        return "Invalid expression"
    }

    return answer.replace(".0", "")
}

@Composable
fun CalculaterButton(modifier: Modifier = Modifier, text:String = "",
                     isFunction:Boolean = false, onClick:(String)-> Unit = {} )
{
Button(modifier = modifier
    .clip(CircleShape)
    .size(72.dp)
    .padding(4.dp) ,onClick={onClick(text)}, colors = ButtonDefaults.buttonColors
    (containerColor = if(isFunction && text == "AC" || text == "=")
    {
        MaterialTheme.colorScheme.secondary

    }
    else if(isFunction)
    MaterialTheme.colorScheme.error
    else
    {
    MaterialTheme.colorScheme.tertiary
    })
     )
{
    Text(
        text = text, style = TextStyle(
            fontSize = 24.sp,
            color = if (isFunction && text != "=" && text != "AC") {
                MaterialTheme.colorScheme.background
            } else {
                MaterialTheme.colorScheme.onTertiary
            }
        )
    )
}
}



@Preview(showBackground = true)
@Composable
fun CalculaterScreenPreview()
{
    CalculaterScreen()
}

@Preview
@Composable

fun CalculaterButtonPreview()
{
    CalculaterButton()
}