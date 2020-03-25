package de.dieserfab.buildservermanager.mapselector;

import de.dieserfab.buildservermanager.api.BSMAPI;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DomainManager {

    @Getter
    private List<Domain> domains;

    public DomainManager() {
        this.domains = new ArrayList<>();
        cacheDomains();
    }

    public Domain getDomain(String name) {
        return domains.stream().filter(domain -> domain.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }


    public void cacheDomains() {
        this.domains = BSMAPI.getInstance().getDomains();
    }

}
