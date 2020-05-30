package org.krishnamatta;

import org.junit.*;

import static org.krishnamatta.util.SudokuConstants.MAX_NUMBER;
import static org.junit.Assert.*;
import java.nio.file.Paths;

public class CsvParserTests {

    @Test(expected = IllegalArgumentException.class)
    public void testGetCharWithIllegalNull() {
        CsvParser cp = new CsvParser();
        cp.getChar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCharWithIllegalInputNumber() {
        CsvParser cp = new CsvParser();
        cp.getChar("12");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCharWithIllegalInputNumberString() {
        CsvParser cp = new CsvParser();
        cp.getChar("1a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCharWithIllegalInputString() {
        CsvParser cp = new CsvParser();
        cp.getChar("Ab");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCharWithIllegalInputZeroString() {
        CsvParser cp = new CsvParser();
        cp.getChar("0");
    }

    @Test
    public void testGetCharWithBlank() {
        CsvParser cp = new CsvParser();
        assertEquals('\0',  cp.getChar(""));
        assertEquals('\0',  cp.getChar(" "));
        assertEquals('\0',  cp.getChar("     "));
        assertEquals('\0',  cp.getChar("\t"));
    }

    @Test
    public void testGetCharWithRightInput() {
        CsvParser cp = new CsvParser();
        for (char c = '1'; c <= MAX_NUMBER; c ++ ) {
            assertEquals(c,  cp.getChar(Character.toString(c)));
            assertEquals(c,  cp.getChar(String.format(" %c", c)));
            assertEquals(c,  cp.getChar(String.format("%c ", c)));
            assertEquals(c,  cp.getChar(String.format(" %c ", c)));
        }
    }

    @Test
    public void testSplitLineToArray() {
        String inLineStr = "1,,3,4,5,6,7,8,9";
        char[] exp = new char[] {'1','\0','3','4','5','6','7','8','9'};
        char[] lineArr = new CsvParser().splitLineToArray(inLineStr, ",");
        assertArrayEquals(exp, lineArr);
    }

    @Test
    public void testParseFile() {
        String projectPath = System.getProperty("user.dir");
        try {
            char[][] expected = {
                    {'1', '2', '3', '4', '5', '6', '7', '8', '9'},
                    {'1', '2', '3', '4', '5', '6', '7', '8', '9'},
                    {'1', '2', '3', '4', '5', '6', '7', '8', '9'},
                    {'1', '2', '3', '4', '5', '6', '7', '8', '9'},
                    {'1', '2', '3', '4', '5', '6', '7', '8', '9'},
                    {'1', '2', '3', '4', '5', '6', '7', '8', '9'},
                    {'1', '2', '3', '4', '5', '6', '7', '8', '9'},
                    {'1', '2', '3', '4', '5', '6', '7', '8', '9'},
                    {'1', '2', '3', '4', '5', '6', '7', '8', '9'}
            };

            String filePath = Paths.get(projectPath, "/TestFiles/sudoku.csv").toString();
            char[][] data = new CsvParser().parse(filePath);

            for (int i = 0; i < 9; i++) {
                assertArrayEquals(expected[i], data[i]);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @Test
    public void testParseReadPartialData() {
        String projectPath = System.getProperty("user.dir");
        try {
            char[][] expected = {
                    {'9',  '\0', '4',  '\0', '6',  '\0', '7',  '\0', '1'},
                    {'\0', '2',  '\0', '4',  '\0', '3',  '\0', '8',  '\0'},
                    {'8',  '\0', '\0', '\0', '\0', '\0', '\0', '\0', '4'},
                    {'\0', '\0', '1',  '8',  '4',  '9',  '6',  '\0', '\0'},
                    {'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'},
                    {'\0', '\0', '3',  '2',  '5',  '7',  '9',  '\0', '\0'},
                    {'4',  '\0', '\0', '\0', '\0', '\0', '\0', '\0', '7'},
                    {'\0', '8',  '\0', '6',  '\0', '4',  '\0', '5',  '\0'},
                    {'5',  '\0', '6',  '\0', '8',  '\0', '2',  '3',  '\0'}
            };

            String filePath = Paths.get(projectPath, "/TestFiles/SampleValidFile.csv").toString();
            char[][] data = new CsvParser().parse(filePath);

            for (int i = 0; i < 9; i++) {
                assertArrayEquals(expected[i], data[i]);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}