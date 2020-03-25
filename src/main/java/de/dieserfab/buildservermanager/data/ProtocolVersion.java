package de.dieserfab.buildservermanager.data;

import lombok.Getter;
import org.bukkit.Bukkit;

public enum ProtocolVersion {

    V1_8(1, "v1_8_R1"),
    V1_8_8(2, "v1_8_R3"),
    V1_12(3, "v1_12_R1"),
    V1_12_2(4, "v1_12_R1"),
    V1_14(5, "v1_14_R1"),
    V1_14_2(6, "v1_14_R1"),
    V1_14_4(7, "v1_14_R1"),
    V1_15(8, "v1_15_R1"),
    V1_15_2(9, "v1_15_R1");

    ProtocolVersion(int version, String identifier) {
        this.version = version;
        this.identifier = identifier;
    }

    public static ProtocolVersion setProtocolVersion() {
        String protocolVersion = Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit", "").replace(".", "");
        for (ProtocolVersion version : values()) {
            if (version.getIdentifier().equalsIgnoreCase(protocolVersion)) {
                serverVersion = version.getVersion();
                return version;
            }
        }
        serverVersion = 999;
        return null;
    }

    public static boolean isHigherOrEqual(ProtocolVersion version) {
        return version.getVersion() >= getServerVersion();
    }

    @Getter
    private static int serverVersion;
    @Getter
    private int version;
    @Getter
    private String identifier;

}
