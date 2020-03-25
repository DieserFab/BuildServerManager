package de.dieserfab.buildservermanager.mapselector;

import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.utilities.Logger;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DomainManager {

    @Getter
    private List<Domain> domains;

    public DomainManager() {
        Logger.l("iCaching all Domains");
        try {
            this.domains = new ArrayList<>();
            cacheDomains();
            Logger.l("iSuccessfully cached all Domains");
        }catch (Exception e){
            Logger.l("eError while caching the Domains:"+e.getMessage());
        }
    }

    public Domain getDomain(String name) {
        return domains.stream().filter(domain -> domain.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }


    public void cacheDomains() {
        this.domains = BSMAPI.getInstance().getDomains();
    }

}
