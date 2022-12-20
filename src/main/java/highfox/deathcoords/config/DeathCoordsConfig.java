package highfox.deathcoords.config;

import java.nio.file.Paths;

import com.electronwill.nightconfig.core.file.FileConfig;

import highfox.deathcoords.util.CoordsPosition;
import net.fabricmc.loader.api.FabricLoader;

public class DeathCoordsConfig {
	private final FileConfig config;
	private boolean displayAfterDeath;
	private CoordsPosition coordsPosition;

	public DeathCoordsConfig(String fileName) {
		this.config = FileConfig.builder(Paths.get(FabricLoader.getInstance().getConfigDir().toString(), fileName))
				.concurrent()
				.defaultResource("/" + fileName)
				.autoreload()
				.build();
	}

	public void load() {
		this.config.load();

		this.displayAfterDeath = this.config.getOrElse("display_after_death", true);
		this.coordsPosition = this.config.getEnumOrElse("coords_position", CoordsPosition.BOTTOM_LEFT);
	}

	public void save() {
		this.config.save();
	}

	public boolean shouldDisplayAfterDeath() {
		return this.displayAfterDeath;
	}

	public void setDisplayAfterDeath(boolean displayAfterDeath) {
		this.displayAfterDeath = displayAfterDeath;
		this.config.set("display_after_death", displayAfterDeath);
	}

	public CoordsPosition getCoordsPosition() {
		return this.coordsPosition;
	}

	public void setCoordsPosition(CoordsPosition coordsPosition) {
		this.coordsPosition = coordsPosition;
		this.config.set("coords_position", coordsPosition.getName());
	}

}
