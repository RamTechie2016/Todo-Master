package com.ramarajan.todomastercompose.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.ramarajan.todomastercompose.R

@Composable
fun ShowLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val contentDesc = stringResource(R.string.app_name)
        CircularProgressIndicator(
            modifier = modifier
                .align(Alignment.Center)
                .semantics {
                    contentDescription = contentDesc
                }
        )
    }
}