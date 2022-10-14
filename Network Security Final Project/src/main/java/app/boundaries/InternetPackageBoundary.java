package app.boundaries;

public class InternetPackageBoundary {
	
	int id;
	String name;
	int megaPerMonth;
	double pricePerMonth;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMegaPerMonth() {
		return megaPerMonth;
	}
	public void setMegaPerMonth(int megaPerMonth) {
		this.megaPerMonth = megaPerMonth;
	}
	public double getPricePerMonth() {
		return pricePerMonth;
	}
	public void setPricePerMonth(double pricePerMonth) {
		this.pricePerMonth = pricePerMonth;
	}
	
	
}
