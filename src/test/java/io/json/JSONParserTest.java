package io.json;

import com.vicious.persist.io.parser.gon.GONParser;
import io.TesterCore;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class JSONParserTest {
    @Test
    public void testJSONParser() {
        try {
            Map<String,Object> map = new GONParser()
                    .autoCastValues(false)
                    .mappify(new FileInputStream("test_run_dir/json_parser_read.json"));
            TesterCore.parser_read_test_file(map);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
