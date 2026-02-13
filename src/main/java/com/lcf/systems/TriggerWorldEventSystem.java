package com.lcf.systems;

import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.system.tick.TickingSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.LivingEntity;
import com.hypixel.hytale.server.core.entity.StatModifiersManager;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;


import javax.annotation.Nonnull;


public class TriggerWorldEventSystem extends TickingSystem<EntityStore>{





    private long lastExecutionTime;

    @Override
    public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
        long currentTime = System.currentTimeMillis();


        if (currentTime - lastExecutionTime >= 10000) { // 10 seconds in milliseconds
            World world = store.getExternalData().getWorld();

            world.getPlayerRefs().forEach(playerRef -> {
                Player player = store.getComponent(playerRef.getReference(), Player.getComponentType());
                //player.sendMessage(Message.raw("Your world: " + world.getName()));

                //StatModifiersManager

            });
            lastExecutionTime = currentTime;
        }





    }
}
