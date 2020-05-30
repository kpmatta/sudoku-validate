package org.krishnamatta;

import org.krishnamatta.util.Utils;
import org.krishnamatta.util.SudokuConstants;

public class SudokuValidate {

    /**
     *
     * @param args - input argument (csv file path)
     */
    public static void main(String[] args) {
        try {
            if(args.length != 1) {
                throw new IllegalArgumentException(
                        "Expecting a single input argument of file path");
            }
            // parse the file into 9X9 array
            char[][] data = new CsvParser().parse(args[0]);

            // validate numbers in the array, capture the validation status.
            Status sf = new SudokuValidate().validate(data);
            sf.printStatus();

            // exit with an error code
            System.exit(sf.getCode());
        }
        catch (Exception ex) {
            // if there is an exception, exit with -1
            System.out.println(String.format("INVALID : %s, StatusCode:%d", ex.toString(), -1));
            System.exit(-1);
        }
    }

    /**
     *
     * @param data - validate the input data array
     */
    public Status validateInputData(char[][] data) {
        if(data == null)
            return Status.INVALID_INPUT_ARGUMENT;

        // check number of rows
        if(data.length != SudokuConstants.GRID_SIZE) {
            return Status.INVALID_ROW_COUNT;
        }

        // check number of columns
        if(data[0].length != SudokuConstants.GRID_SIZE) {
            return Status.INVALID_COLUMN_COUNT;
        }

        return Status.SUCCESS;
    }

    /**
     *
     * @param cNumber - input number character
     * @param dupArr  - input array that tracks the input number (size of GRID_SIZE)
     * @return        - status
     */
    public Status validateNumber(char cNumber,
                                 boolean[] dupArr,
                                 int locNumber,
                                 String locName) {
        // validate input array
        if(dupArr == null || dupArr.length < SudokuConstants.GRID_SIZE) {
            return Status.INVALID_INPUT_ARGUMENT;
        }

        // skip blank numbers
        if(cNumber != SudokuConstants.BLANK_CHAR) {
            // number should be in range
            if( !Utils.isCharInRange(cNumber, SudokuConstants.MIN_NUMBER, SudokuConstants.MAX_NUMBER)) {
                Status s = Status.INVALID_NUMBER;
                s.setStatus(String.format("Invalid number [%c] in %s %d",
                        cNumber, locName, locNumber));
                return s;
            }

            // if this number already exists
            if(dupArr[cNumber - '1']) {
                Status s = Status.DUPLICATE_NUMBER;
                s.setStatus(String.format("Duplicate number %c in %s %d",
                        cNumber, locName, locNumber));
                return s;
            }

            // set the state in the index array
            dupArr[cNumber - '1'] = true;
        }

        return Status.SUCCESS;
    }

    /**
     *
     * @param data - input data grid  (GRID_SIZE X GRID_SIZE)
     */
    public Status validate(char[][] data) {
        // this condition already checked while parsing the data in csv,
        // in case if this function is directly called with data, validate again.
        Status s = validateInputData(data);
        if(s.isFailed())
            return s;

        // for each row
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {

            // prepare tracking arrays for row, col and block
            boolean[] dupRow = new boolean[SudokuConstants.GRID_SIZE];
            boolean[] dupCol = new boolean[SudokuConstants.GRID_SIZE];
            boolean[] dupBlock = new boolean[SudokuConstants.GRID_SIZE];

            // for each column
            for(int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                // validate number row
                Status sr = validateNumber(data[row][col], dupRow, row+1, "row");
                if(sr.isFailed()) {
                    return sr;
                }

                // validate number column
                Status sc = validateNumber(data[col][row], dupCol, row+1, "column");
                if(sc.isFailed()) {
                    return sc;
                }
            }

            // validate sub matrix
            int rowStart = row/ SudokuConstants.SUB_GRID_SIZE * SudokuConstants.SUB_GRID_SIZE;
            int colStart = row% SudokuConstants.SUB_GRID_SIZE * SudokuConstants.SUB_GRID_SIZE;
            for (int i = rowStart; i<rowStart+ SudokuConstants.SUB_GRID_SIZE; i++) {
                for(int j = colStart; j<colStart+ SudokuConstants.SUB_GRID_SIZE; j++) {
                    Status sb = validateNumber(data[i][j], dupBlock, row+1, "block");
                    if(sb.isFailed()) {
                        return sb;
                    }
                }
            }
        }

        return Status.SUCCESS;
    }
}
