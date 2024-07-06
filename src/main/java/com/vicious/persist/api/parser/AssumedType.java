package com.vicious.persist.api.parser;

public enum AssumedType {
    UNKNOWN{
        @Override
        public AssumedType append(char c) {
            if(c == '"' || c == '\''){
                return STRING;
            }
            if(Character.isDigit(c) || c == '-'){
                return INTEGER;
            }
            if(c == '.'){
                return DECIMAL;
            }
            return STRING;
        }
    },
    INTEGER{
        @Override
        public AssumedType append(char c) {
            if(Character.isDigit(c)){
                return this;
            }
            if(c == '.'){
                return DECIMAL;
            }
            else return STRING;
        }
    },
    DECIMAL{
        @Override
        public AssumedType append(char c) {
            if(Character.isDigit(c) || c == '.'){
                return this;
            }
            else return STRING;
        }
    },
    STRING{
        @Override
        public AssumedType append(char c) {
            return STRING;
        }
    };

    public abstract AssumedType append(char c);
}
