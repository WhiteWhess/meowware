package wtf.whess.meowware.client.ui.alt;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;

import java.io.IOException;
import java.util.Iterator;

public class GuiAltManager extends GuiScreen {
   private GuiButton login;
   private GuiButton remove;
   private GuiButton rename;
   private AltLoginThread loginThread;
   private int offset;
   public Alt selectedAlt = null;
   private String status;
   private final GuiScreen parentScreen;

   public GuiAltManager(GuiScreen parentScreen) {
      this.status = ChatFormatting.GRAY + "No alts selected";
      this.parentScreen = parentScreen;
   }

   public void actionPerformed(GuiButton button) throws IOException {
      switch(button.id) {
      case 0:
         if (this.loginThread == null) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else if (!this.loginThread.getStatus().equals(ChatFormatting.YELLOW + "Attempting to log in") && !this.loginThread.getStatus().equals(ChatFormatting.RED + "Do not hit back!" + ChatFormatting.YELLOW + " Logging in...")) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else {
            this.loginThread.setStatus(ChatFormatting.RED + "Failed to login! Please try again!" + ChatFormatting.YELLOW + " Logging in...");
         }
         break;
      case 1:
         String user = this.selectedAlt.getUsername();
         String pass = this.selectedAlt.getPassword();
         this.loginThread = new AltLoginThread(user, pass);
         this.loginThread.start();
         break;
      case 2:
         if (this.loginThread != null) {
            this.loginThread = null;
         }

         AltManager.registry.remove(this.selectedAlt);
         this.status = "§aRemoved.";
         this.selectedAlt = null;
         break;
      case 3:
         this.mc.displayGuiScreen(new GuiAddAlt(this));
         break;
      case 4:
         this.mc.displayGuiScreen(new GuiAltLogin(this));
      case 5:
      default:
         break;
      case 6:
         this.mc.displayGuiScreen(new GuiRenameAlt(this));
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      if (Mouse.hasWheel()) {
         int wheel = Mouse.getDWheel();
         if (wheel < 0) {
            this.offset += 26;
            if (this.offset < 0) {
               this.offset = 0;
            }
         } else if (wheel > 0) {
            this.offset -= 26;
            if (this.offset < 0) {
               this.offset = 0;
            }
         }
      }

      this.drawDefaultBackground();
      FontManager.montserratLight18.drawStringWithShadow("Current Username: " + this.mc.session.getUsername(), 10, 10, -1);
      StringBuilder sb2 = new StringBuilder("Account Manager - ");
      FontManager.montserratLight18.drawCentralizedStringWithShadow(sb2.append(AltManager.registry.size()).append(" alts").toString(), width / 2, 10, -1);
      FontManager.montserratLight18.drawCentralizedStringWithShadow(this.loginThread == null ? this.status : this.loginThread.getStatus(), width / 2, 20, -1);
      //RenderUtil.drawRectWithOutline1(50, 33, width - 50, height - 50, 1, -16777216, Integer.MIN_VALUE);
      GL11.glPushMatrix();
      this.prepareScissorBox(0.0F, 33.0F, (float)width, (float)(height - 50));
      GL11.glEnable(3089);
      int y2 = 38;
      Iterator var8 = AltManager.registry.iterator();

      while(true) {
         Alt alt2;
         do {
            if (!var8.hasNext()) {
               GL11.glDisable(3089);
               GL11.glPopMatrix();
               super.drawScreen(par1, par2, par3);
               if (this.selectedAlt == null) {
                  this.login.enabled = false;
                  this.remove.enabled = false;
                  this.rename.enabled = false;
               } else {
                  this.login.enabled = true;
                  this.remove.enabled = true;
                  this.rename.enabled = true;
               }

               if (Keyboard.isKeyDown(200)) {
                  this.offset -= 26;
                  if (this.offset < 0) {
                     this.offset = 0;
                  }
               } else if (Keyboard.isKeyDown(208)) {
                  this.offset += 26;
                  if (this.offset < 0) {
                     this.offset = 0;
                  }
               }

               return;
            }

            alt2 = (Alt)var8.next();
         } while(!this.isAltInArea(y2));

         String name = alt2.getMask().equals("") ? alt2.getUsername() : alt2.getMask();
         String pass = alt2.getPassword().equals("") ? "§cOffline" : alt2.getPassword().replaceAll(".", "*");
         if (alt2 == this.selectedAlt) {
            if (this.isMouseOverAlt(par1, par2, y2 - this.offset) && Mouse.isButtonDown(0)) {
               RenderUtil.drawRectWithOutline1(52, y2 - this.offset - 4, width - 52, y2 - this.offset + 20, 1, -16777216, -2142943931);
            } else if (this.isMouseOverAlt(par1, par2, y2 - this.offset)) {
               RenderUtil.drawRectWithOutline1(52, y2 - this.offset - 4, width - 52, y2 - this.offset + 20, 1, -16777216, -2142088622);
            } else {
               RenderUtil.drawRectWithOutline1(52, y2 - this.offset - 4, width - 52, y2 - this.offset + 20, 1, -16777216, -2144259791);
            }
         } else if (this.isMouseOverAlt(par1, par2, y2 - this.offset) && Mouse.isButtonDown(0)) {
            RenderUtil.drawRectWithOutline1(52, y2 - this.offset - 4, width - 52, y2 - this.offset + 20, 1, -16777216, -2146101995);
         } else if (this.isMouseOverAlt(par1, par2, y2 - this.offset)) {
            RenderUtil.drawRectWithOutline1(52, y2 - this.offset - 4, width - 52, y2 - this.offset + 20, 1, -16777216, -2145180893);
         }

         FontManager.montserratLight18.drawCentralizedStringWithShadow("    " + name, width / 2F, y2 - this.offset + 3, -1);
         FontManager.montserratLight18.drawCentralizedStringWithShadow(pass, width / 2F, y2 - this.offset + 13, 5592405);
         y2 += 26;
      }
   }

   public void initGui() {
      this.buttonList.add(new GuiButton(0, width / 2 + 4 + 50, height - 24, 100, 20, "Cancel"));
      this.login = new GuiButton(1, width / 2 - 154, height - 48, 100, 20, "Login");
      this.buttonList.add(this.login);
      this.remove = new GuiButton(2, width / 2 - 154, height - 24, 100, 20, "Remove");
      this.buttonList.add(this.remove);
      this.buttonList.add(new GuiButton(3, width / 2 + 4 + 50, height - 48, 100, 20, "Add"));
      this.buttonList.add(new GuiButton(4, width / 2 - 50, height - 48, 100, 20, "Direct Login"));
      this.rename = new GuiButton(6, width / 2 - 50, height - 24, 100, 20, "Edit");
      this.buttonList.add(this.rename);
      this.login.enabled = false;
      this.remove.enabled = false;
      this.rename.enabled = false;
   }

   private boolean isAltInArea(int y2) {
      return y2 - this.offset <= height - 50;
   }

   private boolean isMouseOverAlt(int x2, int y2, int y1) {
      return x2 >= 52 && y2 >= y1 - 4 && x2 <= width - 52 && y2 <= y1 + 20 && y2 >= 33 && x2 <= width && y2 <= height - 50;
   }

   protected void mouseClicked(int par1, int par2, int par3) throws IOException {
      if (this.offset < 0) {
         this.offset = 0;
      }

      int y2 = 38 - this.offset;
      for(Iterator var6 = AltManager.registry.iterator(); var6.hasNext(); y2 += 26) {
         Alt alt2 = (Alt)var6.next();
         if (this.isMouseOverAlt(par1, par2, y2)) {
            if (alt2 == this.selectedAlt) {
               this.actionPerformed(this.buttonList.get(1));
               return;
            }

            this.selectedAlt = alt2;
         }
      }

      try {
         super.mouseClicked(par1, par2, par3);
      } catch (IOException var8) {
         var8.printStackTrace();
      }

   }

   public void prepareScissorBox(float x2, float y2, float x22, float y22) {
      ScaledResolution scale = new ScaledResolution(this.mc);
      int factor = scale.getScaleFactor();
      GL11.glScissor((int)(x2 * (float)factor), (int)(((float)scale.getScaledHeight() - y22) * (float)factor), (int)((x22 - x2) * (float)factor), (int)((y22 - y2) * (float)factor));
   }
}
