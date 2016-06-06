package com.lothrazar.cyclicmagic.event;

import com.lothrazar.cyclicmagic.IHasConfig;
import com.lothrazar.cyclicmagic.ModMain;
import com.lothrazar.cyclicmagic.registry.CapabilityRegistry.IPlayerExtendedProperties;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class EventPlayerSleep implements IHasConfig{
	 
    @SubscribeEvent
    public void onBedCheck(SleepingLocationCheckEvent evt)    {
        final IPlayerExtendedProperties sleep = evt.getEntityPlayer().getCapability(ModMain.CAPABILITYSTORAGE, null);
    	
        if (sleep != null && sleep.isSleeping()){
            evt.setResult(Result.ALLOW);
        }
    }

    @SubscribeEvent
    public void onWakeUp(PlayerWakeUpEvent evt){
        final IPlayerExtendedProperties sleep = evt.getEntityPlayer().getCapability(ModMain.CAPABILITYSTORAGE, null);
    	
        if (sleep != null){
            sleep.setSleeping(false);
        }
    }

	@Override
	public void syncConfig(Configuration config) {
		// TODO Auto-generated method stub
		
	}
}