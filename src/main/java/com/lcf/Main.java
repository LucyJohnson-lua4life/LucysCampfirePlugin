package com.lcf;

import com.lcf.actions.BuilderActionOpenFightDialog;
import com.lcf.commands.ExampleCommand;
import com.lcf.commands.HealEntityCommand;
import com.lcf.commands.SpawnSpecialNpcCommand;
import com.lcf.commands.TargetAttackCommand;
import com.lcf.systems.CustomNPCDeathSystem;
import com.lcf.systems.TriggerWorldEventSystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.npc.NPCPlugin;

public class Main extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public Main(JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Hello from %s version %s", this.getName(), this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {


        super.setup();
        var registry = getEntityStoreRegistry();
        this.getCommandRegistry().registerCommand(new ExampleCommand(this.getName(), this.getManifest().getVersion().toString()));
        this.getCommandRegistry().registerCommand(new TargetAttackCommand());
        this.getCommandRegistry().registerCommand(new HealEntityCommand());
        this.getCommandRegistry().registerCommand(new SpawnSpecialNpcCommand("sn", "Example command", false));
        //OpenCustomUIInteraction.registerCustomPageSupplier(this, NPCDialogInteractionSupplier.class, "NPCDialog", new NPCDialogInteractionSupplier());
        registry.registerSystem(new TriggerWorldEventSystem());
        registry.registerSystem(new CustomNPCDeathSystem());
        NPCPlugin.get().registerCoreComponentType("NPCDialog", BuilderActionOpenFightDialog::new);
        //Model citizenModel = new Model.ModelReference(citizen.getModelId(), scale, randomAttachmentIds).toModel();
        /*
        if(NPCPlugin.get() != null){
            NPCPlugin.get().registerCoreComponentType("NPCDialog", BuilderActionOpenFightDialog::new);
        }
*/



    }




}
