package app.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INTERNET_PACKAGES")
public class InternetPackageEntity {
	
	int id;
	String name;
	int megaPerMonth;
	double pricePerMonth;

	@Id
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
