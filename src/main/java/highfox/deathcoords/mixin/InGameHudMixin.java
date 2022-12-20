package highfox.deathcoords.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.ibm.icu.lang.UCharacter;

import highfox.deathcoords.DeathCoords;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {

	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At(value = "TAIL"))
	private void renderDeathCoords(MatrixStack matrix, float tickDelta, CallbackInfo callback) {
		if (this.client.player != null && this.client.world != null && !this.client.options.hudHidden && DeathCoords.coordsEnabled && this.client.player.getLastDeathPos().isPresent()) {
			PlayerEntity player = this.client.player;
			GlobalPos globalPos = player.getLastDeathPos().orElseThrow();
			BlockPos blockPos = globalPos.getPos();

			List<Text> lines = new ArrayList<>();
			lines.add(Text.literal("Last Death Pos:"));
			lines.add(Text.literal(String.format(Locale.ROOT, "%d %d %d (%s)", blockPos.getX(), blockPos.getY(), blockPos.getZ(), UCharacter.toTitleCase(Locale.ROOT, globalPos.getDimension().getValue().getPath().replace('_', ' '), null, 0))));
			if (player.getWorld().getRegistryKey().getValue().equals(globalPos.getDimension().getValue())) {
				lines.add(Text.literal(String.format(Locale.ROOT, "%d blocks away", (int) MathHelper.sqrt((float) player.getBlockPos().getSquaredDistance(blockPos)))).formatted(Formatting.GRAY));
			}

			DeathCoords.config.getCoordsPosition().renderCoords(lines, matrix, this.client);

		}
	}

}
