package com.example.admin.healthyfoodlistview;

import java.io.Serializable;

public class Collection implements Serializable {
    private String name;
    private String tag;
    private String food_type;
    private String nutrients;
    private String bg_color;
    private boolean is_collected;

    public Collection(String name, String tag, String food_type, String nutrients, String bg_color) {
        setName(name);
        setTag(tag);
        setFood_type(food_type);
        setNutrients(nutrients);
        setBg_color(bg_color);
        setIs_collected(false);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public void setNutrients(String nutrients) {
        this.nutrients = nutrients;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public void setIs_collected(boolean is_collected) {
        this.is_collected = is_collected;
    }

    public boolean getIs_collected() {
        return is_collected;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getFood_type() {
        return food_type;
    }

    public String getNutrients() {
        return nutrients;
    }

    public String getBg_color() {
        return bg_color;
    }
}
