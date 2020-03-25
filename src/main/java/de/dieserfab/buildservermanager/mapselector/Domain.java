package de.dieserfab.buildservermanager.mapselector;

import lombok.Getter;

import java.util.List;

public class Domain {

    @Getter
    private String name;

    @Getter
    private List<Category> categories;

    public Domain(String name, List<Category> categories) {
        this.name = name;
        this.categories = categories;
    }

    public Category getCategory(String name) {
        return categories.stream().filter(category -> category.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

}
