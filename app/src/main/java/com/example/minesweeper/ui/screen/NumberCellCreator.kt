package com.example.minesweeper.ui.screen

object NumberCellCreator {
    fun createNumberCell(
        row: Int,
        column: Int,
        rowIndex: Int,
        columnIndex: Int,
        bombCells: List<BombCell>,
    ): NumberCell {
        val startIndex = 0
        val endIndex = column - 1

        // 左上の隅のマスの場合
        if (rowIndex == startIndex && columnIndex == startIndex) {
            val aroundCellBombCount = bombCells.filter {
                val rightCell = it.x == rowIndex + 1 && it.y == columnIndex
                val rightBottomCell = it.x == rowIndex + 1 && it.y == columnIndex + 1
                val bottomCell = it.x == rowIndex && it.y == columnIndex + 1

                rightCell || bottomCell || rightBottomCell
            }
            return NumberCell(
                x = rowIndex,
                y = columnIndex,
                bombAroundCount = aroundCellBombCount.size
            )
        }
        // 左下の隅のマスの場合
        if (rowIndex == startIndex && columnIndex == endIndex) {
            val aroundCellBombCount = bombCells.filter {
                val rightCell = it.x == rowIndex + 1 && it.y == columnIndex
                val rightTopCell = it.x == rowIndex + 1 && it.y == columnIndex - 1
                val topCell = it.x == rowIndex && it.y == columnIndex - 1

                rightCell || topCell || rightTopCell
            }
            return NumberCell(
                x = rowIndex,
                y = columnIndex,
                bombAroundCount = aroundCellBombCount.size
            )
        }
        // 右上の隅のマスの場合
        if (rowIndex == endIndex && columnIndex == startIndex) {
            val aroundCellBombCount = bombCells.filter {
                val leftCell = it.x == rowIndex - 1 && it.y == columnIndex
                val leftBottomCell = it.x == rowIndex - 1 && it.y == columnIndex + 1
                val bottomCell = it.x == rowIndex  && it.y == columnIndex + 1

                leftCell || bottomCell || leftBottomCell
            }
            return NumberCell(
                x = rowIndex,
                y = columnIndex,
                bombAroundCount = aroundCellBombCount.size
            )
        }
        // 右下の隅のマスの場合
        if (rowIndex == endIndex && columnIndex == endIndex) {
            val aroundCellBombCount = bombCells.filter {
                val leftCell = it.x == rowIndex - 1 && it.y == columnIndex
                val leftTopCell = it.x == rowIndex - 1 && it.y == columnIndex - 1
                val topCell = it.x == rowIndex && it.y == columnIndex - 1

                leftCell || topCell || leftTopCell
            }
            return NumberCell(
                x = rowIndex,
                y = columnIndex,
                bombAroundCount = aroundCellBombCount.size
            )
        }

        // 上の一辺のマス
        if (columnIndex == startIndex) {
            val aroundCellBombCount = bombCells.filter {
                val leftCell = it.x == rowIndex - 1 && it.y == columnIndex
                val leftBottomCell = it.x == rowIndex - 1 && it.y == columnIndex + 1
                val bottomCell = it.x == rowIndex && it.y == columnIndex + 1
                val rightBottomCell = it.x == rowIndex + 1 && it.y == columnIndex + 1
                val rightCell = it.x == rowIndex + 1 && it.y == columnIndex

                leftCell || leftBottomCell || bottomCell || rightBottomCell || rightCell
            }
            return NumberCell(
                x = rowIndex,
                y = columnIndex,
                bombAroundCount = aroundCellBombCount.size
            )
        }
        // 下の一辺のマス
        if (columnIndex == endIndex) {
            val aroundCellBombCount = bombCells.filter {
                val leftCell = it.x == rowIndex - 1 && it.y == columnIndex
                val leftTopCell = it.x == rowIndex - 1 && it.y == columnIndex - 1
                val topCell = it.x == rowIndex && it.y == columnIndex - 1
                val rightTopCell = it.x == rowIndex + 1 && it.y == columnIndex - 1
                val rightCell = it.x == rowIndex + 1 && it.y == columnIndex

                leftCell || leftTopCell || topCell || rightTopCell || rightCell
            }
            return NumberCell(
                x = rowIndex,
                y = columnIndex,
                bombAroundCount = aroundCellBombCount.size
            )
        }
        //左の一辺のマス
        if (rowIndex == startIndex) {
            val aroundCellBombCount = bombCells.filter {
                val topCell = it.x == rowIndex && it.y == columnIndex - 1
                val rightTopCell = it.x == rowIndex + 1 && it.y == columnIndex - 1
                val rightCell = it.x == rowIndex + 1 && it.y == columnIndex
                val rightBottomCell = it.x == rowIndex + 1 && it.y == columnIndex + 1
                val bottomCell = it.x == rowIndex && it.y == columnIndex + 1

                topCell || rightTopCell || rightCell || rightBottomCell || bottomCell
            }
            return NumberCell(
                x = rowIndex,
                y = columnIndex,
                bombAroundCount = aroundCellBombCount.size
            )
        }
        //右の一辺のマス
        if (rowIndex == endIndex) {
            val aroundCellBombCount = bombCells.filter {
                val topCell = it.x == rowIndex && it.y == columnIndex - 1
                val leftTopCell = it.x == rowIndex - 1 && it.y == columnIndex - 1
                val leftCell = it.x == rowIndex - 1 && it.y == columnIndex
                val leftBottomCell = it.x == rowIndex - 1 && it.y == columnIndex + 1
                val bottomCell = it.x == rowIndex && it.y == columnIndex + 1

                topCell || leftTopCell || leftCell || leftBottomCell || bottomCell
            }
            return NumberCell(
                x = rowIndex,
                y = columnIndex,
                bombAroundCount = aroundCellBombCount.size
            )
        }

        // それ以外のマス
        return createNormalSquares(bombCells, rowIndex, columnIndex)
    }

    private fun createNormalSquares(
        bombCells: List<BombCell>,
        rowIndex: Int,
        columnIndex: Int
    ): NumberCell {
        val aroundCellBombCount = bombCells.filter {
            val topCell = it.x == rowIndex && it.y == columnIndex - 1
            val rightTopCell = it.x == rowIndex + 1 && it.y == columnIndex - 1
            val rightCell = it.x == rowIndex + 1 && it.y == columnIndex
            val rightBottomCell = it.x == rowIndex + 1 && it.y == columnIndex + 1
            val bottomCell = it.x == rowIndex && it.y == columnIndex + 1
            val leftBottomCell = it.x == rowIndex - 1 && it.y == columnIndex + 1
            val leftCell = it.x == rowIndex - 1 && it.y == columnIndex
            val leftTopCell = it.x == rowIndex - 1 && it.y == columnIndex - 1

            topCell || rightTopCell ||
                    rightCell || rightBottomCell ||
                    bottomCell || leftBottomCell ||
                    leftCell || leftTopCell
        }
        return NumberCell(
            x = rowIndex,
            y = columnIndex,
            bombAroundCount = aroundCellBombCount.size
        )
    }
}