package com.example.financerepublicassign.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class UserModel {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int id;
    private String name;
    private String saved;
    public int getId() {
        return id;
    }

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
