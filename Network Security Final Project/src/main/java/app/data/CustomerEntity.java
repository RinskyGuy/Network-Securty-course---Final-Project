package app.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CUSTOMERS")
public class CustomerEntity {
	
	String username;
	String email;
	int[] internetPackageIds;
	
	@Id
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int[] getInternetPackageIds() {
		return internetPackageIds;
	}
	public void setInternetPackageIds(int[] internetPackageIds) {
		this.internetPackageIds = internetPackageIds;
	}
	
	

	
	
	
}
