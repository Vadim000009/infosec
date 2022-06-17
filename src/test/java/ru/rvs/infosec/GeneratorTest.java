package ru.rvs.infosec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rvs.infosec.Generator;

import java.util.Arrays;

public class GeneratorTest {

    Generator gen = new Generator();

    /** Тест на проверку корректности заполнения числа ГРН для ОГРН */
    @Test
    public void OGRNGRNValueTest() {
        long numberValueTest = gen.OGRN();
        numberValueTest = numberValueTest / 1000000000000L;
        Assertions.assertTrue(numberValueTest == 1 || numberValueTest == 5);
    }

    /** Тест на проверку корректности заполнения числа ГРН для ОГРНИП */
    @Test
    public void OGRNIPGRNValueTest() {
        long numberValueTest = gen.OGRNIP();
        numberValueTest = numberValueTest / 100000000000000L;
        Assertions.assertEquals(3, numberValueTest);
    }

    /** Тест на проверку корректности заполнения числа субъекта РФ для ОГРН */
    @Test
    public void OGRNSubjectRFValueTest() {
        long numberValueTest = gen.OGRN();
        long getSubjectRFValueFromValue = numberValueTest / 100000000L % 100;
        Assertions.assertTrue(Arrays.stream(gen.theSubjectOfTheRF).anyMatch(cmp -> cmp == (int) getSubjectRFValueFromValue));
    }

    /** Тест на проверку корректности заполнения числа субъекта РФ для ОГРНИП */
    @Test
    public void OGRNIPSubjectRFValueTest() {
        long numberValueTest = gen.OGRNIP();
        long getSubjectRFValueFromValue = numberValueTest / 10000000000L % 100;
        Assertions.assertTrue(Arrays.stream(gen.theSubjectOfTheRF).anyMatch(cmp -> cmp == (int) getSubjectRFValueFromValue));
    }


    /** Тест на проверку корректности контрольного числа для ОГРН */
    @Test
    public void OGRNControlNumberValueTest() {
        long controlNumberValue = gen.OGRN();
        long getControlNumber = controlNumberValue % 10;
        long getControlNumberFromValue = controlNumberValue / 10L - Math.floorDiv(controlNumberValue / 10L, 11) * 11;
        if (getControlNumberFromValue == 10L) getControlNumberFromValue = 0;
        Assertions.assertEquals(getControlNumberFromValue, getControlNumber);
    }

    /** Тест на проверку корректности контрольного числа для ОГРНИП */
    @Test
    public void OGRNIPControlNumberValueTest() {
        long controlNumberValue = gen.OGRNIP();
        long getControlNumber = controlNumberValue % 10;
        long getControlNumberFromValue = controlNumberValue / 10L - Math.floorDiv(controlNumberValue / 10L, 11) * 11;
        if (getControlNumberFromValue == 10L) getControlNumberFromValue = 0;
        Assertions.assertEquals(getControlNumberFromValue, getControlNumber);
    }

    /** Тест на проверку корректности условия перехода и размера целого числа для ОГРН */
    @Test
    public void OGRNLengthValueTest() {
        long controlNumberValue = gen.OGRNOrOGRNIP(true);
        int length = (int) (Math.log10(controlNumberValue) + 1);
        Assertions.assertEquals(length, 13);
    }

    /** Тест на проверку корректности условия перехода и размера целого числа для ОГРНИП */
    @Test
    public void OGRNIPLengthValueTest() {
        long controlNumberValue = gen.OGRNOrOGRNIP(false);
        int length = (int) (Math.log10(controlNumberValue) + 1);
        Assertions.assertEquals(length, 15);
    }

}
