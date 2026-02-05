package com.lcf.events;


import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.lcf.pages.ClassSelectionPage;
import com.lcf.utils.ClassSelectionUtils;


public class RegisterPlayerEvent {




    public static void init(EventRegistry eventRegistry) {
        eventRegistry.registerGlobal(PlayerReadyEvent.class, RegisterPlayerEvent::onPlayerReadyEvent);
    }

    private static void onPlayerReadyEvent(PlayerReadyEvent e) {

        Player player = e.getPlayer();
        Ref<EntityStore> playerRef = e.getPlayer().getReference();
        PlayerRef ref = playerRef.getStore().getComponent(playerRef, PlayerRef.getComponentType());

        ClassSelectionPage page = new ClassSelectionPage(
                ref,
                ClassSelectionUtils.CLASS_WARRIOR
        );

        player.getPageManager().openCustomPage(playerRef, playerRef.getStore(), page);


    }
}