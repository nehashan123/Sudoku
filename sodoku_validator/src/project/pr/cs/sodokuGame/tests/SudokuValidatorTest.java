// DO NOT TOUCH THIS FILE!
package project.pr.cs.sodokuGame.tests;

// import the class to be tested
import project.pr.cs.sodokuGame.SudokuValidator;


// import junit 4 testing framework
import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

// import java.util.Arrays;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule; // system rules lib - useful for capturing system output
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class SudokuValidatorTest {
    @ClassRule
    public static final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule();

    public final String basePath = SudokuValidator.class.getProtectionDomain().getCodeSource().getLocation().getPath()
            + "/../";

    @Before
    public void init() {
        // any pre-test setup here
    }

    @Test
    public void testRemainingMoves() {
        String[] mockFileNames = { "example_boards/correctIncomplete.csv", "example_boards/correctComplete.csv" };
        int[][][] remainingMoves = {
                { { 0, 0, }, { 0, 1, }, { 0, 2, }, { 1, 3, }, { 1, 4, }, { 1, 5, }, { 1, 6, }, { 1, 8, }, { 2, 4, },
                        { 2, 5, }, { 2, 6, }, { 3, 1, }, { 3, 2, }, { 3, 4, }, { 3, 7, }, { 3, 8, }, { 4, 0, },
                        { 4, 1, }, { 4, 3, }, { 4, 4, }, { 4, 5, }, { 4, 6, }, { 5, 1, }, { 5, 4, }, { 5, 5, },
                        { 5, 7, }, { 5, 8, }, { 6, 2, }, { 6, 3, }, { 6, 4, }, { 6, 6, }, { 7, 0, }, { 7, 1, },
                        { 7, 2, }, { 7, 5, }, { 7, 7, }, { 8, 0, }, { 8, 2, }, { 8, 3, }, { 8, 5, }, { 8, 6, },
                        { 8, 7, }, { 8, 8, }, },
                {},
        };
        for (int i = 0; i < mockFileNames.length; i++) {
            int[][] board = SudokuValidator.getContentsOfFile(basePath + mockFileNames[i]);
            ArrayList<int[]> movesAL = SudokuValidator.remainingMoves(board);
            int[][] moves = new int[0][];
            moves = movesAL.toArray(moves);
            boolean movesCheck = Arrays.deepEquals(moves, remainingMoves[i]);
            assertTrue("Expected remainingMoves() to print remaning moves correctly but it didn't.", movesCheck);
        }
    }

    @Test
    public void testLoadBoard() {
        String[] mockFileNames = { "example_boards/correctIncomplete.csv", "example_boards/incorrectIncomplete.csv" };
        int[][][] puzzles = {
                { { 0, 0, 0, 5, 9, 3, 1, 7, 4, }, { 5, 9, 7, 0, 0, 0, 0, 3, 0, }, { 4, 3, 1, 7, 0, 0, 0, 5, 9, },
                        { 9, 0, 0, 4, 0, 2, 8, 0, 0, }, { 0, 0, 4, 0, 0, 0, 0, 6, 7, }, { 8, 0, 5, 1, 0, 0, 9, 0, 0, },
                        { 7, 2, 0, 0, 0, 4, 0, 9, 6, }, { 0, 0, 0, 9, 8, 0, 7, 0, 3, },
                        { 0, 5, 0, 0, 6, 0, 0, 0, 0, }, },
                { { 0, 0, 2, 6, 0, 1, 0, 0, 0, }, { 0, 0, 0, 0, 0, 2, 0, 0, 3, }, { 0, 0, 0, 0, 0, 0, 7, 0, 0, },
                        { 6, 0, 0, 0, 0, 0, 7, 0, 6, }, { 2, 3, 0, 9, 7, 5, 0, 0, 5, }, { 8, 0, 0, 0, 0, 0, 0, 0, 0, },
                        { 0, 0, 5, 0, 0, 0, 1, 7, 0, }, { 0, 0, 0, 5, 1, 0, 0, 9, 4, },
                        { 0, 0, 9, 0, 8, 4, 0, 0, 1, }, },
        };
        for (int i = 0; i < mockFileNames.length; i++) {
            boolean boardCheck = Arrays.deepEquals(SudokuValidator.getContentsOfFile(basePath + mockFileNames[i]),
                    puzzles[i]);
            assertTrue("Expected getContentsOfFile() to load board correctly but it didn't.", boardCheck);
        }
    }

    @Test
    public void testWinCondition() {
        String[] mockFileNames = { "example_boards/correctComplete.csv", "example_boards/correctIncomplete.csv" };
        boolean[] outputResult = { true, false };
        for (int i = 0; i < mockFileNames.length; i++) {
            String mockFilename = basePath + mockFileNames[i];
            int[][] puzzle = SudokuValidator.getContentsOfFile(mockFilename);
            boolean expected = outputResult[i];
            boolean actual = SudokuValidator.wonPuzzle(puzzle);
            assertTrue("Expected wonPuzzle() to return " + expected + " but was " + actual + ".", expected == actual);
        }
    }

    @Test
    public void testGetFilePathFromUser() {
        String[] expecteds = { "boo", "baz", "bum" };
        for (String expected : expecteds) {
            systemInMock.provideLines(expected);
            String actual = SudokuValidator.getFilepathFromUser().trim();
            String expectedFormatted = Paths.get(expected).toString();
            System.out.println(actual);
            boolean filePathCheck = (actual.equals(expected) || actual.equals(expectedFormatted));
            assertTrue("Expected the getFilePathFromUser() function to return the file path we entered. Returned "
                    + actual + " instead.", filePathCheck);
        }
    }

    @Test
    public void testValidateRow() {
        String[] mockFileNames = { "example_boards/correctIncomplete.csv", "example_boards/incorrectIncomplete.csv" };
        int[] mockRows = { 3, 4 };
        boolean[] outputResult = { true, false };
        for (int i = 0; i < mockFileNames.length; i++) {
            String mockFilename = basePath + mockFileNames[i];
            int mockRow = mockRows[i];
            boolean expected = outputResult[i];
            int[][] mockBoard = SudokuValidator.getContentsOfFile(mockFilename);
            assertTrue("Expected the validateRow() to return " + expected + " but it didn't.",
                    expected == SudokuValidator.validateRow(mockBoard, mockRow));
        }
    }

    @Test
    public void testValidateColumn() {
        String[] mockFileNames = { "example_boards/correctIncomplete.csv", "example_boards/incorrectIncomplete.csv" };
        int[] mockColumns = { 4, 6 };
        boolean[] outputResult = { true, false };
        for (int i = 0; i < mockFileNames.length; i++) {
            String mockFilename = basePath + mockFileNames[i];
            int mockColumn = mockColumns[i];
            boolean expected = outputResult[i];
            int[][] mockBoard = SudokuValidator.getContentsOfFile(mockFilename);
            assertTrue("Expected the validateColumn() to return " + expected + " but it didn't.",
                    expected == SudokuValidator.validateColumn(mockBoard, mockColumn));
        }
    }

    @Test
    public void testValidateBlock() {
        String[] mockFileNames = { "example_boards/correctIncomplete.csv", "example_boards/incorrectIncomplete.csv" };
        int[] mockRows = { 1, 2 };
        int[] mockColumns = { 1, 2 };
        boolean[] outputResult = { true, false };
        for (int i = 0; i < mockFileNames.length; i++) {
            String mockFilename = basePath + mockFileNames[i];
            int mockRow = mockRows[i];
            int mockColumn = mockColumns[i];
            boolean expected = outputResult[i];
            int[][] mockBoard = SudokuValidator.getContentsOfFile(mockFilename);
            assertTrue("Expected the validateBlock() to return " + expected + " but it didn't.",
                    expected == SudokuValidator.validateBlock(mockBoard, mockRow, mockColumn));
        }
    }
}
