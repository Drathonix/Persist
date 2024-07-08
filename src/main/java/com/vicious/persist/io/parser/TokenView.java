package com.vicious.persist.io.parser;

import com.vicious.persist.except.ParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class TokenView {
    private final InputStream stream;
    public char[] tokens = new char[3];

    public TokenView(InputStream stream){
        this.stream=stream;
        Arrays.fill(tokens, ' ');
        try{
            read();
        } catch(IOException e){
            throw new ParserException("Failed to initialize token view.",e);
        }
    }

    public void read() throws IOException{
        for (int i = 1; i < tokens.length; i++) {
            tokens[i-1]=tokens[i];
        }
        if(stream.available() > 0){
            tokens[tokens.length-1]=(char)stream.read();
        }
        else{
            tokens[2]=(char)(0);
        }
    }

    public char getLastToken(){
        return tokens[0];
    }

    public char getCurrentToken(){
        return tokens[1];
    }

    public char getNextToken(){
        return tokens[2];
    }

    public boolean isSafe() throws IOException {
        return (int)tokens[2] != 0;
    }


}
