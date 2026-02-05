package com.lcf.events;


import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;


public class RegisterPlayerEvent {




    public static void init(EventRegistry eventRegistry) {
        eventRegistry.registerGlobal(PlayerReadyEvent.class, RegisterPlayerEvent::onPlayerReadyEvent);
    }

    private static void onPlayerReadyEvent(PlayerReadyEvent e) {
        /*
        Player player = e.getPlayer();
        Ref<EntityStore> playerRef = e.getPlayer().getReference();
        PlayerRef ref = playerRef.getStore().getComponent(playerRef, PlayerRef.getComponentType());

        ClassSelectionPage page = new ClassSelectionPage(
                ref,
                ClassSelectionUtils.CLASS_WARRIOR
        );

        player.getPageManager().openCustomPage(playerRef, playerRef.getStore(), page);

         */
    }
}