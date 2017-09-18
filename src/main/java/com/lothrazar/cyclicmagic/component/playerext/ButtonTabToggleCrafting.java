package com.lothrazar.cyclicmagic.component.playerext;
import com.lothrazar.cyclicmagic.ModCyclic;
import com.lothrazar.cyclicmagic.component.playerext.storage.GuiPlayerExtended;
import com.lothrazar.cyclicmagic.gui.base.GuiButtonTooltip;
import com.lothrazar.cyclicmagic.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ButtonTabToggleCrafting extends GuiButtonTooltip {
  private GuiScreen gui;
  public ButtonTabToggleCrafting(GuiScreen g, int x, int y) {
    super(256, x, y, 15, 10, "");
    gui = g;
    if (ClientProxy.keyExtraCraftin != null && ClientProxy.keyExtraCraftin.getDisplayName() != null &&
        ClientProxy.keyExtraCraftin.getDisplayName().equals("NONE") == false) {
      this.displayString = ClientProxy.keyExtraCraftin.getDisplayName();
    }
    else {
      this.displayString = "C";//the legacy one. in case someone is just running with the key unbound
    }
  }
  @Override
  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
    boolean pressed = super.mousePressed(mc, mouseX, mouseY);
    if (pressed) {
      if (this.gui instanceof GuiInventory || this.gui instanceof GuiPlayerExtended) {
        ModCyclic.network.sendToServer(new PacketOpenFakeWorkbench());
      }
      else {//if (this.gui instanceof GuiPlayerExtended || this.gui instanceof GuiCrafting) {
        this.gui.mc.displayGuiScreen(new GuiInventory(gui.mc.player));
        ModCyclic.network.sendToServer(new PacketOpenNormalInventory(this.gui.mc.player));
      }
    }
    return pressed;
  }
}
