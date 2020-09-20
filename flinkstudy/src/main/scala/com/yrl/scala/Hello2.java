package com.yrl.scala;

import static jdk.nashorn.internal.objects.Global.print;

public class Hello2 {
    public String name;
    public Integer age;
    public Hello2(String name, Integer age){
        this.age = age;
        this.name = name;
        print("hello");
    }
}
