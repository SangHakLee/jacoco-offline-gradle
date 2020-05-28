
- [JaCoCo Offline Instrumentation on Gradle](#jacoco-offline-instrumentation-on-gradle)
  - [Usage](#usage)
  - [On-the-fly & Offline comparison](#on-the-fly--offline-comparison)
    - [/build/classes](#buildclasses)
    - [/build/classes-instrumented](#buildclasses-instrumented)

# JaCoCo Offline Instrumentation on Gradle
https://github.com/powermock/powermock/wiki/Code-coverage-with-JaCoCo

A example of JaCoCo-Offline-Instrumentation for gradle project.

## Usage
```bash
$ ./gradlew clean build

$ ls build/classes/java/main/com/example/api/Api.class # on-the-fly
$ ls build/classes-instrumented/com/example/api/Api.class # offline
```

**You can't read `.class` file directly. Use IntelliJ IDEA or Eclipse.**

Or, look at the example I provide. ([/build/classes](#buildclasses), [/build/classes-instrumented](#buildclasses-instrumented))


## On-the-fly & Offline comparison
### /build/classes
```java
// Api.class
package com.example.api;

public class Api {
    private String greetingMsg = "Hello ";
    private String name;

    public Api(String name) {
        this.name = name;
    }

    public int sum(int x, int y) {
        return x + y;
    }

    public String getName() {
        return this.name;
    }

    public String greeting() {
        return this.greetingMsg + this.name;
    }
}
```
`On-the-fly instrumentation` doesn't manipulate the class file itself. Manipulate bytecode at runtime.


### /build/classes-instrumented
```java
// Api.class
package com.example.api;

public class Api {
    private String greetingMsg;
    private String name;

    public Api(String name) {
        boolean[] var2 = $jacocoInit();
        super();
        this.greetingMsg = "Hello ";
        this.name = name;
        var2[0] = true;
    }

    public int sum(int x, int y) {
        boolean[] var3 = $jacocoInit();
        int var10000 = x + y;
        var3[1] = true;
        return var10000;
    }

    public String getName() {
        boolean[] var1 = $jacocoInit();
        var1[2] = true;
        return this.name;
    }

    public String greeting() {
        boolean[] var1 = $jacocoInit();
        String var10000 = this.greetingMsg + this.name;
        var1[3] = true;
        return var10000;
    }
}
```
`Offline instrumentation` manipulates class files in advance.