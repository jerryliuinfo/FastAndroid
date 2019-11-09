package com.apache.fastandroid;

/**
 * author: 01370340
 * data: 2019-10-18
 * description:
 */
public class UserEvent {
    public String name;
    public int age;

    public UserEvent() {
    }

    public UserEvent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserEvent{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
