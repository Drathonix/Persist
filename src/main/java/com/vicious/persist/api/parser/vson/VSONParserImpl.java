package com.vicious.persist.api.parser.vson;

import com.vicious.persist.api.parser.ParserBase2;

import java.io.InputStream;
import java.util.Map;

public class VSONParserImpl extends ParserBase2 implements IVSONParser{

    public VSONParserImpl() {}

    @Override
    protected ParserBase2 copy() {
        return new VSONParserImpl();
    }
}
