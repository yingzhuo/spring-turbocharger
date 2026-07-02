package com.github.yingzhuo.turbocharger.captcha.google.renderer;

import com.github.yingzhuo.turbocharger.captcha.google.color.ColorFactory;
import com.github.yingzhuo.turbocharger.captcha.google.font.FontFactory;

import java.awt.image.BufferedImage;

public interface TextRenderer {

	void setLeftMargin(int leftMargin);

	void setRightMargin(int rightMargin);

	void setTopMargin(int topMargin);

	void setBottomMargin(int bottomMargin);

	void draw(String text, BufferedImage canvas, FontFactory fontFactory, ColorFactory colorFactory);

}
