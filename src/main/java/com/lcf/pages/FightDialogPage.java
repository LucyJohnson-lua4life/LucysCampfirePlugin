package com.lcf.pages;

import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemory;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.npc.role.Role;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;


import javax.annotation.Nonnull;
import java.util.Map;


public class FightDialogPage extends InteractiveCustomUIPage<FightDialogPage.DialogEventData> {

    private final String dialogText;
    private final String dialogHeading;
    private Ref<EntityStore> fighterRef;
    private double searchTime = 0;
    private boolean searchForEnemy = false;

    private final Map<String, String> roleToCombatActionMapping = Map.of("Lone_Warrior", "Chase",
            "Lone_Trork_Gladiator", "Chase",
            "Intelligent_Human", "Combat");
    private final Map<String, String> roleToSearchActionMapping = Map.of("Lone_Warrior", "Search",
            "Lone_Trork_Gladiator", "Search",
            "Intelligent_Human", "Search.Confused");


    public static class DialogEventData {

        public String action;

        public static final BuilderCodec<DialogEventData> CODEC =
                BuilderCodec.builder(DialogEventData.class, DialogEventData::new)
                        .append(new KeyedCodec<>("Action", Codec.STRING), (DialogEventData o, String v) -> o.action = v, (DialogEventData o) -> o.action)
                        .add()
                        .build();
    }


    public FightDialogPage(@Nonnull PlayerRef playerRef, @Nonnull Ref<EntityStore> fighterRef, String dialogHeading, String dialogText) {
        super(playerRef, CustomPageLifetime.CantClose, DialogEventData.CODEC);
        this.dialogHeading = dialogHeading;
        this.dialogText = dialogText;
        this.fighterRef = fighterRef;
    }


    @Override
    public void build(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull UICommandBuilder commandBuilder,
            @Nonnull UIEventBuilder eventBuilder,
            @Nonnull Store<EntityStore> store
    ) {

        commandBuilder.append("Pages/FightDialogPage.ui");

        commandBuilder.set("#Headline.Text", this.dialogHeading);
        commandBuilder.set("#Message.Text", this.dialogText);

        eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#FightButton");
        eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#LeaveButton");

        eventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#FightButton",
                new EventData().append("Action", "Fight"));
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

        if ("Fight".equals(data.action)) {
            TargetMemory targetMemory = this.fighterRef.getStore().getComponent(this.fighterRef, TargetMemory.getComponentType());
            if (targetMemory == null) {
                player.sendMessage(Message.raw("Target Memory is null, create new TargetMemory"));
                targetMemory = new TargetMemory(60.0F);
                store.addComponent(this.fighterRef, TargetMemory.getComponentType(), targetMemory);
            }

            Int2FloatOpenHashMap hostiles = targetMemory.getKnownHostiles();
            if (hostiles.put(ref.getIndex(), targetMemory.getRememberFor()) <= 0.0F && NPCEntity.getComponentType() != null) {
                player.sendMessage(Message.raw("Putting hostile into the list"));
                targetMemory.getKnownHostilesList().add(ref);
            }
            NPCEntity fighterEntity = this.fighterRef.getStore().getComponent(this.fighterRef, NPCEntity.getComponentType());


            if(fighterEntity != null && fighterEntity.getRole() != null){
                player.sendMessage(Message.raw("Set Target State to Attack"));

                fighterEntity.getRole().addDeferredAction((@Nonnull Ref<EntityStore> fighterRef, @Nonnull Role fighterRole, double dt, @Nonnull Store<EntityStore> fighterStore) -> {
                    this.searchTime += dt;
                    if(!this.searchForEnemy){
                        fighterEntity.getRole().getStateSupport().setState(this.fighterRef, roleToSearchActionMapping.get(fighterEntity.getRoleName()), null, this.fighterRef.getStore());
                        player.sendMessage(Message.raw("Change to state: " + roleToSearchActionMapping.get(fighterEntity.getRoleName())));
                        this.searchForEnemy = true;
                    }
                    if(this.searchTime > 1.0){
                        player.sendMessage(Message.raw("Change to state: " + roleToCombatActionMapping.get(fighterEntity.getRoleName())));
                        fighterEntity.getRole().getStateSupport().setState(this.fighterRef, roleToCombatActionMapping.get(fighterEntity.getRoleName()), null, this.fighterRef.getStore());
                        return true;
                    }
//                    player.sendMessage(Message.raw("Time passed: " + this.searchTime));
                    return false;
                });



            }
            player.getPageManager().setPage(ref, store, Page.None);
        }

        else if("Leave".equals(data.action)){
            player.getPageManager().setPage(ref, store, Page.None);
        }



    }
}