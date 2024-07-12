package mappify.extension;

import com.vicious.persist.annotations.Save;

public class TestParentObject {
    @Save
    public int i = 1;
    public int j = 2;
    public int h = 3;

    @Save
    public int k = 4;

    @Save("j")
    public int getJ(){
        return j;
    }

    @Save.Setter("j")
    public void setJ(int j){
        this.j = j;
    }

    @Save("h")
    public int getH(){
        return h;
    }

    @Save.Setter("h")
    public void setH(int h){
        this.h = h;
    }
}
