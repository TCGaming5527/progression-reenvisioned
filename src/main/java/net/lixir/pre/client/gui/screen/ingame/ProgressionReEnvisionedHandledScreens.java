package net.lixir.pre.client.gui.screen.ingame;

import net.lixir.pre.common.init.PREScreenHandlerTypes;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ProgressionReEnvisionedHandledScreens {
    public static void initialize() {
        HandledScreens.register(
                PREScreenHandlerTypes.INDUCTION_FURNACE,
                InductionFurnaceScreen::new
        );
    }
}
