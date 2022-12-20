package highfox.deathcoords.util;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public enum CoordsPosition {
	TOP_LEFT("top_left"),
	TOP_RIGHT("top_right"),
	BOTTOM_LEFT("bottom_left"),
	BOTTOM_RIGHT("bottom_right");

	private final String name;

	private CoordsPosition(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void renderCoords(List<Text> lines, MatrixStack matrix, MinecraftClient client) {
		TextRenderer textRenderer = client.textRenderer;
		int windowHeight = client.getWindow().getScaledHeight();
		int windowWidth = client.getWindow().getScaledWidth();
		if (this == BOTTOM_LEFT || this == BOTTOM_RIGHT) {
			lines = Lists.reverse(lines);
		}

		for (int i = 0; i < lines.size(); i++) {
			Text text = lines.get(i);
			int textColor = text.getStyle().getColor() != null ? text.getStyle().getColor().getRgb() : 0xFFFFFF;
			textRenderer.drawWithShadow(matrix, text, this.getXOffset(windowWidth, textRenderer.getWidth(text)), this.getYOffset(windowHeight, i), textColor);
		}
	}

	private int getXOffset(int windowWidth, int textWidth) {
		switch (this) {
			default:
			case BOTTOM_LEFT:
			case TOP_LEFT:
				return 7;
			case BOTTOM_RIGHT:
			case TOP_RIGHT:
				return windowWidth - 7 - textWidth;
		}
	}

	private int getYOffset(int windowHeight, int lineNumber) {
		switch (this) {
			default:
			case BOTTOM_LEFT:
			case BOTTOM_RIGHT:
				return windowHeight - (14 + lineNumber * 9);
			case TOP_LEFT:
			case TOP_RIGHT:
				return 5 + lineNumber * 9;
		}
	}

}