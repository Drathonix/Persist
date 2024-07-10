package io.gon;

import com.vicious.persist.io.parser.gon.GONParser;
import com.vicious.persist.io.writer.gon.GONWriter;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

public class GONWriterTest {
    @Test
    public void testVSONWriter() {
        //Depends on the parser working.
        try {
            GONWriter writer = new GONWriter();
            GONParser vsonParser = new GONParser();
            Map<String,Object> map = vsonParser.mappify(new FileInputStream("test_run_dir/parser_read.test"));
            writer.write(map,new FileOutputStream("test_run_dir/parser_write.test"));
            TesterCore.parser_write_test_file(vsonParser.mappify(new FileInputStream("test_run_dir/parser_write.test")));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
