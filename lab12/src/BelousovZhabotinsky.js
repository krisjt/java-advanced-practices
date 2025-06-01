//BELOUSOV ZHABOTINSKY
//1. A cell exists in n states
//2. If a cell is ill (at n) it becomes healthy
//3. If a cell is between 0 and n (infected) it becomes the avg of the states of the neighbours plus g
//4. If a cell is healthy it becomes the avg of the num of infected and ill neighbours
function generation(matrix, n, g) {
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

            if (matrix[column][row] == n) {
                newMatrix[column][row] = 0;
            } else if (matrix[column][row]==0) {
                var avg = 0;
                if(matrix[left][above]<=n&&matrix[left][above]>0)
                    avg+=1;
                if(matrix[column][above]<=n&&matrix[column][above]>0)
                    avg+=1;
                if(matrix[right][above]<=n&&matrix[right][above]>0)
                    avg+=1;
                if(matrix[left][row]<=n&&matrix[left][row]>0)
                    avg+=1;
                if(matrix[right][row]<=n&&matrix[right][row]>0)
                    avg+=1;
                if(matrix[left][below]<=n&&matrix[left][below]>0)
                    avg+=1;
                if(matrix[column][below]<=n&&matrix[column][below]>0)
                    avg+=1;
                if(matrix[right][below]<=n&&matrix[right][below]>0)
                    avg+=1;
                if (avg > 0) {
                    newMatrix[column][row] = 1;
                } else {
                    newMatrix[column][row] = 0;
                }
            } else if (matrix[column][row]<n&&matrix[column][row]>0) {
                var avg = 0;
                avg += matrix[left][above] +
                    matrix[column][above] +
                    matrix[right][above] +
                    matrix[left][row] +
                    matrix[right][row] +
                    matrix[left][below] +
                    matrix[column][below] +
                    matrix[right][below];
                avg=avg/8;
                newMatrix[column][row] = (avg+g)%n;
            }
            else newMatrix[column][row] = matrix[column][row];
        }
    }

    return newMatrix;
}