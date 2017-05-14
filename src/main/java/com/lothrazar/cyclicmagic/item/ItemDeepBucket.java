package com.lothrazar.cyclicmagic.item;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class ItemDeepBucket extends ItemFluidContainer {
  public ItemDeepBucket() {
    super(Fluid.BUCKET_VOLUME * 16);
    //      this.setContainerItem(Items.BUCKET);
  }
  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = player.getHeldItem(hand);
    FluidStack fluid = FluidUtil.getFluidContained(stack);
    if (fluid == null) {
      RayTraceResult vector = this.rayTrace(world, player, true);//true==use Liquids
      if (vector == null) { return EnumActionResult.FAIL; }
      BlockPos vectorPos = vector.getBlockPos();
      FluidActionResult fr = FluidUtil.tryPickUpFluid(stack, player, world, vectorPos, facing);
      if (fr.isSuccess()) {//cannot pickup here. maybe place
        world.notifyNeighborsOfStateChange(vectorPos, world.getBlockState(vectorPos).getBlock(), true);
        stack = fr.getResult();
        System.out.println("o!n pickup" + fr.isSuccess() + " amt is " + FluidUtil.getFluidContained(stack).amount);
      }
    }
    else {
      //fluid contained is NOT nll so good okj
      FluidActionResult fr = FluidUtil.tryPlaceFluid(player, world, pos.offset(facing), stack, fluid);
      System.out.println("place!" + fr.isSuccess());
    }
    return EnumActionResult.PASS;
  }
  //  @Override
  //  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
  //    ItemStack stackItem = playerIn.getHeldItem(hand);
  //    IFluidHandler stackFluid = stackItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
  //    if(stackFluid==null){
  //      System.out.println("WAT null capacy");
  //      return ActionResult.newResult(EnumActionResult.FAIL, stackItem);
  //    }
  //    RayTraceResult vector = this.rayTrace(worldIn, playerIn, true);//true==use Liquids
  //    if (vector == null) { return ActionResult.newResult(EnumActionResult.SUCCESS, stackItem); }
  //    BlockPos vectorPos = vector.getBlockPos();
  //    System.out.println("vector.typeOfHit" + vector.typeOfHit);
  //    if (vector.typeOfHit == RayTraceResult.Type.BLOCK) {
  //      IBlockState blockHit = worldIn.getBlockState(vectorPos);
  //      Material material = blockHit.getMaterial();
  //      if (blockHit.getBlock() instanceof IFluidBlock) {//interface for things like tanks
  //        IFluidBlock blockFluid = (IFluidBlock) blockHit.getBlock();
  //        //TODO: temperature??  .getFluid().getTemperature()
  //        //first use false meaning, test/try to fill
  //        boolean isRealDrain = false;
  //        FluidStack drained = blockFluid.drain(worldIn, vectorPos, isRealDrain);
  //        //1 bucket = 1000 mB which is millibuckets. which is this constant
  //        if (drained != null && drained.amount % Fluid.BUCKET_VOLUME == 0) {
  //          //success from draining target. dont set to air it might have been a tank or something.
  //          int amtFilled = stackFluid.fill(drained, isRealDrain);
  //          if (amtFilled == drained.amount) {
  //            //if it took all of it, both directions (drain and fill) worked
  //            //so use .drain(...,true) and .fill(...,rue)  to actually go for realz
  //            isRealDrain = true;
  //            drained = blockFluid.drain(worldIn, vectorPos, isRealDrain);
  //            if (playerIn.capabilities.isCreativeMode == false) {
  //              stackFluid.fill(drained, isRealDrain);
  //            }
  //            return ActionResult.newResult(EnumActionResult.SUCCESS, stackItem);
  //          }
  //        }
  //      }
  //      else if (material.isLiquid()) {
  //        worldIn.setBlockToAir(vectorPos);
  //        FluidStack fluidStack = null;
  //        if (material == Material.WATER) {
  //          fluidStack = new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME);
  //        }
  //        if (material == Material.LAVA) {
  //          fluidStack = new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME);
  //        }
  //        //TODO: modded fluits eh
  //        if (fluidStack != null) {
  //          if (stackFluid.fill(fluidStack, false) == fluidStack.amount) {
  //            stackFluid.fill(fluidStack, true);
  //            return ActionResult.newResult(EnumActionResult.SUCCESS, stackItem);
  //          }
  //        }
  //      }
  //      else { //not a liquid
  //        BlockPos targetPos = vector.getBlockPos().offset(vector.sideHit);
  //        if (playerIn.canPlayerEdit(targetPos, vector.sideHit, stackItem)) { //securit i guess?
  //          int amount = getAmount(stackItem);
  //          if (amount >= Fluid.BUCKET_VOLUME) {
  //            NBTTagCompound tagCompound = stackItem.getTagCompound();
  //            if (tagCompound == null) {
  //              tagCompound = new NBTTagCompound();
  //            }
  //            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(tagCompound.getCompoundTag(FluidHandlerItemStack.FLUID_NBT_KEY));
  //            if (fluidStack != null && worldIn.setBlockState(targetPos, fluidStack.getFluid().getBlock().getDefaultState(), 3)) {
  //              worldIn.notifyNeighborsOfStateChange(targetPos, fluidStack.getFluid().getBlock(), true);
  //              if (playerIn.capabilities.isCreativeMode == false) {
  //                //now drain the bucket using Fluid.BUCKET_VOLUME amt
  //                stackFluid.drain(Fluid.BUCKET_VOLUME, true);
  //                return ActionResult.newResult(EnumActionResult.SUCCESS, stackItem);
  //              }
  //            }
  //          }
  //        }
  //      }
  //    }
  //    return ActionResult.newResult(EnumActionResult.PASS, stackItem);
  //  }
  //  private int getAmount(ItemStack stackItem) {
  //    return 2000;//TODO: use fluid capability/handler/storage thing
  //  }
}
