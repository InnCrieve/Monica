package cn.netdiscovery.monica.ui.controlpanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.netdiscovery.monica.state.ApplicationState
import cn.netdiscovery.monica.utils.click
import org.koin.compose.koinInject

/**
 *
 * @FileName:
 *          cn.netdiscovery.monica.ui.controlpanel.CropView
 * @author: Tony Shen
 * @date: 2024/5/7 13:56
 * @version: V1.0 <描述当前版本功能>
 */
var clickStatus = mutableStateOf(0)

@Composable
fun cropView(state: ApplicationState) {
    val viewModel:CropViewModel = koinInject()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(state.isCrop, onCheckedChange = {
            state.isCrop = it

            if (!state.isCrop) {
                clearClickStatus()
            }
        })
        Text("裁剪", color = Color.Black, fontSize = 20.sp)
    }

    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.padding(5.dp),
            onClick = {
                clearClickStatus()
                viewModel.flip(state)
            },
            enabled = state.isCrop
        ) {
            Icon(
                painter = painterResource("images/flip.png"),
                contentDescription = "翻转",
                modifier = Modifier.size(36.dp)
            )
        }

        IconButton(
            modifier = Modifier.padding(5.dp),
            onClick = {
                clearClickStatus()
                viewModel.rotate(state)
            },
            enabled = state.isCrop
        ) {
            Icon(
                painter = painterResource("images/rotate.png"),
                contentDescription = "旋转",
                modifier = Modifier.size(36.dp)
            )
        }

        IconButton(
            modifier = Modifier.padding(5.dp),
            onClick = {
                clickStatus.value = 1
            },
            enabled = state.isCrop
        ) {
            Icon(
                painter = painterResource("images/resize.png"),
                contentDescription = "缩放",
                modifier = Modifier.size(36.dp)
            )
        }

    }

    Column {
        if (clickStatus.value == 1 && state.currentImage!=null) {
            generateResizeParams(state)
        }
    }

}

private fun clearClickStatus() {
    clickStatus.value = 0
}

@Composable
fun generateResizeParams(state: ApplicationState) {

    var widthText by remember {
        mutableStateOf("${state.currentImage?.width?:400}")
    }

    var heightText by remember {
        mutableStateOf("${state.currentImage?.height?:400}")
    }

    Row(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Text(text = "width")

        BasicTextField(
            value = widthText,
            onValueChange = { str ->
                widthText = str
            },
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            cursorBrush = SolidColor(Color.Gray),
            singleLine = true,
            modifier = Modifier.padding(start = 10.dp).width(120.dp).background(Color.LightGray.copy(alpha = 0.5f), shape = RoundedCornerShape(3.dp)).height(20.dp),
            textStyle = TextStyle(Color.Black, fontSize = 12.sp)
        )
    }

    Row(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Text(text = "height")

        BasicTextField(
            value = heightText,
            onValueChange = { str ->
                heightText = str
            },
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            cursorBrush = SolidColor(Color.Gray),
            singleLine = true,
            modifier = Modifier.padding(start = 10.dp).width(120.dp).background(Color.LightGray.copy(alpha = 0.5f), shape = RoundedCornerShape(3.dp)).height(20.dp),
            textStyle = TextStyle(Color.Black, fontSize = 12.sp)
        )

        Row {
            Button(
                modifier = Modifier.offset(x = 140.dp,y = 0.dp),
                onClick = {
                    click {
                    }
                },
                enabled = state.isCrop
            ) {
                Text(text = "确定",
                color = if (state.isCrop) Color.Unspecified else Color.LightGray)
            }
        }

    }

}
