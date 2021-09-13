package com.example.minesweeper.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun GameOverDialog(
    title: String,
    body: String,
    onClickContinue: () -> Unit,
    onClickCancel: () -> Unit,
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            buttons = {
                Row {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            onClickContinue()
                        },
                    ) {
                        Text(text = "コンティニュー")
                    }
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            onClickCancel()
                        },
                    ) {
                        Text(text = "キャンセル")
                    }
                }
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = body)
            }
        )
    }
}

@Composable
fun GameClearDialog(
    title: String,
    body: String,
    onClickOneMore: () -> Unit,
    onClickCancel: () -> Unit,
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            buttons = {
                Row {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            onClickOneMore()
                        },
                    ) {
                        Text(text = "もう一度やる")
                    }
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            onClickCancel()
                        },
                    ) {
                        Text(text = "キャンセル")
                    }
                }
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = body)
            }
        )
    }
}