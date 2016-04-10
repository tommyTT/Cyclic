package com.lothrazar.cyclicmagic.item;

import java.util.Set;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemMultiTool extends ItemTool{
	public static final String name = "";

	// copied from tool base classes not imported since private
	private static final Set<Block> AXE_EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder });
	private static final Set<Block> PICKAXE_EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.activator_rail, Blocks.coal_ore, Blocks.cobblestone, Blocks.detector_rail, Blocks.diamond_block, Blocks.diamond_ore, Blocks.double_stone_slab, Blocks.golden_rail, Blocks.gold_block, Blocks.gold_ore, Blocks.ice, Blocks.iron_block, Blocks.iron_ore, Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.packed_ice, Blocks.rail, Blocks.redstone_ore, Blocks.sandstone, Blocks.red_sandstone, Blocks.stone, Blocks.stone_slab });
	private static final Set<Block> SPADE_EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand });

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Iterables.concat(AXE_EFFECTIVE_ON, PICKAXE_EFFECTIVE_ON, SPADE_EFFECTIVE_ON));

    protected static float damageVsEntity = 1;
    protected static float attackSpeed = -2.8F;
	public ItemMultiTool(){

		//hardcoc
		super(damageVsEntity,attackSpeed, ToolMaterial.DIAMOND, EFFECTIVE_ON);

		this.setMaxDamage(-1);

	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass){

		return this.toolMaterial.getHarvestLevel();
	}

	/*
	@Override
	public float getDigSpeed(ItemStack stack, net.minecraft.block.state.IBlockState state){

		float digSpeed = ToolMaterial.EMERALD.getEfficiencyOnProperMaterial();
		if(state != null && state.getBlock() == Blocks.obsidian){
			digSpeed = digSpeed * 3; // from 8 to 24
		}
		return digSpeed;
	}
*/
	@Override
	public Set<String> getToolClasses(ItemStack stack){

		return Sets.newHashSet(new String[] { "pickaxe", "axe", "shovel" });
	}

	@Override
	public int getItemEnchantability(ItemStack stack){

		return ToolMaterial.GOLD.getEnchantability();
	}
}