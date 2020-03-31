package de.dieserfab.buildservermanager.mapselector;

import lombok.Getter;

import java.util.List;

public class Category {

    @Getter
    private List<Map> maps;

    @Getter
    private String name,domain;

    public Category(String name, List<Map> maps, String domain) {
        this.maps = maps;
        this.name = name;
        this.domain = domain;
    }

    public Map getMap(String name){
        return maps.stream().filter(map -> map.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

}
