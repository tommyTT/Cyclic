package com.lothrazar.cyclicmagic.component.fan;
import com.lothrazar.cyclicmagic.IHasRecipe;
import com.lothrazar.cyclicmagic.ModCyclic;
import com.lothrazar.cyclicmagic.block.BlockBaseFacingInventory;
import com.lothrazar.cyclicmagic.gui.ModGuiHandler;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFan extends BlockBaseFacingInventory implements IHasRecipe {
  //block rotation in json http://www.minecraftforge.net/forum/index.php?topic=32753.0
  public static final PropertyDirection FACINGALL = BlockDirectional.FACING;
  public BlockFan() {
    super(Material.ROCK, ModGuiHandler.GUI_INDEX_FAN);
    this.setHardness(3F);
    this.setResistance(5F);
    this.setSoundType(SoundType.WOOD);
    this.setTickRandomly(true);
    this.setTranslucent();
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACINGALL, EnumFacing.NORTH));
    
  }
  @Override
  public TileEntity createTileEntity(World worldIn, IBlockState state) {
    return new TileEntityFan();
  }
  @Override//overrride the BLockBaseFacing one
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
  {
    EnumFacing f = EnumFacing.getDirectionFromEntityLiving(pos, placer);
    ModCyclic.logger.info("fan "+f);
      return this.getDefaultState().withProperty(FACINGALL, f);
  }

  @Override
  public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return side == EnumFacing.DOWN;
  }
  @Override
  public IRecipe addRecipe() {
    return GameRegistry.addShapedRecipe(new ItemStack(this),
        " i ",
        "iri",
        "sis",
        'i', Items.IRON_INGOT,
        'r', Items.REPEATER,
        's', Blocks.STONE);
  }
  @Override
  protected BlockStateContainer createBlockState()
  {
      return new BlockStateContainer(this, new IProperty[] {FACINGALL});
  }
  @Override
  public int getMetaFromState(IBlockState state) {
    int i = 0;//same as dispenser
    return i | ((EnumFacing)state.getValue(FACINGALL)).getIndex();
  }
  @Override
  public IBlockState getStateFromMeta(int meta)//same as dispenser
  {
      return this.getDefaultState().withProperty(FACINGALL, EnumFacing.getFront(meta & 7));//.withProperty(TRIGGERED, Boolean.valueOf((meta & 8) > 0));
  }
}
