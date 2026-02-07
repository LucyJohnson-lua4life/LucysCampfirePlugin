package com.lcf.actions;

import com.lcf.pages.FightDialogPage;
import com.lcf.pages.HelloWorldPage;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class ActionOpenFightDialog extends ActionBase {

    private final List<String> randomDialogs = List.of("You stumble upon a lone warrior standing amidst the misty clearing, armor dented and bloodied. His sword rests heavily in his hands, eyes scanning the treeline. He notices you, grip tightening—friend or foe, it’s unclear, but the tension in the air is unmistakable."
    ,"You find a lone fighter kneeling by a dying campfire, sharpening his sword with slow, practiced strokes. He looks up as you approach, eyes hard from battle, yet weary. The fire crackles, the moment heavy with unspoken tension."
    ,"You encounter a solitary warrior on the battlefield’s outskirts, surrounded by fallen foes. His blade drips crimson as he turns toward you, expression unreadable. Whether he sees you as the next enemy remains uncertain."
    , "A grim warrior emerges from the fog, armor marked by fresh battle. He halts when he sees you, weapon angled low but ready. The fog swirls between you, blurring friend and foe in the uncertain light.");
    private final Random rand = new Random();

    public ActionOpenFightDialog(@Nonnull BuilderActionOpenFightDialog builder, @Nonnull BuilderSupport support) {
        super(builder);

    }

    @Override
    public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        return super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null;
    }

    @Override
    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        super.execute(ref, role, sensorInfo, dt, store);
        Ref<EntityStore> playerReference = role.getStateSupport().getInteractionIterationTarget();
        if (playerReference == null) {
            return false;
        } else {
            PlayerRef playerRefComponent = store.getComponent(playerReference, PlayerRef.getComponentType());
            if (playerRefComponent == null) {
                return false;
            } else {
                Player playerComponent = store.getComponent(playerReference, Player.getComponentType());
                if (playerComponent == null) {
                    return false;
                } else {
                    int dialogIndex = rand.nextInt(randomDialogs.size());
                    String randomDialog =  randomDialogs.get(dialogIndex);

                    playerComponent.getPageManager().openCustomPage(ref, store, new FightDialogPage(playerRefComponent, ref, "You encountered a lone Warrior...",randomDialog));
                    //context.sendMessage(Message.raw("trying to apply the attack!"));

                    return true;
                }
            }
        }
    }
}

