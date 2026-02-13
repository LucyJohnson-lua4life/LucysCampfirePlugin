package com.lcf.actions;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import com.lcf.pages.AmbienceInfoPage;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;



public class ActionOpenAmbienceInfoDialog extends ActionBase {

    private final List<String> randomDialogs = List.of("People call the Trorks monsters, but I’ve traded with them once or twice. They laugh loud, drink harder, and fight because it’s all they know. When they build camps, the land suffers—yet none of them ever seem pleased about it. It’s like they’re trapped in a culture older than their guilt."
            ,"Before towns, before roads… the world was quiet. The old scriptures say only animals and Kweebecs walked these lands. No kings, no wars worth naming. Sometimes I wonder if intelligence itself was a curse planted here, like a seed that grew too fast for its own roots."
            ,"Mainclain. A myth, a madman, or the greatest mind that ever lived. If half the stories are true, he didn’t just study intelligence—he built it. Scholars argue day and night, but none can explain how so many races share such similar thought. Coincidence feels… unlikely."
            , "They say Aeterox smiles like a friend and disappears like smoke. A pirate who charts ruins instead of plundering gold. Finding one of Mainclain’s early Intelligence Cores should’ve made him famous forever—yet he avoids every banner and crown. Makes you wonder what he learned that scared him."
    , "Some scholars think Mainclain never existed—that the Intelligence Cores evolved naturally. But I’ve seen one fragment myself. Perfect angles. Purposeful design. Nature doesn’t carve thoughts into crystal. Someone, or something, wanted minds to bloom here."
    , "Aeterox once mapped a ruin so old the walls had grown roots thicker than pillars. Inside, nothing but silence and a faint hum, like a sleeping thought. He never revealed what powered it. Only said, ‘Some doors should stay curious, not open.’"
    , "Every Kweebec sapling is named. Every fallen tree is mourned. They believe they were shaped with the forests, not placed upon them. When Trorks build forges, it’s not destruction they fear—it’s forgetting. A land without memory cannot protect its children.");
    private final Random rand = new Random();

    public ActionOpenAmbienceInfoDialog(@Nonnull BuilderActionOpenAmbienceInfoDialog builder, @Nonnull BuilderSupport support) {
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

                    playerComponent.getPageManager().openCustomPage(ref, store, new AmbienceInfoPage(playerRefComponent, ref, "Youre talking to a Adventurer...",randomDialog));
                    //context.sendMessage(Message.raw("trying to apply the attack!"));

                    return true;
                }
            }
        }
    }
}

