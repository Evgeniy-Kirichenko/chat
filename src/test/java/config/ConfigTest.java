package config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigTest {

    Config config = Config.getInstance();

    @Test
    public void portTest() {
        var expected = 9999;
        var result = config.getPort();
        assertEquals(expected, result);
    }

    @Test
    public void idTest(){
        var expected = "127.0.0.1";
        var result = config.getIp();
        assertEquals(expected,result);
    }
}
