package com.vicious.persist.shortcuts;

import com.vicious.persist.io.parser.IParser;
import com.vicious.persist.io.parser.gon.GONParser;
import com.vicious.persist.io.writer.IWriter;
import com.vicious.persist.io.writer.gon.GONWriter;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum NotationFormat {
    GON(GONWriter.DEFAULT, GONParser.DEFAULT,".txt",".gon"),
    JSON(GONWriter.UGLY, GONParser.DEFAULT,"json"),
    JSON5(GONWriter.JSON5, GONParser.DEFAULT,"json5");

    private final IWriter writer;
    private final IParser parser;
    private final Set<String> validExtensions;

    NotationFormat(IWriter writer, IParser parser, String... validExtensions) {
        this.writer = writer;
        this.parser = parser;
        for (String validExtension : validExtensions) {
            
        }
    }

    @SuppressWarnings("unchecked")
    public void write(Map<?,?> map, OutputStream out) {
        writer.write((Map<Object,Object>)map,out);
    }

    public Map<String,Object> parse(InputStream in) {
        return parser.mappify(in);
    }
}
