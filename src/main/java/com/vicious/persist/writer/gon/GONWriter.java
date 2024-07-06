package com.vicious.persist.writer.gon;

import com.vicious.persist.Stringify;
import com.vicious.persist.except.WriterException;
import com.vicious.persist.writer.IWriter;
import com.vicious.persist.writer.WrappedObject;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

public class GONWriter implements IWriter {
    private Separation listValueSeparator = Separation.NEWLINE;
    private Separation mapEntrySeparator = Separation.NEWLINE;
    private int tabWidth = 1;
    private int equalsPadding = 1;
    private int commaPadding = 1;
    private boolean stringsAutomaticallyQuoted = true;
    private boolean charsAutomaticallyQuoted = true;
    private int commentLineWrap = 72;

    public GONWriter() {}

    public GONWriter quoteStrings(boolean setting) {
        this.stringsAutomaticallyQuoted=setting;
        return this;
    }

    public GONWriter quoteChars(boolean setting) {
        this.charsAutomaticallyQuoted=setting;
        return this;
    }

    public GONWriter useListValueSeparator(@NotNull Separation separation) {
        listValueSeparator=separation;
        return this;
    }

    public GONWriter useMapEntrySeparator(@NotNull Separation separation) {
        mapEntrySeparator=separation;
        return this;
    }

    public GONWriter useCommentLineWrap(int length) {
        commentLineWrap=length;
        return this;
    }

    public GONWriter useTabWidth(int width) {
        tabWidth=width;
        return this;
    }

    public GONWriter equalsPadding(int padding) {
        this.equalsPadding = padding;
        return this;
    }

    public GONWriter commaPadding(int padding) {
        this.commaPadding=padding;
        return this;
    }

    @Override
    public void write(@NotNull Map<? extends Object, Object> map, @NotNull OutputStream out) {
        write(map,out,0);
    }

    public void write(Map<? extends Object, Object> map, OutputStream out, int depth) {
        map.forEach((k, v) -> {
            try {
                Object value = WrappedObject.unwrap(v);
                String comment = WrappedObject.unwrapComment(v);
                if(!comment.isEmpty()){
                    writeComment(out,comment,depth);
                }
                if(mapEntrySeparator == Separation.COMMA && (value instanceof Collection || value instanceof Map)){
                    out.write('\n');
                }
                if(mapEntrySeparator==Separation.NEWLINE || value instanceof Map || value instanceof Collection) {
                    writeChar(out,'\t',depth*tabWidth);
                }
                out.write(Stringify.stringify(k).getBytes(StandardCharsets.UTF_8));
                writeChar(out,' ',equalsPadding);
                out.write('=');
                writeChar(out,' ',equalsPadding);
                writeValue(value,out,depth);
                if(mapEntrySeparator==Separation.NEWLINE || value instanceof Map || value instanceof Collection) {
                    out.write('\n');
                }
                else{
                    out.write(',');
                    writeChar(out,' ',commaPadding);
                }
            } catch (IOException e) {
                throw new WriterException("Failed to write.",e);
            }
        });
    }

    @SuppressWarnings("unchecked")
    protected void writeValue(Object value, OutputStream out, int depth) throws IOException {
        if(value instanceof Map){
            out.write('{');
            if(mapEntrySeparator==Separation.NEWLINE) {
                out.write('\n');
            }
            write((Map<Object, Object>) value,out,depth+1);
            tabs(out,tabWidth*depth);
            out.write('}');
            return;
        }
        if(value instanceof Collection){
            out.write('[');
            if(listValueSeparator==Separation.NEWLINE) {
                out.write('\n');
            }
            writeCollection((Collection<Object>)value,out,depth+1);
            tabs(out,tabWidth*depth);
            out.write(']');
            return;
        }
        String stringifiedObject = Stringify.stringify(value);
        if(stringsAutomaticallyQuoted && value instanceof String){
            stringifiedObject = "\""+stringifiedObject+"\"";
        }
        if(charsAutomaticallyQuoted && value instanceof Character){
            stringifiedObject = "'"+stringifiedObject+"'";
        }
        out.write(stringifiedObject.getBytes(StandardCharsets.UTF_8));
    }

    private void tabs(OutputStream out, int n) throws IOException {
        writeChar(out,'\t',n);
    }

    private void writeCollection(Collection<Object> collection, OutputStream out, int depth) {
       collection.forEach(v -> {
            try {
                Object value = WrappedObject.unwrap(v);
                String comment = WrappedObject.unwrapComment(v);
                if(!comment.isEmpty()){
                    writeComment(out,comment,depth);
                }
                if(listValueSeparator == Separation.COMMA && (value instanceof Collection || value instanceof Map)){
                    out.write('\n');
                }
                if(listValueSeparator==Separation.NEWLINE || value instanceof Map || value instanceof Collection) {
                    writeChar(out,'\t',depth*tabWidth);
                }
                writeValue(value,out,depth);
                if(listValueSeparator==Separation.NEWLINE) {
                    out.write('\n');
                }
                else{
                    out.write(',');
                    writeChar(out,' ',commaPadding);
                }
            } catch (IOException e) {
                throw new WriterException("Failed to write.",e);
            }
        });
    }

    protected void writeComment(OutputStream out, String comment, int depth) throws IOException {
        int lines = comment.length()/commentLineWrap+1;
        int start = 0;
        int end;
        out.write("/*".getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < lines; i++) {
            end = Math.min(comment.length(),(i+1)*commentLineWrap);
            int j = end;
            while (j >= 0) {
                if(Character.isWhitespace(comment.charAt(j))) {
                    end=j;
                }
                j--;
            }
            String line = comment.substring(start, end);
            if(i == lines - 1) {
                line+="*/";
            }
            start = end;
            writeChar(out,'\t',depth*tabWidth);
            out.write(line.getBytes(StandardCharsets.UTF_8));
            out.write('\n');
        }
    }

    protected void writeChar(OutputStream out, char c, int times) throws IOException {
        for (int i = 0; i < times; i++) {
            out.write(c);
        }
    }
}
