package no.bekk.bowling;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void total_whenNoScores_returns0() {
        var target = new Calculator();

        var result = target.total();

        assertEquals(0, result);
    }

    @Test
    void total_whenOneScoreIsAdded_returnsSameScore(){
        var score = 4;
        var target = new Calculator();

        target.add(score);
        int result = target.total();

        assertEquals(score, result);
    }

    @Test
    void add_withTooHighNumber_throws(){
        var tooHigh = 11;
        var target = new Calculator();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            target.add(tooHigh);
        });

        assertEquals("There aren't that many pins in bowling!", exception.getMessage());
    }

    @Test
    void add_withTooLowNumber_throws(){
        var tooLow = -1;
        var target = new Calculator();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            target.add(tooLow);
        });

        assertEquals("You cannot score less than zero.", exception.getMessage());
    }

    @TestFactory
    Stream<DynamicTest> total_afterMultipleAdds_returnsExpected(){
        return Stream.of(
            new testInput(0, new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, "Gutter game"),
            new testInput(20, new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, "One pin down in each roll"),
            new testInput(29, new int[]{9,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, "Spare in first roll, one pin down in each other roll, (score = 10 + 1 + 18 = 29)")
        ).map(input -> DynamicTest.dynamicTest(input.displayName, () -> input.Assert()));
    }

    private class testInput{
        private final int expectedTotal;
        private final int[] scores;
        private final String displayName;

        public testInput(int expectedTotal, int[] scores, String displayName){

            this.expectedTotal = expectedTotal;
            this.scores = scores;
            this.displayName = displayName;
        }
        public int getDisplayName() { return this.expectedTotal; }
        public void Assert() {
            var target = new Calculator();
            for (int n = 0; n < scores.length; n++){
                target.add(scores[n]);
            }
            var result = target.total();
            assertEquals(expectedTotal, result);
        }
    }
}