package com.vicious.persist.api.parser.enums;

public enum State {
    SKIP_IRRELEVANT {
        @Override
        public boolean skipsWhitespace() {
            return true;
        }
    },
    READING_KEY,
    FINDING_VALUE {
        @Override
        public boolean skipsWhitespace() {
            return true;
        }
    },
    READING_VALUE;
    public boolean skipsWhitespace() {
        return false;
    }
}
