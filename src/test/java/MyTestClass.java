import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class MyTestClass {
    private Calculator calculatorUnderTest;
    private static Instant startedAt;

    @BeforeAll
    public static void initStartinggTime() {
        System.out.println("Appel avant tous les tests");
        startedAt = Instant.now();
    }

    @AfterAll
    public static void showTestDuration() {
        System.out.println("Appel après tous les test");
        Instant endedAt = Instant.now();
        long duration = Duration.between(startedAt, endedAt).toMillis();
        System.out.println(MessageFormat.format("Durée des test : {0} ms", duration));
    }


    @BeforeEach
    public void initCalculator() {
        calculatorUnderTest = new Calculator();
        System.out.println("Avant chaque test");
    }

    @AfterEach
    public void undefCalculator() {
        System.out.println("Appel après chaque test");
        calculatorUnderTest = null;
    }

    @Test
    void testAddTwoPositiveNumbers() {
        // ARRANGE
        int a = 2;
        int b= 3;


        // ACT
        int somme = calculatorUnderTest.add(a,b);

        // ASSERT
//        assertEquals(5,somme);
        assertThat(somme).isEqualTo(5);

    }

    @Test
    void multiply() {
        // Arrange
        int a = 5;
        int b = 3;

        // Act
        int produit = calculatorUnderTest.multiply(a, b);

        // Assert
//        assertEquals(15, produit);
        assertThat(produit).isEqualTo(15);

    }

    @ParameterizedTest(name = "{0} x 0 doit etre egal a zero")
    @ValueSource(ints = {1, 2, 42, 1001, 5089})
    public void multiply_ShouldReturnZero_ofZeroWithMultipleIntegers(int arg) {
        // ARRANGE - Tout est pret!

        // ACT -- Multiplier par zero
        int actualResult = calculatorUnderTest.multiply(arg, 0);

        // ASSERT -Ca vaut toujours zero!
        assertEquals(0,actualResult);
        assertThat(actualResult).isEqualTo(0);
    }

    @ParameterizedTest(name="{0} + {1} doit etre egal à {2}")
    @CsvSource({"1,1,2", "2,3,5", "42,57,99"})
    public void add_shouldReturnTheSum_ofMultipleIntegers(int arg1, int arg2, int expectResult) {
        // ARRANGE - Tout est pret!

        // ACT
        int actualResult = calculatorUnderTest.add(arg1,arg2);

        // ASSERT
//        assertEquals(expectResult, actualResult);
        assertThat(actualResult).isEqualTo(expectResult);

    }

    @Test
    @Timeout(1)
    public void longCalcul_shouldComputeInLessThan1Second() {
        // Arrange

        // Act
        calculatorUnderTest.longCalculation();

        // Assert
    }

    @Test
    public void listDigits_shouldReturnTheListOfDigits_ofPositiveInteger() {
        // GIVEN
        int number = 95897;

        // WHEN
        Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

        //THEN
        final Set<Integer> expectedDigits = Stream.of(5,7,8,9).collect(Collectors.toSet());
        assertEquals(expectedDigits, actualDigits);
        assertThat(actualDigits).containsExactlyInAnyOrder(9,5,8,7);

    }

    @Test
    public void listDigits_shouldReturnTheListOfDigits_ofNegativeInteger() {
        // GIVEN
        final int number = -124432;
        final Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);
        assertThat(actualDigits).containsExactlyInAnyOrder(1,2,3,4);
    }
    @Test
    public void listDigits_shouldReturnTheListOfZero_ofZero() {
        // GIVEN
        final int number = 0;
        final Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);
        assertThat(actualDigits).containsExactlyInAnyOrder(0);
    }
}
