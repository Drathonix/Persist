# Persist
An Annotation-Driven Persistence Library for Java.
## Using as a Dependency
```groovy
repositories {
    maven {
        url = uri("https://panel.ryuutech.com/nexus/repository/maven-releases/")
    }
}
dependencies {
    //                           Replace or use a gradle.property.
    implementation("com.vicious:persist:${persist_version}")
}
```

### Getting Started
For a more in depth explanation see [the wiki](https://github.com/Drathonix/Persist/wiki/Getting-Started).

```java
public class Example {
    @PersistentPath
    public static final String path = "data/example.json5";
    @Save
    public static int exampleVersion = 0;
    @Save(description = "The name of this project")
    public static String projectName = "Persist";

    public static void main(String[] args){
         //First reads the file if it exists.
         PersistShortcuts.readFromFile(Example.class);
         System.out.println(projectName + ":" + exampleVersion);
         if(exampleVersion = -1){
             //This change will be saved to file if it occurs.
             exampleVersion=2;
         }
         //Overwrites/writes the file
         PersistShortcuts.saveToFile(Example.class);
    }
}
```

# Credits
Includes [Annotation Detector](https://github.com/rmuller/infomas-asl/tree/master), the original maven has seemingly shut down, so it has been included locally.
