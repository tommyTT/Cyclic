package com.lothrazar.cyclicmagic.block.pump;

import java.util.Map;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.lothrazar.cyclicmagic.block.cable.BlockCableBase.EnumConnectType;
import com.lothrazar.cyclicmagic.core.block.BlockBaseFacingOmni;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;

public abstract class BlockPump extends BlockBaseFacingOmni {

  private static final double SIZE = 0.875D;
  protected static final AxisAlignedBB AABB = new AxisAlignedBB(1 - SIZE, 1 - SIZE, 1 - SIZE, SIZE, SIZE, SIZE);
  /**
   * Virtual properties used for the multipart cable model and determining the presence of adjacent inventories
   */
  protected static final Map<EnumFacing, IUnlistedProperty<EnumConnectType>> PROPERTIES = Maps.newEnumMap(
      new ImmutableMap.Builder<EnumFacing, IUnlistedProperty<EnumConnectType>>()
          .put(EnumFacing.DOWN, Properties.toUnlisted(PropertyEnum.create("down", EnumConnectType.class)))
          .put(EnumFacing.UP, Properties.toUnlisted(PropertyEnum.create("up", EnumConnectType.class)))
          .put(EnumFacing.NORTH, Properties.toUnlisted(PropertyEnum.create("north", EnumConnectType.class)))
          .put(EnumFacing.SOUTH, Properties.toUnlisted(PropertyEnum.create("south", EnumConnectType.class)))
          .put(EnumFacing.WEST, Properties.toUnlisted(PropertyEnum.create("west", EnumConnectType.class)))
          .put(EnumFacing.EAST, Properties.toUnlisted(PropertyEnum.create("east", EnumConnectType.class)))
          .build());
  public BlockPump() {
    super(Material.ROCK);
    this.setHardness(3F);
    this.setResistance(3F);
    this.setHarvestLevel("pickaxe", 1);
    this.setTranslucent();
    this.placeType = PlacementType.SIDE_BLOCK;
  }

  private boolean itemTransport = false;
  private boolean fluidTransport = false;
  private boolean powerTransport = false;

  public void setItemTransport() {
    this.itemTransport = true;
  }

  public void setFluidTransport() {
    this.fluidTransport = true;
  }

  public void setPowerTransport() {
    this.powerTransport = true;
  }
  @Override
  public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    if (side == EnumFacing.UP || side == EnumFacing.DOWN)
      return false;//allows, for example, you to open chest that is directly below
    return true;
  }
  @Override
  public IExtendedBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
    IExtendedBlockState ext = (IExtendedBlockState) state;
    //  TileEntity te = world.getTileEntity(pos);
    // if (te instanceof MyTE) {
    for (EnumFacing val : EnumFacing.values())//TODO: get real here
    {
      IUnlistedProperty<EnumConnectType> property = PROPERTIES.get(val);
      ext = ext.withProperty(property, EnumConnectType.NONE);
    }
    //}
    return ext;
  }

  @Override
  protected BlockStateContainer createBlockState() {
    BlockStateContainer.Builder builder = new BlockStateContainer.Builder(this);
    //listed properties first before unlisted
    builder.add(BlockDirectional.FACING);
    for (IUnlistedProperty<EnumConnectType> property : PROPERTIES.values()) {
      builder.add(property);
    }
    return builder.build();
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return AABB;
  }
  //
  //  @Override
  //  public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos origin) {
  //    BlockPos pos = new BlockPos(origin);
  //    TileEntityCableBase cableHere = null;// (TileEntityCableBase) world.getTileEntity(pos);
  //    for (EnumFacing side : EnumFacing.VALUES) {
  //      PropertyEnum<EnumConnectType> property = PROPERTIES.get(side);
  //      if (cableHere != null && cableHere.getBlacklist(side)) {
  //        //im blocked off, so i cant connect to you
  //        state = state.withProperty(property, EnumConnectType.BLOCKED);
  //        continue;
  //      }
  //      pos = origin.offset(side);
  //      state = state.withProperty(property, EnumConnectType.NONE);
  //      TileEntity tileTarget = world.getTileEntity(pos);
  //      TileEntityCableBase tileCable = null;
  //      if (tileTarget != null && tileTarget instanceof TileEntityCableBase) {
  //        tileCable = (TileEntityCableBase) tileTarget;
  //      }
  //      if (tileCable != null && tileCable.getBlacklist(side.getOpposite())) {
  //        //you are blocked on your facing side, so i wont push out to you
  //        continue;
  //      }
  //      if (this.powerTransport) {
  //        if (tileCable != null && tileCable.isEnergyPipe()) {
  //          state = state.withProperty(property, EnumConnectType.CABLE);
  //        }
  //        if (tileTarget != null &&
  //            tileTarget.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite())) {
  //          state = state.withProperty(property, EnumConnectType.CABLE);
  //        }
  //      }
  //      if (this.itemTransport) {
  //        if (tileCable != null && tileCable.isItemPipe()) {
  //          state = state.withProperty(property, EnumConnectType.INVENTORY);
  //        }
  //        if (tileTarget != null &&
  //            tileTarget.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite())) {
  //          state = state.withProperty(property, EnumConnectType.INVENTORY);
  //        }
  //      }
  //      if (this.fluidTransport) {
  //        if (tileCable != null && tileCable.isFluidPipe()) {
  //          state = state.withProperty(property, EnumConnectType.CABLE);
  //        }
  //        // getFluidHandler uses fluid capability and other things
  //        // CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
  //        //        if (world instanceof World && FluidUtil.getFluidHandler((World) world, pos, side.getOpposite()) != null) {
  //        if (tileTarget != null &&
  //            tileTarget.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite())) {
  //          state = state.withProperty(property, EnumConnectType.INVENTORY);
  //        }
  //      }
  //    }
  //    return super.getActualState(state, world, origin);
  //  }
}
