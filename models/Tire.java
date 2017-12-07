package models;

public class Tire {
	
	private int tireID;
	private String name;
	private double price;
	private String brand;
	private int rimDiameter;
	
	public Tire() {
		this.setName("");
		
	}
	
	public Tire(int tireID, String name, double price, String brand, int rimDiameter) {
		this.tireID = tireID;
		this.name = name;
		this.price = price;
		this.brand = brand;
		this.rimDiameter = rimDiameter;
	}

	public Tire(Tire another) {
		this.tireID = another.getTireID();
		this.name = another.getName();
		this.price = another.getPrice();
		this.brand = another.getBrand();
		this.rimDiameter = another.getRimDiameter();
	}

	public int getTireID() {
		return tireID;
	}

	public void setTireID(int tireID) {
		this.tireID = tireID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getRimDiameter() {
		return rimDiameter;
	}

	public void setRimDiameter(int rimDiameter) {
		this.rimDiameter = rimDiameter;
	}

	@Override
	public String toString() {
		return "Tire [tireID=" + tireID + ", name=" + name + ", price=" + price + ", brand=" + brand + ", rimDiameter="
				+ rimDiameter + "]";
	}
	
}
