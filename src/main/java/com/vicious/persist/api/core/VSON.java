package com.vicious.persist.api.core;

import com.vicious.persist.api.parser.vson.VSONParserImpl;

import java.io.InputStream;
import java.util.Map;

public class VSON {
    private final VSONParserImpl parser;

    public VSON(){
        this.parser=new VSONParserImpl();
    }

    public Map<String,Object> parse(InputStream in){
        return parser.mappify(in);
    }
}
