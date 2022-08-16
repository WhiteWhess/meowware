package wtf.whess.meowware.client.ui.alt;

import wtf.whess.meowware.Minecraft;
import net.minecraft.util.Session;

public class AltHelper {
   private final Minecraft mc = Minecraft.getMinecraft();

   public AltHelper(Minecraft mc) {
   }

   public static void setusername(String username) {
      Minecraft.getMinecraft().session = new Session(username, "", "", "mojang");
   }
}
