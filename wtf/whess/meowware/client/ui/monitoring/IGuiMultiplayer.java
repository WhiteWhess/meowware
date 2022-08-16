package wtf.whess.meowware.client.ui.monitoring;

import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.ServerPinger;

public interface IGuiMultiplayer {
    ServerPinger getOldServerPinger();

    ServerList getServerList();

    void setHoveringText(final String p0);

    boolean canMoveUp(final ServerListEntryNormal p0, final int p1);

    boolean canMoveDown(final ServerListEntryNormal p0, final int p1);

    void selectServer(final int p0);

    void connectToSelected();

    void moveServerUp(final ServerListEntryNormal p0, final int p1, final boolean p2);

    void moveServerDown(final ServerListEntryNormal p0, final int p1, final boolean p2);
}
