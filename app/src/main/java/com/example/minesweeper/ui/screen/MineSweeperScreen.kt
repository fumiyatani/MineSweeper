package com.example.minesweeper.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minesweeper.ui.theme.Tile


private const val MAX_ROW = 9
private const val MAX_COLUMN = 9
private const val MAX_BOMB = 10

@Composable
fun MineSweeperScreen(
    viewModel: MineSweeperViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar { Text("マインスイーパー") } }
    ) {
        MineSweeperContent(
            uiState = uiState,
            onClickCell = {
                if (
                    uiState.gameState == GameState.INITIAL ||
                    uiState.gameState == GameState.PLAYING
                ) {
                    viewModel.onOpenCell(it)
                }
            },
            onClickReset = { viewModel.onResetGame(MAX_ROW, MAX_COLUMN, MAX_BOMB) }
        )
    }

    // ゲームクリア時のダイアログ
    if (uiState.gameState == GameState.CLEAR) {
        GameClearDialog(
            title = "🎉🎉ゲームクリア🎉🎉",
            body = "もう一度やりますか？",
            onClickOneMore = { viewModel.onStartGame(MAX_ROW, MAX_COLUMN, MAX_BOMB) },
            onClickCancel = {}
        )
    }

    // ゲームオーバー時のダイアログ
    if (uiState.gameState == GameState.GAME_OVER) {
        GameOverDialog(
            title = "ゲームオーバー😢",
            body = "もう一度やりますか？",
            onClickContinue = { viewModel.onStartGame(MAX_ROW, MAX_COLUMN, MAX_BOMB) },
            onClickCancel = {}
        )
    }
}

@Composable
fun MineSweeperContent(
    uiState: MineSweeperUiState,
    onClickCell: (Cell) -> Unit,
    onClickReset: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ResetButton(
                onClick = onClickReset
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        MineSweeperGridList(
            allCells = uiState.allCells,
            openedCells = uiState.openedCells,
            addedFragCells = uiState.addedFragCells,
            onClickCell = onClickCell,
        )
    }
}

@Composable
fun ResetButton(
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Icon(imageVector = Icons.Filled.Android, contentDescription = "reset button")
    }
}

@Composable
fun MineSweeperGridList(
    allCells: List<List<Cell>>,
    openedCells: Set<Cell>,
    addedFragCells: Set<Cell>,
    onClickCell: (Cell) -> Unit,
) {
    val row = allCells.size
    val column = allCells.firstOrNull()?.size ?: 0
    if (row != 0 && column != 0) {
        val currentDisplayWidth = LocalConfiguration.current.screenWidthDp
        val verticalMaxPadding = 4.dp * (row + 1)
        val squareSideSize = (currentDisplayWidth - verticalMaxPadding.value.toInt()) / row

        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(4.dp))
            allCells.forEach { cellList ->
                Spacer(modifier = Modifier.size(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    cellList.forEach { cell ->
                        MineSweeperCell(
                            modifier = Modifier.weight(1f, fill = true),
                            cell = cell,
                            isOpen = openedCells.contains(cell),
                            isAddFrag = addedFragCells.contains(cell),
                            onClickCell = onClickCell,
                            squareSideSize = squareSideSize,
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
fun MineSweeperCell(
    modifier: Modifier = Modifier,
    cell: Cell,
    isOpen: Boolean,
    isAddFrag: Boolean,
    squareSideSize: Int = 0,
    onClickCell: (Cell) -> Unit,
) {
    // todo フラグ用のセルも作成する
    if (isOpen) {
        if (cell is NumberCell) {
            Box(
                modifier = modifier
                    .size(squareSideSize.dp)
                    .background(color = Color.Transparent)
                    .border(1.dp, Color.Black),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = modifier.align(Alignment.Center),
                    text = cell.bombAroundCountText,
                )
            }
        } else {
            Box(
                modifier = modifier
                    .size(squareSideSize.dp)
                    .background(color = Color.Transparent)
                    .border(1.dp, Color.Black),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = modifier.align(Alignment.Center),
                    imageVector = Icons.Filled.Dangerous,
                    contentDescription = null,
                )
            }
        }
    } else {
        Box(
            modifier = modifier
                .size(squareSideSize.dp)
                .background(color = Tile)
                .clickable { onClickCell(cell) },
        )
    }
}