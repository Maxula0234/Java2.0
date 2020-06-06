import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Method;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestChat {

    @ParameterizedTest
    @CsvSource({"1","2","3"})
    public void test1(String s){
        System.out.println(String.format("%s",s));
    }
}
