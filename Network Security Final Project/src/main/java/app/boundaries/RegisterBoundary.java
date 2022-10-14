package app.boundaries;

public class RegisterBoundary {
	private String username;
	private String email;
	private String password;
	private String repeatPassword;
	
	
	
	public RegisterBoundary(String username, String email, String password, String repeatPassword) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.repeatPassword = repeatPassword;
	}
	
	
	public RegisterBoundary() {
	
	}


	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}

	
}
