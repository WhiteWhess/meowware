package wtf.whess.meowware.client.main.functions;

public enum Category {

    combat ("Combat"),
    move ("Move"),
    visual("Visual"),
    misc ("Misc"),
    exploit ("Exploit"),
    bots ("Bots");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
