package net.lixir.pre.common.item;

import lombok.experimental.UtilityClass;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.mixin.accessor.SmithingTemplateItemAccessor;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

@UtilityClass
public class SmithingTemplateUtil {

    public Text BASIC_UPGRADE_APPLIES_TO_TEXT = Text.translatable(
                    Util.createTranslationKey("item", ProgressionReEnvisioned.id("smithing_template.basic_upgrade.applies_to"))
            )
            .formatted(SmithingTemplateItemAccessor.getDescriptionFormatting());
    public Text BASIC_UPGRADE_INGREDIENTS_TEXT = Text.translatable(
                    Util.createTranslationKey("item", ProgressionReEnvisioned.id("smithing_template.basic_upgrade.ingredients"))
            )
            .formatted(SmithingTemplateItemAccessor.getDescriptionFormatting());
    public Text BASIC_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Text.translatable(
            Util.createTranslationKey("item", ProgressionReEnvisioned.id("smithing_template.basic_upgrade.base_slot_description"))
    );
    public Text BASIC_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Text.translatable(
            Util.createTranslationKey("item", ProgressionReEnvisioned.id("smithing_template.basic_upgrade.additions_slot_description"))
    );

    public SmithingTemplateItem createBasicSmithingTemplate(Item.Settings settings) {
        return new SmithingTemplateItem(
                BASIC_UPGRADE_APPLIES_TO_TEXT,
                BASIC_UPGRADE_INGREDIENTS_TEXT,
                BASIC_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT,
                BASIC_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                SmithingTemplateItemAccessor.callGetNetheriteUpgradeEmptyBaseSlotTextures(),
                SmithingTemplateItemAccessor.callGetNetheriteUpgradeEmptyAdditionsSlotTextures(),
                settings
        );
    }
}
