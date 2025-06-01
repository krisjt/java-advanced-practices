//BRIANS BRAIN
//1. Cell may be in three states: on, dying, off -- 0,1,2
//2. Cell turns on if it has exactly 2 neughbours that are on and it was off
//3. All 'on' cells go into dying state
//4. All 'dying' cells go to the off state.
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

            var left = (column - 1 + columnCount) % columnCount;
            var right = (column + 1) % columnCount;
            var above = (row - 1 + rowCount) % columnCount;
            var below = (row + 1) % rowCount;
            var neighbours = 0;

            if(matrix[left][above]==0)
                neighbours+=1;
            if(matrix[column][above]==0)
                neighbours+=1;
            if(matrix[right][above]==0)
                neighbours+=1;
            if(matrix[left][row]==0)
                neighbours+=1;
            if(matrix[right][row]==0)
                neighbours+=1;
            if(matrix[left][below]==0)
                neighbours+=1;
            if(matrix[column][below]==0)
                neighbours+=1;
            if(matrix[right][below]==0)
                neighbours+=1;


            if (neighbours == 2 && matrix[column][row] == 2) {
                newMatrix[column][row] = 0;
            } else if (matrix[column][row]==0) {
                newMatrix[column][row] = 1;
            } else if (matrix[column][row]==1)
                newMatrix[column][row] = 2;
            else newMatrix[column][row] = matrix[column][row];
        }
    }

    return newMatrix;
}