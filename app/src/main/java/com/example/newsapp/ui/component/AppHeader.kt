package com.example.newsapp.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AppHeader(
    modifier: Modifier = Modifier,
    @StringRes title: Int? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign = TextAlign.Center,
    navigationStartIcon: @Composable (() -> Unit)? = null,
    navigationEndIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon on position start (left)
        navigationStartIcon?.invoke()

        // Title content center
        if (title != null) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = title),
                style = textStyle,
                textAlign = textAlign,
                maxLines = 1
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        // Icon on position end (right)
        navigationEndIcon?.invoke()
    }
}