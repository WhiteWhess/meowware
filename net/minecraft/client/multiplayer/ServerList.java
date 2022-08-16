package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import wtf.whess.meowware.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

public class ServerList
{
    private static final Logger LOGGER = LogManager.getLogger();

    /** The Minecraft instance. */
    private final Minecraft mc;
    private final List<ServerData> servers = Lists.newArrayList();
    private final List<ServerData> shkoloservers = Lists.newArrayList();

    public ServerList(Minecraft mcIn)
    {
        this.mc = mcIn;
        this.loadServerList();
        this.loadShkoloServerList();
    }

    /**
     * Loads a list of servers from servers.dat, by running ServerData.getServerDataFromNBTCompound on each NBT compound
     * found in the "servers" tag list.
     */
    public void loadServerList()
    {
        try
        {
            this.servers.clear();
            NBTTagCompound nbttagcompound = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));

            if (nbttagcompound == null)
            {
                return;
            }

            NBTTagList nbttaglist = nbttagcompound.getTagList("servers", 10);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                this.servers.add(ServerData.getServerDataFromNBTCompound(nbttaglist.getCompoundTagAt(i)));
            }
        }
        catch (Exception exception)
        {
            LOGGER.error("Couldn't load server list", (Throwable)exception);
        }
    }

    /**
     * Runs getNBTCompound on each ServerData instance, puts everything into a "servers" NBT list and writes it to
     * servers.dat.
     */
    public void saveServerList()
    {
        try
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (ServerData serverdata : this.servers)
            {
                nbttaglist.appendTag(serverdata.getNBTCompound());
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setTag("servers", nbttaglist);
            CompressedStreamTools.safeWrite(nbttagcompound, new File(this.mc.mcDataDir, "servers.dat"));
        }
        catch (Exception exception)
        {
            LOGGER.error("Couldn't save server list", (Throwable)exception);
        }
    }

    public void loadShkoloServerList() {
        try {
            this.getShkoloservers().clear();
            final NBTTagCompound nbttagcompound = CompressedStreamTools.read(new File(this.mc.mcDataDir, "shkoloservers.dat"));
            if (nbttagcompound == null) {
                return;
            }
            final NBTTagList nbttaglist = nbttagcompound.getTagList("shkoloservers", 10);
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                this.getShkoloservers().add(ServerData.getServerDataFromNBTCompound(nbttaglist.getCompoundTagAt(i)));
            }
        }
        catch (Exception exception) {
            ServerList.LOGGER.error("Couldn't load server list", exception);
        }
    }

    public void saveShkoloServerList() {
        try {
            final NBTTagList nbttaglist = new NBTTagList();
            for (final ServerData serverdata : this.getShkoloservers()) {
                nbttaglist.appendTag(serverdata.getNBTCompound());
            }
            final NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setTag("shkoloservers", nbttaglist);
            CompressedStreamTools.safeWrite(nbttagcompound, new File(this.mc.mcDataDir, "shkoloservers.dat"));
        }
        catch (Exception exception) {
            ServerList.LOGGER.error("Couldn't save server list", exception);
        }
    }

    public ServerData getShkoloServerData(final int index) {
        return this.getShkoloservers().get(index);
    }

    public void removeShkoloServerData(final int index) {
        this.getShkoloservers().remove(index);
    }

    public void clearShkoloServerData() {
        this.shkoloservers.clear();
    }

    public void addShkoloServerData(final ServerData server) {
        this.getShkoloservers().add(server);
    }

    public int countShkoloServers() {
        return this.getShkoloservers().size();
    }

    public void swapShkoloServers(final int pos1, final int pos2) {
        final ServerData serverdata = this.getShkoloServerData(pos1);
        this.getShkoloservers().set(pos1, this.getShkoloServerData(pos2));
        this.getShkoloservers().set(pos2, serverdata);
        this.saveShkoloServerList();
    }

    public void shkoloset(final int index, final ServerData server) {
        this.getShkoloservers().set(index, server);
    }

    public static void saveSingleShkoloServer(final ServerData server) {
        final ServerList serverlist = new ServerList(Minecraft.getMinecraft());
        serverlist.loadShkoloServerList();
        for (int i = 0; i < serverlist.countShkoloServers(); ++i) {
            final ServerData serverdata = serverlist.getShkoloServerData(i);
            if (serverdata.serverName.equals(server.serverName) && serverdata.serverIP.equals(server.serverIP)) {
                serverlist.shkoloset(i, server);
                break;
            }
        }
        serverlist.saveShkoloServerList();
    }

    public List<ServerData> getShkoloservers() {
        return this.shkoloservers;
    }

    /**
     * Gets the ServerData instance stored for the given index in the list.
     */
    public ServerData getServerData(int index)
    {
        return this.servers.get(index);
    }

    /**
     * Removes the ServerData instance stored for the given index in the list.
     */
    public void removeServerData(int index)
    {
        this.servers.remove(index);
    }

    /**
     * Adds the given ServerData instance to the list.
     */
    public void addServerData(ServerData server)
    {
        this.servers.add(server);
    }

    /**
     * Counts the number of ServerData instances in the list.
     */
    public int countServers()
    {
        return this.servers.size();
    }

    /**
     * Takes two list indexes, and swaps their order around.
     */
    public void swapServers(int pos1, int pos2)
    {
        ServerData serverdata = this.getServerData(pos1);
        this.servers.set(pos1, this.getServerData(pos2));
        this.servers.set(pos2, serverdata);
        this.saveServerList();
    }

    public void set(int index, ServerData server)
    {
        this.servers.set(index, server);
    }

    public static void saveSingleServer(ServerData server)
    {
        ServerList serverlist = new ServerList(Minecraft.getMinecraft());
        serverlist.loadServerList();

        for (int i = 0; i < serverlist.countServers(); ++i)
        {
            ServerData serverdata = serverlist.getServerData(i);

            if (serverdata.serverName.equals(server.serverName) && serverdata.serverIP.equals(server.serverIP))
            {
                serverlist.set(i, server);
                break;
            }
        }

        serverlist.saveServerList();
    }
}
