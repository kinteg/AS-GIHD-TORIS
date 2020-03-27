package ru.iac.ASGIHDTORIS;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.iac.ASGIHDTORIS.parser.file.type.TypeValidator;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TypeValidatorTest {

    private final List<String> NUMBER_LIST = Arrays.asList("1", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2");
    private final List<String> NUMBER_LIST2 = Arrays.asList("4294967295", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2");
    private final List<String> NUMBER_LIST3 = Arrays.asList("4294967295", "2", "2", "2", "2.1", "2", "2", "2", "2", "2", "2");
    private final List<String> DOUBLE_LIST = Arrays.asList("1.1", "2.2", "2.2", "2.2", "2.2", "2.2", "2.2", "2.2", "2.2", "2.2", "2.2");
    private final List<String> DATE_LIST = Arrays.asList("20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999");
    private final List<String> DATE_LIST2 = Arrays.asList("20.11.1999", "20.11.1999", "1", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999", "20.11.1999");
    private final List<String> DATE_LIST3 = Arrays.asList("04:05:06", "04:05:06.728", "04:05:06", "04:05:06");
    private final List<String> DATE_LIST4 = Arrays.asList("20.11.1999 04:05:06", "20.11.1999 04:05:06.728", "20.11.1999 04:05:06", "20.11.1999 04:05:06");

    @Test
    public void isValidNumberList() {
        TypeValidator validator = new TypeValidator();

        assertTrue(validator.isInt(NUMBER_LIST));
        assertTrue(validator.isLong(NUMBER_LIST));

        assertTrue(validator.isDouble(NUMBER_LIST));
        assertTrue(validator.isFloat(NUMBER_LIST));

        assertFalse(validator.isDate(NUMBER_LIST));
        assertFalse(validator.isDateTime(NUMBER_LIST));
        assertFalse(validator.isTime(NUMBER_LIST));

    }

    @Test
    public void isValidNumberList2() {
        TypeValidator validator = new TypeValidator();

        assertFalse(validator.isInt(NUMBER_LIST2));
        assertTrue(validator.isLong(NUMBER_LIST2));

        assertTrue(validator.isDouble(NUMBER_LIST2));
        assertTrue(validator.isFloat(NUMBER_LIST2));

        assertFalse(validator.isDate(NUMBER_LIST2));
        assertFalse(validator.isDateTime(NUMBER_LIST2));
        assertFalse(validator.isTime(NUMBER_LIST));

    }

    @Test
    public void isValidNumberList3() {
        TypeValidator validator = new TypeValidator();

        assertFalse(validator.isInt(NUMBER_LIST3));
        assertFalse(validator.isLong(NUMBER_LIST3));

        assertTrue(validator.isDouble(NUMBER_LIST3));
        assertTrue(validator.isFloat(NUMBER_LIST3));

        assertFalse(validator.isDate(NUMBER_LIST3));
        assertFalse(validator.isDateTime(NUMBER_LIST3));
        assertFalse(validator.isTime(NUMBER_LIST));

    }

    @Test
    public void isValidNumberList4() {
        TypeValidator validator = new TypeValidator();

        assertFalse(validator.isInt(DATE_LIST2));
        assertFalse(validator.isLong(DATE_LIST2));

        assertFalse(validator.isDouble(DATE_LIST2));
        assertFalse(validator.isFloat(DATE_LIST2));

        assertFalse(validator.isDate(DATE_LIST2));
        assertFalse(validator.isDateTime(DATE_LIST2));
        assertFalse(validator.isTime(NUMBER_LIST));

    }

    @Test
    public void isValidDoubleList1() {
        TypeValidator validator = new TypeValidator();

        assertFalse(validator.isInt(DOUBLE_LIST));
        assertFalse(validator.isLong(DOUBLE_LIST));

        assertTrue(validator.isDouble(DOUBLE_LIST));
        assertTrue(validator.isFloat(DOUBLE_LIST));

        assertFalse(validator.isDate(DOUBLE_LIST));
        assertFalse(validator.isDateTime(DOUBLE_LIST));
        assertFalse(validator.isTime(NUMBER_LIST));

    }

    @Test
    public void isValidDateList1() {
        TypeValidator validator = new TypeValidator();

        assertFalse(validator.isInt(DATE_LIST));
        assertFalse(validator.isLong(DATE_LIST));

        assertFalse(validator.isDouble(DATE_LIST));
        assertFalse(validator.isFloat(DATE_LIST));

        assertTrue(validator.isDate(DATE_LIST));
        assertTrue(validator.isDateTime(DATE_LIST));
        assertFalse(validator.isTime(NUMBER_LIST));

    }

    @Test
    public void isValidDateList2() {
        TypeValidator validator = new TypeValidator();

        assertFalse(validator.isInt(DATE_LIST3));
        assertFalse(validator.isLong(DATE_LIST3));

        assertFalse(validator.isDouble(DATE_LIST3));
        assertFalse(validator.isFloat(DATE_LIST3));

        assertFalse(validator.isDate(DATE_LIST3));
        assertFalse(validator.isDateTime(DATE_LIST3));
        assertTrue(validator.isTime(DATE_LIST3));

    }

    @Test
    public void isValidDateList3() {
        TypeValidator validator = new TypeValidator();

        assertFalse(validator.isInt(DATE_LIST4));
        assertFalse(validator.isLong(DATE_LIST4));

        assertFalse(validator.isDouble(DATE_LIST4));
        assertFalse(validator.isFloat(DATE_LIST4));

        assertFalse(validator.isDate(DATE_LIST4));
        assertTrue(validator.isDateTime(DATE_LIST4));
        assertFalse(validator.isTime(DATE_LIST4));

    }

}
