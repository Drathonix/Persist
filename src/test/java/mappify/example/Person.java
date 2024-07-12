package mappify.example;

import com.vicious.persist.annotations.Save;
import com.vicious.persist.mappify.Mappifier;
import org.junit.jupiter.api.Test;

public class Person {
    @Save(description="The person's name.")
    public String name;
    @Save("int_id")
    public int id;

    public Person(String name, int id){
        this.id=id;
        this.name=name;
    }

    public static void main(String[] args) {
        System.out.println(Mappifier.DEFAULT.mappify(new Person("Jim",0)));
    }
}
