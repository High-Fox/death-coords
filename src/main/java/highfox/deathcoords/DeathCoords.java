package highfox.deathcoords;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import highfox.deathcoords.config.DeathCoordsConfig;
import highfox.deathcoords.util.Translation;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

@Environment(EnvType.CLIENT)
public class DeathCoords implements ClientModInitializer {
	public static final String MODID = "deathcoords";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static DeathCoordsConfig config = null;

	private final KeyBinding toggleCoordsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(Translation.translatableString("key", "toggleCoords"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, Translation.translatableString("key", "keyCategory")));
	public static boolean coordsEnabled = false;

	@Override
	public void onInitializeClient() {
		config = new DeathCoordsConfig("death-coords.toml");
		config.load();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (this.toggleCoordsKey.wasPressed()) {
				coordsEnabled = !coordsEnabled;
			}
		});
	}

}
