package com.lothrazar.cyclicmagic.block.battery;

import com.lothrazar.cyclicmagic.core.block.TileEntityBaseMachineInvo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityBattery extends TileEntityBaseMachineInvo implements ITickable {

  private static final double PCT_UPDATE_ON_TICK = 0.01;
  //for reference RFT powercells: 250k, 1M, 4M, ; gadgetry 480k
  // int dynamics is 1M
  public static final int PER_TICK = 256;
  public static final int CAPACITY = 1000000;
  //  private static final int TRANSFER_ENERGY_PER_TICK = PER_TICK * 4;

  public TileEntityBattery() {
    super(1);
    this.initEnergy(0, CAPACITY);
    this.setSlotsForBoth();
  }

  @Override
  public void update() {
    if (isValid() == false) {
      return;
    }
    if (world.rand.nextDouble() < PCT_UPDATE_ON_TICK) {
      //push client updates for energy bar but not every tick 
      world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }
    //attept to auto export power to nbrs 
    ItemStack toCharge = this.getStackInSlot(0);
    if (toCharge.hasCapability(CapabilityEnergy.ENERGY, null)) {
      IEnergyStorage energyItemStack = toCharge.getCapability(CapabilityEnergy.ENERGY, null);
      if (energyItemStack.canReceive() && this.energyStorage.canExtract()) {
        int canRecieve = energyItemStack.receiveEnergy(PER_TICK, true);
        int canExtract = this.energyStorage.extractEnergy(PER_TICK, true);
        int actual = Math.min(canRecieve, canExtract);
        int toExtract = energyItemStack.receiveEnergy(actual, false);
        this.energyStorage.extractEnergy(toExtract, false);
      }
    }
  }
}
