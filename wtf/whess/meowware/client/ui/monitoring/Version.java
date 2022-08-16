package wtf.whess.meowware.client.ui.monitoring;

import lombok.Getter;

public enum Version {
    V189("1.8.9"),
    V1122("1.12.2"),
    V1165("1.16.5");

    @Getter
    private String name;

    Version(String name) {
        this.name = name;
    }
}
