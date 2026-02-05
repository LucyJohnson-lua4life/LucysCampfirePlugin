package com.lcf.commands;

import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemory;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetEntityCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectList;

import java.util.Optional;

public class TargetAttackCommand extends AbstractTargetEntityCommand {

    public TargetAttackCommand() {
        super("attackme", "Attacks the caster of the command.");
    }


    @Override
    protected void execute(CommandContext context,
                           ObjectList<Ref<EntityStore>> entities,  // List of entities!
                           World world,
                           Store<EntityStore> store) {

        context.sendMessage(Message.raw("trying to apply the attack!"));
        for (Ref<EntityStore> ref : entities) {

            TargetMemory targetMemory = ref.getStore().getComponent(ref, TargetMemory.getComponentType());
            if (targetMemory == null) {
                context.sendMessage(Message.raw("Target Memory is null, create new TargetMemory"));
                targetMemory = new TargetMemory(5.0F);
                store.addComponent(ref, TargetMemory.getComponentType(), targetMemory);
            }

            Optional<PlayerRef> pRef = world.getPlayerRefs()
                    .stream()
                    .filter(playerRef -> playerRef.getUuid().equals(context.sender().getUuid()))
                    .findFirst();

            if (pRef.isPresent()) {
                context.sendMessage(Message.raw("Start retrieving target reference"));
                Ref<EntityStore> target = pRef.get().getReference();
                if(target == null){
                    context.sendMessage(Message.raw("Target Reference is null"));
                    continue;
                }

                Int2FloatOpenHashMap hostiles = targetMemory.getKnownHostiles();
                if (hostiles.put(target.getIndex(), targetMemory.getRememberFor()) <= 0.0F && NPCEntity.getComponentType() != null) {
                    targetMemory.getKnownHostilesList().add(target);

                    NPCEntity entity = ref.getStore().getComponent(ref, NPCEntity.getComponentType());

                    if(entity != null && entity.getRole() != null){
                        context.sendMessage(Message.raw("Set Target State to Attack"));
                        entity.getRole().getStateSupport().setState(ref, "Attack", null, ref.getStore());
                    }

                }

            } else {
                context.sendMessage(Message.raw("Player ref is null: " + context.sender().getUuid()));
            }


        }



    }
}
