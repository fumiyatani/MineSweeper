package com.example.minesweeper.ui.screen

data class MineSweeperUiState(
    val allCells: List<List<Cell>> = emptyList(),
    val openedCells: Set<Cell> = emptySet(),
    val addedFragCells: Set<Cell> = emptySet(),
    val mode: Operation = Operation.OPEN,
    val gameState: GameState = GameState.INITIAL,
)

sealed interface Cell {
    val x: Int
    val y: Int
}

data class BombCell(
    override val x: Int,
    override val y: Int,
) : Cell

data class NumberCell(
    override val x: Int,
    override val y: Int,
    val bombAroundCount: Int,
) : Cell {
    val bombAroundCountText: String
        get() = if (bombAroundCount == 0) "" else bombAroundCount.toString()
}

enum class Operation {
    OPEN,
    FRAG
}
enum class GameState {
    INITIAL,
    PLAYING,
    CLEAR,
    GAME_OVER;
}