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
import com.lcf.utils.ClassSelectionUtils;


import javax.annotation.Nonnull;

/**
 * StyledDialogPage - A dialog with buttons that can be clicked.
 *
 * EXTENDS: InteractiveCustomUIPage<DialogEventData>
 *   - Use this when you need to handle events (button clicks, inputs, etc.)
 *   - Generic parameter <DialogEventData> defines what data we receive from events
 *
 * LIFETIME: CanDismissOrCloseThroughInteraction
 *   - Player can press ESC to close
 *   - Or the page closes when certain interactions happen
 *
 * This page demonstrates:
 *   1. Event binding with eventBuilder.addEventBinding()
 *   2. Handling events in handleDataEvent()
 *   3. Closing the page programmatically
 */
public class ClassSelectionPage extends InteractiveCustomUIPage<ClassSelectionPage.DialogEventData> {

    private final String backgroundName;



    /**
     * EventData class - defines what data we receive when events fire.
     *
     * In this simple case, we don't need any data - we just want to know
     * that a button was clicked. So the class is empty.
     *
     * The CODEC is required to serialize/deserialize the data.
     * For empty data, just use an empty builder.
     */
    public static class DialogEventData {

        public String action;

        public static final BuilderCodec<DialogEventData> CODEC =
                BuilderCodec.builder(DialogEventData.class, DialogEventData::new)
                        .append(new KeyedCodec<>("Action", Codec.STRING), (DialogEventData o, String v) -> o.action = v, (DialogEventData o) -> o.action)
                        .add()
                        .build();
    }

    /**
     * Constructor.
     *
     * @param playerRef Reference to the player
     */
    public ClassSelectionPage(@Nonnull PlayerRef playerRef, String backgroundName) {
        // InteractiveCustomUIPage constructor takes:
        //   - playerRef: Which player sees this UI
        //   - lifetime: When can the UI be closed
        //   - codec: How to deserialize event data (DialogEventData.CODEC)
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, DialogEventData.CODEC);
        this.backgroundName = backgroundName;
    }

    /**
     * Build the UI and bind events.
     *
     * This is called once when the page opens.
     *
     * @param ref            Entity reference (for accessing components)
     * @param commandBuilder Builder for UI commands (append, set, clear)
     * @param eventBuilder   Builder for event bindings (button clicks, etc.)
     * @param store          Entity store (for accessing components)
     */
    @Override
    public void build(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull UICommandBuilder commandBuilder,
            @Nonnull UIEventBuilder eventBuilder,
            @Nonnull Store<EntityStore> store
    ) {
        // Load the UI layout
        commandBuilder.append("Pages/ClassSelectionPage.ui");

        // Set dynamic text values
        //commandBuilder.set("#Headline.Text", headline);
        commandBuilder.set("#BackgroundName.Text", backgroundName);

        // Bind button click events
        // CustomUIEventBindingType.Activating = "when this element is clicked"
        // The selector "#ActionButton" finds the button with that ID
        //
        // When clicked, handleDataEvent() will be called with an empty DialogEventData
        eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#SelectButton");
        eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#NextButton");
        eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#PreviousButton");

        eventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#SelectButton",
                new EventData().append("Action", "Select"));

        eventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#PreviousButton",
                new EventData().append("Action", "Previous"));


        eventBuilder.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#NextButton",
                new EventData().append("Action", "Next"));



    }

    /**
     * Handle events from the UI.
     *
     * Called when any bound event fires (button click, etc.)
     *
     * @param ref   Entity reference
     * @param store Entity store
     * @param data  The event data (empty in this case)
     */
    @Override
    public void handleDataEvent(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull Store<EntityStore> store,
            @Nonnull DialogEventData data
    ) {
        // Get the Player component to access PageManager
        Player player = (Player) store.getComponent(ref, Player.getComponentType());


        if ("Next".equals(data.action)) {

            String nextBackground = ClassSelectionUtils.getNextClass(this.backgroundName);

            ClassSelectionPage page = new ClassSelectionPage(
                    playerRef,
                    nextBackground
            );
            player.getPageManager().openCustomPage(ref, store, page);

        }
        else if("Previous".equals(data.action)){
            String previousBackground = ClassSelectionUtils.getNextClass(this.backgroundName);
            ClassSelectionPage page = new ClassSelectionPage(
                    playerRef,
                    previousBackground
            );
            player.getPageManager().openCustomPage(ref, store, page);
        }
        else if("Select".equals(data.action)){
            player.setInventory(ClassSelectionUtils.classInventoryMapping.get(this.backgroundName));
            player.getPageManager().setPage(ref, store, Page.None);
        }



    }
}