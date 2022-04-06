package com.nutritious.meal.config;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/4/2 16:44
 **/
public class Banana implements Cloneable{
    private String name;
    private int age;
    private Cat cat;

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

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }


    @Override
    public Banana clone() throws CloneNotSupportedException {
        Banana banana = (Banana) super.clone();
        Cat c = (Cat) banana.getCat().clone();
        banana.setCat(c);
        return banana;
    }
}
