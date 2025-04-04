package com.example.dragun.data;

public abstract class Model {
    protected int id;

    public Model() {
        id = 0;
    }

    @Override
    public abstract String toString();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
