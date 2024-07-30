package io.gon;

import com.vicious.persist.io.parser.gon.GONParser;
import com.vicious.persist.io.writer.gon.GONWriter;
import com.vicious.persist.io.writer.wrapped.WrappedObject;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

public class GONWriterTest {
    @Test
    public void testGONWriter() {
        //Depends on the parser working.
        try {
            GONWriter writer = new GONWriter();
            GONParser vsonParser = new GONParser();
            Map<String,Object> map = vsonParser.mappify(new FileInputStream("test_run_dir/parser_read.test"));
            map.put("commentedValue1", WrappedObject.of(123,"This is a test comment. It is long enough to be multi-line. I am adding some extra comment lore. This comment was written at 3:28 PM on 7/30/2024. It was made to test if comments were written (and ignored properly). 23089231089213089e9dsa90sjisjk432ljk234jads q23e 423jk e dkjsajfejk435ij34234klj234kjdnkasm r32 n23r kdsakklclkdgjkt43jkjkresac"));
            map.put("commentedValue2", WrappedObject.of(1234,"This is a single-line test comment."));
            map.put("commentedValue3", WrappedObject.of(1235,"This is a double-line test comment. Double line data. Cool text. Cool text 2. Really well written message."));
            writer.write(map,new FileOutputStream("test_run_dir/parser_write.test"));
            TesterCore.parser_write_test_file(vsonParser.mappify(new FileInputStream("test_run_dir/parser_write.test")));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
