package org.krishnamatta;

import org.junit.Test;

import java.nio.file.Paths;

import static org.krishnamatta.util.SudokuConstants.GRID_SIZE;
import static org.junit.Assert.*;

public class SudokuValidateTests extends SudokuValidate {

    private final char[][]  valid_board = {
            {'8', '1', '2', '7', '5', '3', '6', '4', '9'},
            {'9', '4', '3', '6', '8', '2', '1', '7', '5'},
            {'6', '7', '5', '4', '9', '1', '2', '8', '3'},
            {'1', '5', '4', '2', '3', '7', '8', '9', '6'},
            {'3', '6', '9', '8', '4', '5', '7', '2', '1'},
            {'2', '8', '7', '1', '6', '9', '5', '3', '4'},
            {'5', '2', '1', '9', '7', '4', '3', '6', '8'},
            {'4', '3', '8', '5', '2', '6', '9', '1', '7'},
            {'7', '9', '6', '3', '1', '8', '4', '5', '2'}
    };

    private final char[][] in_valid_board = {
            {'9', '1', '2', '7', '5', '3', '6', '4', '9'},
            {'9', '4', '3', '6', '8', '2', '1', '7', '5'},
            {'6', '7', '5', '4', '9', '1', '2', '8', '3'},
            {'1', '5', '4', '2', '3', '7', '8', '9', '6'},
            {'3', '6', '9', '8', '4', '5', '7', '2', '1'},
            {'2', '8', '7', '1', '6', '9', '5', '3', '4'},
            {'5', '2', '1', '9', '7', '4', '3', '6', '8'},
            {'4', '3', '8', '5', '2', '6', '9', '1', '7'},
            {'7', '9', '6', '3', '1', '8', '4', '5', '2'}
    };

    private final char[][] in_valid_board_block1 = {
            {'8', '1', '2', '7', '5', '3', '6', '4', '9'},
            {'9', '4', '3', '6', '8', '2', '1', '7', '5'},
            {'6', '7', '4', '4', '9', '1', '2', '8', '3'},
            {'1', '5', '4', '2', '3', '7', '8', '9', '6'},
            {'3', '6', '9', '8', '4', '5', '7', '2', '1'},
            {'2', '8', '7', '1', '6', '9', '5', '3', '4'},
            {'5', '2', '1', '9', '7', '4', '3', '6', '8'},
            {'4', '3', '8', '5', '2', '6', '9', '1', '7'},
            {'7', '9', '6', '3', '1', '8', '4', '5', '2'}
    };

    @Test
    public void testValidateWithValidData() {
        SudokuValidate sv = new SudokuValidate();
        Status s = sv.validate(valid_board);
        s.printStatus();
        assertTrue(s.isSuccess());
    }

    @Test
    public void testValidateWithInvalidData() {
        SudokuValidate sv = new SudokuValidate();
        Status s = sv.validate(in_valid_board);
        s.printStatus();
        assertFalse(s.isSuccess());
    }

    @Test
    public void testValidateWithInvalidBlock() {
        SudokuValidate sv = new SudokuValidate();
        Status s = sv.validate(in_valid_board_block1);
        s.printStatus();
        assertFalse(s.isSuccess());
    }

    @Test
    public void testValidateNumberStatus() {
        SudokuValidate sv = new SudokuValidate();
        assertEquals(Status.INVALID_INPUT_ARGUMENT , sv.validateNumber('1', null, 1, "row"));
        assertEquals(Status.SUCCESS, sv.validateNumber('1', new boolean[10], 1, "row"));
        assertEquals(Status.INVALID_INPUT_ARGUMENT, sv.validateNumber('1', new boolean[8], 1, "row"));
        boolean[] dupArr = new boolean[9];
        assertEquals(Status.INVALID_NUMBER, sv.validateNumber('-', dupArr, 2, "column"));
    }

    @Test
    public void testValidateInputDataWithNull() {
        SudokuValidate sv = new SudokuValidate();
        Status s = sv.validateInputData(null);
        assertEquals(Status.INVALID_INPUT_ARGUMENT, s);
        assertTrue(s.isFailed());
    }

    @Test
    public void testValidateInputDataWithWrongColumns() {
        SudokuValidate sv = new SudokuValidate();
        Status s = sv.validateInputData(new char[9][3]);
        assertEquals(Status.INVALID_COLUMN_COUNT, s);
        assertTrue(s.isFailed());
    }

    @Test
    public void testValidateInputDataWrongRows() {
        SudokuValidate sv = new SudokuValidate();
        Status s = sv.validateInputData(new char[1][9]);
        assertEquals(Status.INVALID_ROW_COUNT, s);
        assertTrue(s.isFailed());
    }

    @Test
    public void testValidateInputDataRightSize() {
        SudokuValidate sv = new SudokuValidate();
        Status s = sv.validateInputData(new char[GRID_SIZE][GRID_SIZE]);
        assertEquals(Status.SUCCESS, s);
        assertTrue(s.isSuccess());
    }

    @Test
    public void testValidateWithNull() {
        SudokuValidate sv = new SudokuValidate();
        Status s = sv.validate(null);
        assertEquals(Status.INVALID_INPUT_ARGUMENT, s);
        assertTrue(s.isFailed());
    }

    @Test
    public void testParseAndValidateWithErrorFile() {
        String projectPath = System.getProperty("user.dir");

        try {
            String filePath = Paths.get(projectPath, "/TestFiles/SampleErrorFile.csv").toString();
            char[][] invalidData = new CsvParser().parse(filePath, "utf-8", ",");
            SudokuValidate sv = new SudokuValidate();
            Status s = sv.validate(invalidData);
            s.printStatus();
            assertFalse(s.isSuccess());
        }
        catch( Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @Test
    public void testParseAndValidateWithValidFile() {
        String projectPath = System.getProperty("user.dir");

        try {
            String filePath = Paths.get(projectPath, "/TestFiles/SampleValidFile.csv").toString();
            char[][] invalidData = new CsvParser().parse(filePath, "utf-8", ",");
            SudokuValidate sv = new SudokuValidate();
            Status s = sv.validate(invalidData);
            s.printStatus();
            assertTrue(s.isSuccess());
        }
        catch( Exception ex) {
            System.out.println(ex.toString());
        }
    }
}