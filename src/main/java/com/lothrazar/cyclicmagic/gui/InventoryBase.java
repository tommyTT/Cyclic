package com.lothrazar.cyclicmagic.gui;
import com.lothrazar.cyclicmagic.util.UtilItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack; 
import net.minecraft.util.text.ITextComponent;

public class InventoryBase {
  public ItemStack[] inv;
  public InventoryBase(int invoSize) {
    inv = new ItemStack[invoSize];
  }
  public int getSizeInventory() {
    return this.inv.length;
  }
  public ItemStack getStackInSlot(int s) {
    return (s >= this.getSizeInventory() ? (ItemStack) UtilItemStack.EMPTY : this.inv[s]);
  }
  public String getName() {
    return "";
  }
  public boolean hasCustomName() {
    return false;
  }
  public ITextComponent getDisplayName() {
    return null;
  }
  public ItemStack removeStackFromSlot(int index) {
    ItemStack stack = getStackInSlot(index);
    setInventorySlotContents(index, UtilItemStack.EMPTY);
    return stack;
  }
  public ItemStack decrStackSize(int index, int count) {
    ItemStack stack = getStackInSlot(index);
    if (stack != UtilItemStack.EMPTY) {
      if (stack.getMaxStackSize() <= count) {
        setInventorySlotContents(index, UtilItemStack.EMPTY);
      }
      else {
        stack = stack.splitStack(count);
        if (stack.getMaxStackSize() == 0) {
          setInventorySlotContents(index, UtilItemStack.EMPTY);
        }
      }
    }
    return stack;
  }
  public void setInventorySlotContents(int index, ItemStack stack) {
    inv[index] = stack;
  }
  public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer) {
    return true;
  }
  public void openInventory(EntityPlayer player) {}
  public void closeInventory(EntityPlayer player) {}
  public int getField(int id) {
    return 0;
  }
  public void setField(int id, int value) {}
  public int getFieldCount() {
    return 0;
  }
  public void clear() {}
  public boolean isEmpty() {
    return false;
  }
}
