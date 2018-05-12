package com.vekain19.prathamvisioncodingchallenge.helpers.Entity;

import java.io.Serializable;

public class Pojo implements Serializable {

    private String name;
    private String number;
    private String DateTime;

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
