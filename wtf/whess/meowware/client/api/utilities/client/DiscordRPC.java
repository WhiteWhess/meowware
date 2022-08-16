package wtf.whess.meowware.client.api.utilities.client;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRichPresence;

public final class DiscordRPC {

    private boolean running = true;
    private long created = 0;

    public void init() {
        running = true;
        this.created = System.currentTimeMillis();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(discordUser -> {
        }).build();
        net.arikia.dev.drpc.DiscordRPC.discordInitialize("996857141761671208", handlers, true);

        new Thread("") {

            @Override
            public void run() {
                net.arikia.dev.drpc.DiscordRPC.discordRunCallbacks();
            }

        }.start();
    }

    public void shutdown() {
        running = false;
        net.arikia.dev.drpc.DiscordRPC.discordShutdown();
    }

    public void update(String firstLine, String secondLine) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
        b.setBigImage("meowware", "get good. get meowware.");
        b.setSmallImage("", "");
        b.setDetails(firstLine);
        b.setStartTimestamps(created);
        net.arikia.dev.drpc.DiscordRPC.discordUpdatePresence(b.build());
    }

}
