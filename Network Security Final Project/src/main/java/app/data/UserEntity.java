package app.data;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="USERS")
public class UserEntity {
	
	private String username;
	private String salt;
	private String hashCode;
	private ArrayList<String> pastPasswordsHashCodes;
	private String emailConfirmationCode;
	private int falseConnectionAttempts;
	
	@Id
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getHashCode() {
		return hashCode;
	}
	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	
	public ArrayList<String> getPastPasswordsHashCodes() {
		return pastPasswordsHashCodes;
	}
	
	public void setPastPasswordsHashCodes(ArrayList<String> pastPasswordsHashCodes) {
		this.pastPasswordsHashCodes = pastPasswordsHashCodes;
	}
	
	public String getEmailConfirmationCode() {
		return emailConfirmationCode;
	}
	public void setEmailConfirmationCode(String emailConfirmationCode) {
		this.emailConfirmationCode = emailConfirmationCode;
	}
	public int getFalseConnectionAttempts() {
		return falseConnectionAttempts;
	}
	public void setFalseConnectionAttempts(int falseConnectionAttempts) {
		this.falseConnectionAttempts = falseConnectionAttempts;
	}	
	
}
