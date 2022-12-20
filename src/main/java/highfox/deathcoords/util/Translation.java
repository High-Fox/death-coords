package highfox.deathcoords.util;

import highfox.deathcoords.DeathCoords;
import net.minecraft.text.Text;

public class Translation {

	public static String translatableString(String domain, String path) {
		return domain + "." + DeathCoords.MODID + "." + path;
	}

	public static Text translate(String domain, String path) {
		return Text.translatable(translatableString(domain, path));
	}

}
