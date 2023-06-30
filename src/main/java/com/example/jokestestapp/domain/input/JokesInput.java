package com.example.jokestestapp.domain.input;

public class JokesInput {
    private Integer count = 5;

    public JokesInput(Integer count) {
        this.count = count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public Integer getCount() {
        return count;
    }
}
