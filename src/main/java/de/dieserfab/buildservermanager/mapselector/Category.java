package de.dieserfab.buildservermanager.mapselector;

import lombok.Getter;

import java.util.List;

public class Category {

    @Getter
    private List<Map> maps;

    @Getter
    private String name;

    public Category(String name, List<Map> maps) {
        this.maps = maps;
        this.name = name;
    }

    public Map getMap(String name){
        return maps.stream().filter(map -> map.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

}
