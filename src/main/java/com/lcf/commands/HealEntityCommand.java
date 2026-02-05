package com.lcf.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetEntityCommand;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import it.unimi.dsi.fastutil.objects.ObjectList;

public class HealEntityCommand extends AbstractTargetEntityCommand {

    public HealEntityCommand() {
        super("healentity", "Heals entities you're looking at");
    }

    @Override
    protected void execute(CommandContext context,
                           ObjectList<Ref<EntityStore>> entities,  // List of entities!
                           World world,
                           Store<EntityStore> store) {

        int healed = 0;

        for (Ref<EntityStore> ref : entities) {
            EntityStatMap stats = store.getComponent(ref, EntityStatMap.getComponentType());
            if (stats == null) continue;

            int healthIdx = DefaultEntityStatTypes.getHealth();
            EntityStatValue health = stats.get(healthIdx);
            if (health == null) continue;

            stats.addStatValue(healthIdx, health.getMax() - health.get());
            healed++;
        }

        context.sendMessage(Message.raw("Healed " + healed + " entities!"));
    }
}