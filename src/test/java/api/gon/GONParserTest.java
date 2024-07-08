package api.gon;

import com.vicious.persist.io.parser.gon.GONParser;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class GONParserTest {
    @Test
    public void testVSONParser() {
        try {
            Map<String,Object> map = new GONParser()
                    .autoCastValues(false)
                    .mappify(new FileInputStream("test_run_dir/parser_read.test"));
            TesterCore.parser_read_test_file(map);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
