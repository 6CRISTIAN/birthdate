package com.latam.birthdate.dto;

import com.latam.birthdate.model.Poem;

public class UserInfoDto {

    private String name;
    private int age;
    private long remainingDays;
    private Poem poem;

    public UserInfoDto() {
    }

    public UserInfoDto(String name, int age, long remainingDays) {
        this.name = name;
        this.age = age;
        this.remainingDays = remainingDays;
    }

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

    public long getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(long remainingDays) {
        this.remainingDays = remainingDays;
    }

    public Poem getPoem() {
        return poem;
    }

    public void setPoem(Poem poem) {
        this.poem = poem;
    }
}
