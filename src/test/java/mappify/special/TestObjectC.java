package mappify.special;

import com.vicious.persist.annotations.C_NAME;
import com.vicious.persist.annotations.Save;

@C_NAME("testobjectc")
public class TestObjectC implements ITestInterfaceA{
    public char c = 'b';

    @Override
    public char getChar() {
        return c;
    }

    @Override
    public void setChar(char c) {
        this.c=c;
    }
}
