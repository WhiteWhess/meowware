package net.minecraft.client.resources;

import wtf.whess.meowware.Minecraft;
import net.minecraft.client.gui.GuiScreenResourcePacks;

public class ResourcePackListEntryDefault extends ResourcePackListEntryServer
{
    public ResourcePackListEntryDefault(GuiScreenResourcePacks resourcePacksGUIIn)
    {
        super(resourcePacksGUIIn, Minecraft.getMinecraft().getResourcePackRepository().rprDefaultResourcePack);
    }

    protected String getResourcePackName()
    {
        return "Default";
    }

    public boolean isServerPack()
    {
        return false;
    }
}
