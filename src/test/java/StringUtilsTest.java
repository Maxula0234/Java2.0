import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.levelup.lessons.lesson8.StringUtils;

public class StringUtilsTest {

    @Test
    @DisplayName("Return true if string is null")
    public void testIsblanck_whenStringIsNull_thenReturnTrue(){
        boolean result = StringUtils.isBlanck(null);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Return true if string empty")
    public void testIsBlank_whenStringIsEmpty_thenReturnTrue(){
        Assertions.assertTrue(StringUtils.isBlanck(""));
    }

    @Test
    @DisplayName("Return true if string consists only from whitespace")
    public void testIsBlank_whenStringContainceOnlyFromWhitespace_thenReturnTrue(){
        Assertions.assertTrue(StringUtils.isBlanck("        "));
    }


}
