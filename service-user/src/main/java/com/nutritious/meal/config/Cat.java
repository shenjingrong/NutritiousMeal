package com.nutritious.meal.config;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/4/2 16:45
 **/
public class Cat implements Cloneable{

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
