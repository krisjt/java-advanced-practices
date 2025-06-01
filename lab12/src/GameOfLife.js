//GAME OF LIFE
//1. Any live cell with fewer than two live neighbours dies.
//2. Any live cell with more than three live neighbours dies.
//3. Any live cell with two or three live neighbours lives, unchanged, to the next generation.
//4. Any dead cell with exactly three live neighbours will come to life.
function generation(matrix) {
    var newMatrix = [];

    for (var i = 0; i < matrix.length; i++) {
        newMatrix.push([]);
        for (var j = 0; j < matrix[i].length; j++) {
            newMatrix[i].push(matrix[i][j]);
        }
    }

    var columnCount = matrix.length;
    var rowCount = matrix[0].length;
    for (var column = 0; column < columnCount; column++) {
        for (var row = 0; row < rowCount; row++) {
            // Column left of current cell
            // if column is at left edge, use modulus to wrap to right edge
            var left = (column - 1 + columnCount) % columnCount;

            // Column right of current cell
            // if column is at right edge, use modulus to wrap to left edge
            var right = (column + 1) % columnCount;

            // Row above current cell
            // if row is at top edge, use modulus to wrap to bottom edge
            var above = (row - 1 + rowCount) % columnCount;

            // Row below current cell
            // if row is at bottom edge, use modulus to wrap to top edge
            var below = (row + 1) % rowCount;

            var neighbours =
                matrix[left][above] +
                matrix[column][above] +
                matrix[right][above] +
                matrix[left][row] +
                matrix[right][row] +
                matrix[left][below] +
                matrix[column][below] +
                matrix[right][below];

            // Rules of Life
            // 1. Any live cell with fewer than two live neighbours dies
            // 2. Any live cell with more than three live neighbours dies
            if (neighbours < 2 || neighbours > 3) {
                newMatrix[column][row] = 0;
                // 4. Any dead cell with exactly three live neighbours will come to life.
            } else if (neighbours === 3) {
                newMatrix[column][row] = 1;
                // 3. Any live cell with two or three live neighbours lives, unchanged, to the next generation.
            } else newMatrix[column][row] = matrix[column][row];
        }
    }

    // Swap the current and next arrays for next generation
    return newMatrix;
}