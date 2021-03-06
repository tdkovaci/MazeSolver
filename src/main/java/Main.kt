import CellType.*
import MazeConfiguration.CELL_SIZE
import MazeConfiguration.NUM_COLS
import MazeConfiguration.NUM_ROWS
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel

object MazeConfiguration {
    //729
    //243
    //81
    //27
    const val NUM_ROWS = 100
    const val NUM_COLS = 100
    const val CELL_SIZE = 10

    const val SHOW_GENERATION = false
    const val GENERATION_SLOWDOWN = 5 //Larger means slower; milliseconds

    const val SHOW_SEARCH = true
    const val SEARCH_SLOWDOWN = 5 //Larger means slower; milliseconds
}

fun main(args: Array<String>) {

    val frame = JFrame("Maze Solver")
    frame.isUndecorated = true
    frame.isVisible = true
    frame.setSize(NUM_ROWS * CELL_SIZE, NUM_COLS * CELL_SIZE)

    val canvas = Canvas()
    canvas.setSize(NUM_ROWS * CELL_SIZE, NUM_COLS * CELL_SIZE)

    frame.contentPane.add(canvas)

    val maze = MazeGenerator.generateMaze(NUM_ROWS, NUM_COLS) { maze ->
        canvas.updateMaze(maze)
        canvas.repaint()
    }

    Thread.sleep(2000)

    val searcher = AStarSearcher(maze)
    searcher.solve { maze ->
        canvas.updateMaze(maze)
        canvas.repaint()
    }
}

class Canvas(private var maze: Maze = Maze.generateMazeFromString("")) : JPanel() {

    fun updateMaze(newMaze: Maze) {
        maze = newMaze
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D

        g2.color = Color.BLACK
        for (y in 0 until maze.getColSize()) {
            for (x in 0 until maze.getRowSize()) {
                val cell = maze.get(x, y)
                when (cell.type) {
                    Free -> g2.color = Color.WHITE
                    Wall -> g2.color = Color.BLACK
                    Start -> g2.color = Color.GREEN
                    End -> g2.color = Color.RED
                    Frontier -> g2.color = Color.MAGENTA
                    Visited -> g2.color = Color.BLUE
                    Solution -> g2.color = Color.PINK
                    else -> g2.color = Color.BLACK
                }
                g2.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
                if (cell.type != Wall) {
                    g2.font = (Font("TimesRoman", Font.PLAIN, 5))
                    g2.color = Color.RED
                    //g2.drawString("${cell.weight}", (x * CELL_SIZE) + CELL_SIZE/4, ((y + 1) * CELL_SIZE) - CELL_SIZE/4)
                }
            }
        }
    }
}