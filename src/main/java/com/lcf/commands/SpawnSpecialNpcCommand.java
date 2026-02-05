package com.lcf.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import it.unimi.dsi.fastutil.Pair;

import javax.annotation.Nonnull;

public class SpawnSpecialNpcCommand extends AbstractPlayerCommand {
    public SpawnSpecialNpcCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
        super(name, description, requiresConfirmation);
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {

        TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
        //Vector3d playerPosition = transformComponent.getPosition();
        //Vector3d spawnPosition = new Vector3d(playerPosition.getX() + 10, playerPosition.getY(), playerPosition.getZ());
        Vector3d spawnPosition = new Vector3d(-896.142,118.000,408.477);

        Pair<Ref<EntityStore>, INonPlayerCharacter> pair = NPCPlugin.get().spawnNPC(store, "Static_Human", (String)null, spawnPosition, new Vector3f(0,0,0));
        //Pair<Ref<EntityStore>, INonPlayerCharacter> other = NPCPlugin.get().spawnNPC(store, "Base_Attacker_Skeleton_Knight", (String)null, spawnPosition, new Vector3f(0,0,0));
        //Interactions interactions = store.getComponent(pair.first(), Interactions.getComponentType());

        /*
        if (interactions == null) {
            commandContext.sendMessage(Message.raw("Interactions is null! Create new one"));
            interactions = new Interactions();
            store.putComponent(pair.first(), Interactions.getComponentType(), interactions);
            interactions.setInteractionId(InteractionType.Use, "NPC_Dialog");
            interactions.setInteractionHint("Press F to talk");
        }else{
            commandContext.sendMessage(Message.raw("Modify interaction"));
            interactions.setInteractionId(InteractionType.Use, "NPC_Dialog");
            interactions.setInteractionHint("Press F to talk");
        }

         */


    }


}
