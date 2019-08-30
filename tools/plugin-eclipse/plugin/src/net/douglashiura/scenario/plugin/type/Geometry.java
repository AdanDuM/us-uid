package net.douglashiura.scenario.plugin.type;

public class Geometry {

	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;

	public Geometry(Integer x, Integer y, Integer width, Integer hieght) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = hieght;
	
	}
	
	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

}
