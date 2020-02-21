package xyz.thetbw.blog.data.model.admin;

import xyz.thetbw.blog.data.entity.Category;

import java.util.List;

public class CategoryOptionModel {
    private int value;
    private String label;
    private List<CategoryOptionModel> children;


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<CategoryOptionModel> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryOptionModel> children) {
        this.children = children;
    }
}
