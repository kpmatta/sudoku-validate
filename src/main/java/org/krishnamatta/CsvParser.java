package org.krishnamatta;

import org.krishnamatta.util.Utils;
import org.krishnamatta.util.SudokuConstants;

import java.io.*;
import java.util.Scanner;

public class CsvParser {

    /**
     *
     * @param strNumber   : input number to convert char
     * @return            : output character
     */
    public char getChar(String strNumber) {
        if(strNumber == null)
            throw new IllegalArgumentException("Invalid input argument");

        String trimmed = strNumber.trim();
        char out = SudokuConstants.BLANK_CHAR;

        // only only char should in string
        if(trimmed.length()==1) {
            out = trimmed.charAt(0);
            // check char in the expected range
            if (!Utils.isCharInRange(out, SudokuConstants.MIN_NUMBER, SudokuConstants.MAX_NUMBER)) {
                throw new IllegalArgumentException(
                        String.format("%s is not a valid number", trimmed));
            }
        }
        // else, exception
        else if(trimmed.length() > 1) {
            throw new IllegalArgumentException(
                    String.format("%s is not a valid number", trimmed));
        }
        return out;
    }

    /**
     *
     * @param line      : Input line
     * @param delimiter : delimiter to split
     * @return          : array of characters after validation
     */
    public char[] splitLineToArray(String line, String delimiter) {
        if(line == null)
            throw new IllegalArgumentException("Invalid input argument");

        String[] strArr = line.split(delimiter, -1);

        // throw exception, for wrong size array
        if(strArr.length != SudokuConstants.GRID_SIZE) {
            throw new IllegalArgumentException(
                    String.format("Invalid input line: row [%s] with length of [%d] ", line, strArr.length));
        }

        // convert each string into character
        char[] charArr = new char[SudokuConstants.GRID_SIZE];
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            charArr[i] = getChar(strArr[i]);
        }
        return charArr;
    }

    /**
     *
     * @param filePath      : input csv file path
     * @return              : parsed sudoku grid
     * @throws FileNotFoundException : throws an exception
     */
    public char[][] parse(String filePath) throws FileNotFoundException {
        return parse(filePath, "UTF-8", ",");
    }

    /**
     *
     * @param filePath      : input csv file path
     * @param charsetName   : character encoding
     * @return              : parsed Sudoku grid
     * @throws FileNotFoundException  : throws an exception
     */
    public char[][] parse(String filePath,
                          String charsetName,
                          String delimiter) throws FileNotFoundException {

        File file;
        Scanner scanner = null;
        char[][] data;
        try {
            file = new File(filePath);
            scanner = new Scanner(file, charsetName);
            data = new char[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
            boolean firstLine = true;
            int rows = 0;

            // for each line
            while (scanner.hasNextLine()) {
                // if grid size already reached
                if (SudokuConstants.GRID_SIZE == rows) {
                    throw new IllegalArgumentException(
                            String.format("Input data has more than %d lines", SudokuConstants.GRID_SIZE));
                }

                // get next line
                String line = scanner.nextLine();

                // for first line, check BOM char and remove it
                if (firstLine) {
                    line = checkAndRemoveUTF8BOM(line);
                    firstLine = false;
                }

                // get char array from line.
                data[rows++] = splitLineToArray(line, delimiter);
            }

            // if not enough rows
            if (SudokuConstants.GRID_SIZE != rows) {
                throw new IllegalArgumentException(
                        String.format("Input data has less than %d lines", SudokuConstants.GRID_SIZE));
            }
        }
        finally {
            if(scanner != null) {
                scanner.close();
            }
        }
        return data;
    }

    /**
     *
     * @param line  : Check and remove Byte order mark from line
     * @return      : string without BOM char.
     */
    private String checkAndRemoveUTF8BOM(String line) {
        final String BOM = "\uFEFF";
        if(line.startsWith(BOM)) {
            return line.substring(1);
        }
        return line;
    }
}
