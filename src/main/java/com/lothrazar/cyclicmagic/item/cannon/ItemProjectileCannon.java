/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.lothrazar.cyclicmagic.item.cannon;

import com.lothrazar.cyclicmagic.IHasRecipe;
import com.lothrazar.cyclicmagic.core.item.BaseTool;
import com.lothrazar.cyclicmagic.core.registry.RecipeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemProjectileCannon extends BaseTool implements IHasRecipe {

  public ItemProjectileCannon() {
    super(1000);
  }

  public void createBullet(World world, EntityPlayer player) {
    EntityGolemLaser s = new EntityGolemLaser(world);
    s.initCustom(player.posX, player.posY + 1.6, player.posZ, player.getLookVec().x * 0.5, player.getLookVec().y * 0.5, player.getLookVec().z * 0.5, 4.0f, player.getUniqueID());
    if (world.isRemote == false)
      world.spawnEntity(s);
  }

  @Override
  public IRecipe addRecipe() {
    return RecipeRegistry.addShapedOreRecipe(new ItemStack(this),
        " cc",
        "fbc",
        "ff ",
        'c', Items.FIRE_CHARGE,
        'b', new ItemStack(Items.BLAZE_POWDER),
        'f', new ItemStack(Items.FLINT));
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    this.createBullet(world, player);
    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItemMainhand());
  }

  @Override
  public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    return getRepairItem().isItemEqualIgnoreDurability(toRepair);
  }
}