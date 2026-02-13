package com.lcf.pages;


import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class AmbienceInfoPage extends InteractiveCustomUIPage<AmbienceInfoPage.DialogEventData> {

    private final String dialogText;
    private final String dialogHeading;

    public static class DialogEventData {

        public String action;

        public static final BuilderCodec<DialogEventData> CODEC =
                BuilderCodec.builder(DialogEventData.class, DialogEventData::new)
                        .append(new KeyedCodec<>("Action", Codec.STRING), (DialogEventData o, String v) -> o.action = v, (DialogEventData o) -> o.action)
                        .add()
                        .build();
    }

    public AmbienceInfoPage(@Nonnull PlayerRef playerRef, @Nonnull Ref<EntityStore> fighterRef, String dialogHeading, String dialogText) {
        super(playerRef, CustomPageLifetime.CantClose, DialogEventData.CODEC);
        this.dialogHeading = dialogHeading;
        this.dialogText = dialogText;
    }


    @Override
    public void build(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull UICommandBuilder commandBuilder,
            @Nonnull UIEventBuilder eventBuilder,
            @Nonnull Store<EntityStore> store
    ) {

        commandBuilder.append("Pages/AmbienceInfoPage.ui");

        commandBuilder.set("#Headline.Text", this.dialogHeading);
        commandBuilder.set("#Message.Text", this.dialogText);

        eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#LeaveButton");

        eventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#LeaveButton",
                new EventData().append("Action", "Leave"));

    }


    @Override
    public void handleDataEvent(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull Store<EntityStore> store,
            @Nonnull DialogEventData data
    ) {
        // Get the Player component to access PageManager
        Player player = (Player) store.getComponent(ref, Player.getComponentType());


        if("Leave".equals(data.action)){
            player.getPageManager().setPage(ref, store, Page.None);
        }


    }
}
