package com.example.newsapplicationjetpackcompose.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onCloseIconClick: () -> Unit,
    onSearchIconClicked: () -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value, onValueChange = onValueChange,
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        leadingIcon = {

            Icon(
                imageVector = Icons.Default.Search, contentDescription = "Search",
                tint = Color.White
            )


        },
        placeholder = {
            Text(
                text = "Search...",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 16.sp
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                if (value.isNotEmpty()) onValueChange("")
                else onCloseIconClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Search",
                    tint = Color.White
                )

            }
        }, keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchIconClicked()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = Color.Gray,
            cursorColor = Color.Yellow,
            focusedIndicatorColor = Color.White,

            )
    )

}