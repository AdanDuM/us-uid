package net.douglashiura.us.serial;

import java.awt.Color;

public enum Results {

	OK(Color.GREEN), ERROR(Color.RED), FAIL(Color.BLUE), NOT_EXECUTED(Color.BLACK), END(Color.BLACK),
	UN_EXECUTED(Color.BLACK), HIGHLIGHT(Color.BLUE);

	private Color color;

	public static boolean isExecutionFinishy(Results result) {
		return ERROR.equals(result) || FAIL.equals(result) || END.equals(result);
	}

	private Results(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}