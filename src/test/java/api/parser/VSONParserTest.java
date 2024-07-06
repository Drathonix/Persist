package api.parser;

import com.vicious.persist.api.core.VSON;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class VSONParserTest {
    VSON vson = new VSON();
    @Test
    public void testVSONParser() {
        try {
            Map<String,Object> map = vson.parse(new FileInputStream("test_run_dir/parser_read.test"));
            TesterCore.parser_read_test_file(map);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
