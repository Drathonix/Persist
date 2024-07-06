package com.vicious.persist.parser;

import com.vicious.persist.except.ParserException;
import com.vicious.persist.parser.enums.CommentType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ParserBase implements IParser {
    protected CommentType commentState;
    protected TokenView view;

    protected void setup(TokenView view) {
        commentState = CommentType.NONE;
        this.view = view;
    }

    @Override
    public Map<String, Object> mappify(TokenView tokenView) {
        setup(tokenView);
        try {
            Map<String, Object> map = new HashMap<>();
            while(view.isSafe()){
                if(!skipIrrelevantData()){
                    view.read();
                    return map;
                }
                String key = readKey();
                if(!skipIrrelevantData()){
                    view.read();
                    return map;
                }
                map.put(key, readValue());
            }
            return map;
        } catch (Throwable e) {
            throw new ParserException("Failed to parse map due to an error.",e);
        }
    }

    @Override
    public List<Object> listify(TokenView tokenView) {
        setup(tokenView);
        try {
            List<Object> list = new ArrayList<>();
            while(view.isSafe()){
                if(!skipIrrelevantData()){
                    view.read();
                    return list;
                }
                list.add(readValue());
            }
            return list;
        } catch (Throwable e) {
            throw new ParserException("Failed to parse list due to an error.",e);
        }
    }

    protected boolean skipIrrelevantData() {
        try {
            while (view.isSafe()) {
                if(!isEscaped()){
                    updateCommentState();
                    if(inComment()){
                        view.read();
                        continue;
                    }
                    //Can mutate on comment checks.
                    char c = getCurrentToken();
                    //Is a map or list end.
                    if(c == ']' || c == '}'){
                        return false;
                    }
                    //Found a real character.
                    if(!(isWhitespace(c) || c == '=' || c == ',' || isNewline(c))){
                        return true;
                    }
                }
                view.read();
            }
            //End of file.
            return false;
        } catch (IOException e) {
            throw new ParserException("Failed to find key due to an error.",e);
        }
    }

    protected String readKey(){
        try {
            StringBuilder key = new StringBuilder();
            while (view.isSafe()) {
                if(!isNameTerminus()) {
                    key.append(getCurrentToken());
                }
                else{
                    return key.toString().trim();
                }
                view.read();
            }
            return key.toString().trim();
        } catch (IOException e) {
            throw new ParserException("Failed to read key due to an error.",e);
        }
    }

    protected Object readValue() {
        try {
            StringBuilder value = new StringBuilder();
            if(getCurrentToken() == '{'){
                ParserBase mapper = copy();
                view.read();
                return mapper.mappify(view);
            }
            if(getCurrentToken() == '['){
                ParserBase collector = copy();
                view.read();
                return collector.listify(view);
            }
            AssumedType type = AssumedType.UNKNOWN.append(getCurrentToken());
            value.append(getCurrentToken());
            while (view.isSafe()) {
                view.read();
                if(!isEndOfValue()) {
                    value.append(getCurrentToken());
                    type = type.append(getCurrentToken());
                }
                else{
                    return trimValue(value.toString(), type);
                }
            }
            return trimValue(value.toString(), type);
        } catch (IOException e) {
            throw new ParserException("Failed to read value due to an error.",e);
        }
    }

    protected Object trimValue(String value, AssumedType type) {
        try {
            value = value.trim();
            if(type == AssumedType.CHAR && value.length() > 3){
                type = AssumedType.STRING;
            }
            if (type == AssumedType.STRING) {
                char f = value.charAt(0);
                char e = value.charAt(value.length() - 1);
                if ("'\"".contains("" + f) || "'\"".contains("" + e)) {
                    return convertFromString(value.substring(1, value.length() - 1),type);
                }
            }
            if(type == AssumedType.CHAR){
                if(value.isEmpty()){
                    throw new ParserException("Cannot parse a char due to an empty string.");
                }
                return value.length() > 1 ? value.charAt(1) : value.charAt(0);
            }
            return convertFromString(value,type);
        } catch (Exception e) {
            if(e instanceof ParserException){
                throw e;
            }
            throw new ParserException("Failed to trim " + value + " due to an error.",e);
        }
    }

    protected Object convertFromString(String value, AssumedType type) {
        return value;
    }

    protected boolean isEndOfValue() {
        char current = getCurrentToken();
        return !isEscaped() && (
                isNewline(current) ||
                current == ',' ||
                current == '}' ||
                current == ']');
    }

    protected boolean isNameTerminus() {
        char current = getCurrentToken();
        return !isEscaped() && current == '=' || current == ':';
    }


    protected boolean isEscaped(){
        return getLastToken() == '\\';
    }

    protected boolean isWhitespace(char current) {
        return Character.isWhitespace(current);
    }

    protected abstract ParserBase copy();

    @Override
    public TokenView getTokenView() {
        return view;
    }

    @Override
    public CommentType getCommentState() {
        return commentState;
    }

    @Override
    public void setCommentState(CommentType commentState) {
        this.commentState = commentState;
    }
}
