package com.vicious.persist.parser.gon;

import com.vicious.persist.Stringify;
import com.vicious.persist.parser.AssumedType;
import com.vicious.persist.parser.IParser;
import com.vicious.persist.parser.ParserBase;

public class GONParser extends ParserBase implements IParser {
    public boolean castValues = true;

    public GONParser() {}

    public GONParser autoCastValues(boolean castValues){
        this.castValues=castValues;
        return this;
    }

    @Override
    protected Object convertFromString(String value, AssumedType type) {
        if(!castValues) {
            return super.convertFromString(value, type);
        }
        else{
            return Stringify.objectify(type.getType(),value);
        }
    }

    @Override
    protected ParserBase copy() {
        return new GONParser();
    }
}
