package io.json5;

import com.vicious.persist.io.parser.gon.GONParser;
import io.JSONTesterCore;
import io.TesterCore;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class JSON5ParserTest {
    @Test
    public void testJSON5Parser() {
        try {
            Map<String,Object> map = new GONParser()
                    .autoCastValues(false)
                    .mappify(new FileInputStream("test_run_dir/json_parser_read.json"));
            JSONTesterCore.parser_read_test_file(map);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
