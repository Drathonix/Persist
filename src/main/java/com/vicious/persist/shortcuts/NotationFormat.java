package com.vicious.persist.shortcuts;

import com.vicious.persist.io.parser.IParser;
import com.vicious.persist.io.parser.gon.GONParser;
import com.vicious.persist.io.writer.IWriter;
import com.vicious.persist.io.writer.gon.GONWriter;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public enum NotationFormat {
    GON(GONWriter.DEFAULT, GONParser.DEFAULT){
    };

    private final IWriter writer;
    private final IParser parser;

    NotationFormat(IWriter writer, IParser parser) {

        this.writer = writer;
        this.parser = parser;
    }

    @SuppressWarnings("unchecked")
    public void write(Map<?,?> map, OutputStream out) {
        writer.write((Map<Object,Object>)map,out);
    }

    public Map<String,Object> parse(InputStream in) {
        return parser.mappify(in);
    }
}
