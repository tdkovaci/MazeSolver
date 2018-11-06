class Solver {

    companion object {
        var listOfVisitedPositions = ArrayList<Position>()
        var listOfJunctions = ArrayList<Junction>()
        var endOfMaze = Cell(Position(0,0), CellType.Boundary)

        fun isAlreadyVisited(position: Position?): Boolean {
            return listOfVisitedPositions.contains(position)
        }

        fun isNotAlreadyVisited(position: Position?): Boolean {
            return !isAlreadyVisited(position)
        }

        fun junctionAlreadyExists(junction: Junction): Boolean {
            listOfJunctions.forEach { junctionInList ->
                if (junction.position == junctionInList.position) {
                    return true
                }
            }
            return false
        }

        fun junctionDoesNotExist(junction: Junction): Boolean {
            return !junctionAlreadyExists(junction)
        }
    }

}