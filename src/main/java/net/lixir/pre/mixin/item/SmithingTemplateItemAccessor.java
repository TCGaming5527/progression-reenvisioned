package net.lixir.pre.mixin.item;

import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.item.SmithingTemplateItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(SmithingTemplateItem.class)
public interface SmithingTemplateItemAccessor {
    @Accessor("TITLE_FORMATTING")
    static Formatting getTitleFormatting() {
        throw new AssertionError();
    }

    @Accessor("DESCRIPTION_FORMATTING")
    static Formatting getDescriptionFormatting() {
        throw new AssertionError();
    }

    @Invoker("getArmorTrimEmptyBaseSlotTextures")
    static List<Identifier> callGetArmorTrimEmptyBaseSlotTextures() {
        throw new AssertionError();
    }

    @Invoker("getArmorTrimEmptyAdditionsSlotTextures")
    static List<Identifier> callGetArmorTrimEmptyAdditionsSlotTextures() {
        throw new AssertionError();
    }

    @Invoker("getNetheriteUpgradeEmptyBaseSlotTextures")
    static List<Identifier> callGetNetheriteUpgradeEmptyBaseSlotTextures() {
        throw new AssertionError();
    }

    @Invoker("getNetheriteUpgradeEmptyAdditionsSlotTextures")
    static List<Identifier> callGetNetheriteUpgradeEmptyAdditionsSlotTextures() {
        throw new AssertionError();
    }
}
