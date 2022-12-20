package highfox.deathcoords.config;

import highfox.deathcoords.DeathCoords;
import highfox.deathcoords.util.CoordsPosition;
import highfox.deathcoords.util.Translation;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;

public class ConfigScreen {

	public static Screen makeScreen(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(Translation.translate("config", "title"))
				.setSavingRunnable(DeathCoords.config::save);
		ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		builder.getOrCreateCategory(Translation.translate("config", "category.main"))
			.addEntry(entryBuilder
					.startBooleanToggle(Translation.translate("config", "displayAfterDeath"), DeathCoords.config.shouldDisplayAfterDeath())
					.setTooltip(Translation.translate("config", "displayAfterDeath.tooltip"))
					.setSaveConsumer(DeathCoords.config::setDisplayAfterDeath)
					.build())
			.addEntry(entryBuilder
					.startEnumSelector(Translation.translate("config", "coordsPosition"), CoordsPosition.class, DeathCoords.config.getCoordsPosition())
					.setSaveConsumer(DeathCoords.config::setCoordsPosition)
					.setEnumNameProvider(position -> Translation.translate("config", "coordsPosition." + ((CoordsPosition) position).getName()))
					.build());

		return builder.build();
	}

}
