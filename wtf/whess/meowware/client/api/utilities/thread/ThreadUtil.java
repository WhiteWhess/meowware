package wtf.whess.meowware.client.api.utilities.thread;

import wtf.whess.meowware.client.api.utilities.Utility;

public final class ThreadUtil extends Utility {
    public static void sleep(final Long millis) {
        try {
            Thread.sleep(millis);
        } catch (final Exception ignored) {}
    }
}
