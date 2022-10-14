package app.boundaries;

public class CustomerBoundary {
	String username;
	String Email;
	String phone;
	int internetPackageId;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return Email;
	}
	public void setName(String name) {
		this.Email = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getInternetPackageId() {
		return internetPackageId;
	}
	public void setInternetPackageId(int internetPackageId) {
		this.internetPackageId = internetPackageId;
	}
	
}