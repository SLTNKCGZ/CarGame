package application;

public class Metadata {
private double width;
private double height;
private int gridCellsX;
private int gridCellsY;
private int numOfPaths;
private int numOfCars;
private int carAccident;

public Metadata(double width, double height, int gridCellsX, int gridCellsY, int numOfPaths, int numOfCars,
		int carAccident) {
	super();
	this.width = width;
	this.height = height;
	this.gridCellsX = gridCellsX;
	this.gridCellsY = gridCellsY;
	this.numOfPaths = numOfPaths;
	this.numOfCars = numOfCars;
	this.carAccident = carAccident;
}
public double getWidth() {
	return width;
}
public void setWidth(double width) {
	this.width = width;
}
public double getHeight() {
	return height;
}
public void setHeight(double height) {
	this.height = height;
}
public int getGridCellsX() {
	return gridCellsX;
}
public void setGridCellsX(int gridCellsX) {
	this.gridCellsX = gridCellsX;
}
public int getGridCellsY() {
	return gridCellsY;
}
public void setGridCellsY(int gridCellsY) {
	this.gridCellsY = gridCellsY;
}
public int getNumOfPaths() {
	return numOfPaths;
}
public void setNumOfPaths(int numOfPaths) {
	this.numOfPaths = numOfPaths;
}
public int getNumOfCars() {
	return numOfCars;
}
public void setNumOfCars(int numOfCars) {
	this.numOfCars = numOfCars;
}
public int getCarAccident() {
	return carAccident;
}
public void setCarAccident(int carAccident) {
	this.carAccident = carAccident;
}
}
