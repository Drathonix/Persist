package io.json;

import com.vicious.persist.io.parser.gon.GONParser;
import io.JSONTesterCore;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColonStringTest {
    @Test
    public void colonTest() {
        try {
            Map<String,Object> map = new GONParser()
                    .autoCastValues(false)
                    .mappify(new FileInputStream("test_run_dir/string_with_colon.json"));
            System.out.println(map);
            assertEquals("namespace:key",map.get("str_val"));
            List<String> strList = (List<String>)map.get("set_val");
            Map<String,String> strMap = (Map<String,String>)map.get("map_val");
            for (int i = 1; i <= 3; i++) {
                assertEquals("name"+i+":key"+i,strList.get(i-1));
                assertEquals("name"+i+":key"+i,strMap.get("k"+i));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
