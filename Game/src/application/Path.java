package application;

public class Path {

	private int indexOfPath;
	private String element;
	private double x;
	private double y;
	
	
	public Path(int indexOfPath, String element, double x, double y) {
		super();
		this.indexOfPath = indexOfPath;
		this.element = element;
		this.x = x;
		this.y = y;
	}
	public int getIndexOfPath() {
		return indexOfPath;
	}
	public void setIndexOfPath(int indexOfPath) {
		this.indexOfPath = indexOfPath;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
}
