package com.example.newsapp.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.newsapp.R

@Composable
fun Heading(
    modifier: Modifier = Modifier,
    @StringRes stringRes: Int,
    onTextButtonClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = stringRes),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h4
        )
        if (onTextButtonClick != null) {
            TextButton(onClick = { onTextButtonClick() }) {
                Text(text = stringResource(id = R.string.btn_see_all))
            }
        }
    }
}