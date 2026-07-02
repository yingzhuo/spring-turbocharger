package com.github.yingzhuo.turbocharger.captcha.google.word;

import java.io.Serializable;

public class Word implements Serializable {

	private String stringForDrawing;
	private String stringForValidation;

	public Word() {
	}

	public String getStringForDrawing() {
		return stringForDrawing;
	}

	public void setStringForDrawing(String stringForDrawing) {
		this.stringForDrawing = stringForDrawing;
	}

	public String getStringForValidation() {
		return stringForValidation;
	}

	public void setStringForValidation(String stringForValidation) {
		this.stringForValidation = stringForValidation;
	}

}
