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
package com.lothrazar.cyclicmagic.block.fire;

import com.lothrazar.cyclicmagic.core.util.Const;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFireFrost extends BlockFireBase {

  private static final int FIRESECONDS = 3;

  public BlockFireFrost() {
    super();
  }

  @Override
  public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    if (!worldIn.isRemote && entityIn instanceof EntityLivingBase) {
      EntityLivingBase e = ((EntityLivingBase) entityIn);
      if (!e.isPotionActive(MobEffects.SLOWNESS)) {
        if (worldIn.rand.nextDouble() < 0.35) {
          e.setFire(FIRESECONDS);
        }
        //        MobEffects.
        //        e.addPotionEffect(new PotionEffect(PotionEffectRegistry.SNOW, Const.TICKS_PER_SEC * 10, Const.Potions.I));
        e.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, Const.TICKS_PER_SEC * 10, Const.Potions.II));
        e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, Const.TICKS_PER_SEC * 10, Const.Potions.II));
      }
    }
    super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
  }
}
