
- [JaCoCo Offline Instrumentation on Gradle](#jacoco-offline-instrumentation-on-gradle)
  - [Usage](#usage)
  - [On-the-fly & Offline comparison](#on-the-fly--offline-comparison)
    - [/build/classes](#buildclasses)
    - [/build/classes-instrumented](#buildclasses-instrumented)
  - [bytecode](#bytecode)
    - [/build/classes](#buildclasses-1)
    - [/build/classes-instrumented](#buildclasses-instrumented-1)

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

Or, use `javap`. Of course, I'll provide an example.([/build/classes](#buildclasses-1), [/build/classes-instrumented](#buildclasses-instrumented-1))

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

## bytecode
### /build/classes
```bash
$ javap -c Api.class
```
```java
public class com.example.api.Api {
  public com.example.api.Api(java.lang.String);
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: aload_0
       5: ldc           #2                  // String Hello
       7: putfield      #3                  // Field greetingMsg:Ljava/lang/String;
      10: aload_0
      11: aload_1
      12: putfield      #4                  // Field name:Ljava/lang/String;
      15: return

  public int sum(int, int);
    Code:
       0: iload_1
       1: iload_2
       2: iadd
       3: ireturn

  public java.lang.String getName();
    Code:
       0: aload_0
       1: getfield      #4                  // Field name:Ljava/lang/String;
       4: areturn

  public java.lang.String greeting();
    Code:
       0: new           #5                  // class java/lang/StringBuilder
       3: dup
       4: invokespecial #6                  // Method java/lang/StringBuilder."<init>":()V
       7: aload_0
       8: getfield      #3                  // Field greetingMsg:Ljava/lang/String;
      11: invokevirtual #7                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      14: aload_0
      15: getfield      #4                  // Field name:Ljava/lang/String;
      18: invokevirtual #7                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      21: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      24: areturn
}
```

### /build/classes-instrumented
```bash
$ javap -c Api.class
```
```java
public class com.example.api.Api {
  public com.example.api.Api(java.lang.String);
    Code:
       0: invokestatic  #47                 // Method $jacocoInit:()[Z
       3: astore_2
       4: aload_0
       5: invokespecial #1                  // Method java/lang/Object."<init>":()V
       8: aload_0
       9: ldc           #2                  // String Hello
      11: putfield      #3                  // Field greetingMsg:Ljava/lang/String;
      14: aload_0
      15: aload_1
      16: putfield      #4                  // Field name:Ljava/lang/String;
      19: aload_2
      20: iconst_0
      21: iconst_1
      22: bastore
      23: return

  public int sum(int, int);
    Code:
       0: invokestatic  #47                 // Method $jacocoInit:()[Z
       3: astore_3
       4: iload_1
       5: iload_2
       6: iadd
       7: aload_3
       8: iconst_1
       9: iconst_1
      10: bastore
      11: ireturn

  public java.lang.String getName();
    Code:
       0: invokestatic  #47                 // Method $jacocoInit:()[Z
       3: astore_1
       4: aload_0
       5: getfield      #4                  // Field name:Ljava/lang/String;
       8: aload_1
       9: iconst_2
      10: iconst_1
      11: bastore
      12: areturn

  public java.lang.String greeting();
    Code:
       0: invokestatic  #47                 // Method $jacocoInit:()[Z
       3: astore_1
       4: new           #5                  // class java/lang/StringBuilder
       7: dup
       8: invokespecial #6                  // Method java/lang/StringBuilder."<init>":()V
      11: aload_0
      12: getfield      #3                  // Field greetingMsg:Ljava/lang/String;
      15: invokevirtual #7                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      18: aload_0
      19: getfield      #4                  // Field name:Ljava/lang/String;
      22: invokevirtual #7                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      25: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      28: aload_1
      29: iconst_3
      30: iconst_1
      31: bastore
      32: areturn
}
```
- Look at the byte code, you can see the words '$jacocoInit'