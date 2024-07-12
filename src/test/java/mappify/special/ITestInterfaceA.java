package mappify.special;

import com.vicious.persist.annotations.Save;

public interface ITestInterfaceA {
    @Save.Setter("char")
    default void setChar2(char c){
        setChar(c);
    }


    @Save("char")
    default char getChar2(){
        return getChar();
    }

    char getChar();
    void setChar(char c);
}
