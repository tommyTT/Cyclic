package com.lothrazar.cyclicmagic.block.tileentity;
import com.lothrazar.cyclicmagic.block.BlockBaseFacing;
import com.lothrazar.cyclicmagic.block.BlockMiner;
import com.lothrazar.cyclicmagic.util.UtilParticle;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public abstract class TileEntityBaseMachine extends TileEntity {
  protected boolean isPowered() {
    return this.getWorld().isBlockPowered(this.getPos());//this.getWorld().isBlockIndirectlyGettingPowered(this.getPos()) > 0;
  }
  protected EnumFacing getCurrentFacing() {
    BlockBaseFacing b = ((BlockBaseFacing) this.getBlockType());
    EnumFacing facing;
    if (b == null || this.getWorld().getBlockState(this.getPos()) == null || b.getFacingFromState(this.getWorld().getBlockState(this.getPos())) == null)
      facing = EnumFacing.UP;
    else
      facing = b.getFacingFromState(this.getWorld().getBlockState(this.getPos()));
    return facing;
  }
  protected BlockPos getCurrentFacingPos() {
    return this.getPos().offset(this.getCurrentFacing());
  }
  protected void spawnParticlesAbove() {
    if(this.getWorld().isRemote){
      double x = this.getPos().getX() + 0.5;
      double y = this.getPos().getY() + 0.5;
      double z = this.getPos().getZ() + 0.5;
      UtilParticle.spawnParticle(this.getWorld(), EnumParticleTypes.SMOKE_NORMAL, x, y, z);
    }
  }
  //
  //  public EnumFacing getFacingSelf() {
  //    return worldObj.getBlockState(pos).getValue(BlockMiner.PROPERTYFACING).getOpposite();
  //  }
}
