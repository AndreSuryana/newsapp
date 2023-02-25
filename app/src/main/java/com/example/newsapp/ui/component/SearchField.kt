package com.example.newsapp.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.newsapp.R

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.body2
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = modifier,
        textStyle = textStyle,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_placeholder),
                style = textStyle
            )
        },
        maxLines = 1,
        shape = RoundedCornerShape(48.dp),
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = null)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        )
    )
}