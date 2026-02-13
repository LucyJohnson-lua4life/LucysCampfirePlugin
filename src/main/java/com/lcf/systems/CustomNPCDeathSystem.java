package com.lcf.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.npc.systems.NPCDeathSystems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomNPCDeathSystem extends DeathSystems.OnDeathSystem {



    @Override
    public void onComponentAdded(@NotNull Ref<EntityStore> ref, @NotNull DeathComponent deathComponent, @NotNull Store<EntityStore> store, @NotNull CommandBuffer<EntityStore> commandBuffer) {


        World world = store.getExternalData().getWorld();


        world.getPlayerRefs().forEach(playerRef -> {
            Player player = store.getComponent(playerRef.getReference(), Player.getComponentType());
            //player.sendMessage(Message.raw("Someone Died!"));
            NPCEntity npc = store.getComponent(ref, NPCEntity.getComponentType());
            if(npc != null){
                String roleName = npc.getRole().getRoleName();
                if(roleName != null){
                    //player.sendMessage(Message.raw("Death Event triggered!: " + roleName));
                }
            }

        });



    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return DeathComponent.getComponentType();
    }
}
