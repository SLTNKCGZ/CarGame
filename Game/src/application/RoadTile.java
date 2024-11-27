package application;

public class RoadTile {
private int type;
private int degrees;
private int cellX;
private int cellY;
public RoadTile(int type, int degrees, int cellX, int cellY) {
	
	this.type = type;
	this.degrees = degrees;
	this.cellX = cellX;
	this.cellY = cellY;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public int getDegrees() {
	return degrees;
}
public void setDegrees(int degrees) {
	this.degrees = degrees;
}
public int getCellX() {
	return cellX;
}
public void setCellX(int cellX) {
	this.cellX = cellX;
}
public int getCellY() {
	return cellY;
}
public void setCellY(int cellY) {
	this.cellY = cellY;
}





}
