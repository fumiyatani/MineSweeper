package com.example.minesweeper.ui.screen

import androidx.lifecycle.ViewModel
import com.example.minesweeper.extension.containCells
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class MineSweeperViewModel : ViewModel() {

    private var maxRowSize = 0
    private var maxColumnSize = 0
    private var maxBombSize = 0

    private val _uiState = MutableStateFlow(MineSweeperUiState())
    val uiState
        get() = _uiState.asStateFlow()

    init {
        onStartGame(
            DEFAULT_ROW_COUNT,
            DEFAULT_COLUMN_COUNT,
            DEFAULT_BOMB_COUNT
        )
    }

    fun onStartGame(maxRowSize: Int, maxColumnSize: Int, maxBombSize: Int) {
        this.maxRowSize = maxRowSize
        this.maxColumnSize = maxColumnSize
        this.maxBombSize = maxBombSize

        _uiState.update { uiState ->
            val allCells = makeCells(maxRowSize, maxColumnSize)
            uiState.copy(
                allCells = allCells,
                openedCells = setOf(),
                addedFragCells = setOf(),
                mode = Operation.OPEN,
                gameState = GameState.INITIAL
            )
        }
    }

    fun onResetGame(maxRowSize: Int, maxColumnSize: Int, maxBombSize: Int) {
        this.maxRowSize = maxRowSize
        this.maxColumnSize = maxColumnSize
        this.maxBombSize = maxBombSize

        _uiState.update { uiState ->
            val allCells = makeCells(maxRowSize, maxColumnSize)
            uiState.copy(
                allCells = allCells,
                openedCells = setOf(),
                addedFragCells = setOf(),
                mode = Operation.OPEN,
                gameState = GameState.INITIAL
            )
        }
    }

    // TODO: move External Class
    private fun makeCells(
        row: Int,
        column: Int,
        bombCells: List<BombCell> = makeBombCells(row, column)
    ): MutableList<MutableList<Cell>> {
        return MutableList(row) { columnIndex ->
            MutableList(column) { rowIndex ->
                bombCells
                    .firstOrNull {
                        it.x == rowIndex && it.y == columnIndex
                    }
                    ?: NumberCellCreator.createNumberCell(
                        row = row,
                        column = column,
                        rowIndex = rowIndex,
                        columnIndex = columnIndex,
                        bombCells = bombCells,
                    )
            }
        }
    }

    // TODO: move External Class
    private fun makeBombCells(row: Int, column: Int): List<BombCell> {
        val bombs = mutableListOf<BombCell>()

        for (i in 0 until maxBombSize) {
            var bombPosition = BombCell(
                x = Random.nextInt(row),
                y = Random.nextInt(column)
            )

            // 爆弾セルの重複確認
            while (bombs.containCells(bombPosition)) {
                bombPosition = BombCell(
                    x = Random.nextInt(row),
                    y = Random.nextInt(column)
                )
            }

            bombs.add(bombPosition)
        }
        return bombs
    }

    fun onAddFrag(cell: Cell) {
        _uiState.update { uiState ->
            val set = uiState.addedFragCells.toMutableSet()
            set.add(cell)
            uiState.copy(addedFragCells = set.toSet())
        }
    }

    fun onOpenCell(cell: Cell) {
        _uiState.update { uiState ->
            val set = uiState.openedCells.toMutableSet()
            set.add(cell)
            uiState.copy(openedCells = set.toSet())
        }
        checkGameState(cell)
    }

    private fun checkGameState(cell: Cell) {
        if (cell is BombCell) {
            _uiState.update { uiState ->
                uiState.copy(gameState = GameState.GAME_OVER)
            }
        } else {
            _uiState.update { uiState ->
                val maxCellCount = maxRowSize * maxColumnSize
                val clearCount = maxCellCount - maxBombSize
                val openedCellCount = uiState.openedCells
                    .filterIsInstance<NumberCell>()
                    .size

                if (clearCount == openedCellCount) {
                    uiState.copy(gameState = GameState.CLEAR)
                } else {
                    uiState.copy(gameState = GameState.PLAYING)
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 9
        private const val DEFAULT_ROW_COUNT = 9
        private const val DEFAULT_BOMB_COUNT = 9
    }
}