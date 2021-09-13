package com.example.minesweeper.extension

import com.example.minesweeper.ui.screen.Cell

fun <T: Cell> Collection<T>.containCells(cell: T): Boolean {
    return any {
        it.x == cell.x && it.y == cell.y
    }
}