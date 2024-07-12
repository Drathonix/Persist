package mappify.extension;

import com.vicious.persist.annotations.Save;

public class TestChildObject extends TestParentObject{
    public double h = 10.0;

    //Will override parent value
    @Save
    public double k = 11.0;

    //Will override parent getter.
    @Save("h")
    public double getHOverride(){
        return h;
    }

    //Will override parent setter.
    @Save.Setter("h")
    public void setHOverride(double h){
        this.h = h;
    }
}
