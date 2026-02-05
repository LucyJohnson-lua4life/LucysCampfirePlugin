package com.lcf.actions;


import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.holder.AssetHolder;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;

import javax.annotation.Nonnull;

public class BuilderActionOpenFightDialog extends BuilderActionBase {
    protected final AssetHolder shopId = new AssetHolder();

    @Nonnull
    @Override
    public String getShortDescription() {
        return "Open the barter shop UI for the current player";
    }

    @Nonnull
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    @Nonnull
    public Action build(@Nonnull BuilderSupport builderSupport) {
        return new ActionOpenFightDialog(this, builderSupport);
    }

    @Nonnull
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }


}
