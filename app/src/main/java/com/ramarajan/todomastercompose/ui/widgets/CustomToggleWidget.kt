package com.ramarajan.todomastercompose.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomToggleWidget(currentStatus : Boolean = true,onToggle: (Boolean) -> Unit) {
    var checked by remember { mutableStateOf(currentStatus) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            modifier = Modifier.padding(2.dp),
            checked = checked,
            onCheckedChange = {
                checked = it
                onToggle.invoke(it)
            },
            thumbContent = if (checked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
        if(checked){
            Text("Completed")
        }else{
            Text("Not Completed")
        }

    }

}