package app.logic;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import app.data.UserDao;
import app.boundaries.UserBoundary;
import app.data.UserEntity;
import app.boundaries.CustomerBoundary;
import app.boundaries.RegisterBoundary;
import app.boundaries.ResetPasswordBoundary;
import app.data.CustomerDao;
import app.data.CustomerEntity;


@Service
public class CustomerServiceImplementation implements CustomerService {

	//DB Objects:
	private UserDao UserDao;
	private CustomerDao CustomerDao;
	private JavaMailSender Sender;
	private ConfigFileSingleton config;
	private File configfile;
	
	
	@Autowired
	public CustomerServiceImplementation(UserDao UserDao, CustomerDao CustomerDao,JavaMailSender Sender) {
		
		this.UserDao = UserDao;
		this.CustomerDao = CustomerDao;
		this.Sender = Sender;
		this.configfile = new File("Config.txt");
		this.config = ConfigFileSingleton.getInstance(this.configfile);
	}
	
	@Override
	public int signUp(RegisterBoundary details) {
		
		//check new register
		int passStatus = checkPass(details.getPassword(),
				details.getRepeatPassword(),
				new String[0],
				" ");
		if (passStatus != 0) return passStatus; // only 0 = ok
		
		// XSS check
		int usernameStatus = checkUsername(details.getUsername());
		if (usernameStatus != 0) return usernameStatus; //only 0 = ok
		// End of XSS check
		
		//create and save customer entity
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setUsername(details.getUsername());
		customerEntity.setEmail(details.getEmail());
		customerEntity.setInternetPackageIds(new int[5]);
		this.CustomerDao.save(customerEntity);
		
		//create and save user entity
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(details.getUsername());
		userEntity.setSalt(generateRandomHash());
		userEntity.setHashCode(calcSaltedHash(details.getPassword(), userEntity.getSalt()));
		userEntity.setFalseConnectionAttempts(0);
		this.UserDao.save(userEntity);
		
		return passStatus;
	}
	
	
	private int checkUsername(String username) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");
		Matcher m = p.matcher(username);
		if (m.find()) {
				return 0;
				}else {
					return 1;
				}
	}

	private int checkPass(String password, String repeatPassword,String[] pastPasswords,String salt) {
		return this.config.validateNewPassword(password, repeatPassword, this.configfile, pastPasswords, salt);
	}

	@Override
	public CustomerBoundary purchasePackage() {
		
		return null;
	}

	@Override
	public int login(UserBoundary user) {
		//inputValidation();
		config.ReadJSON(configfile);	
		Optional<UserEntity> exist = this.UserDao.findById(user.getUsername());
		if (exist.isPresent()) { //good username
			UserEntity DBentity = exist.get();
			String compared = calcSaltedHash(user.getPassword(),DBentity.getSalt());
			if(compared.compareTo(DBentity.getHashCode()) == 0) { 
				//good username good password
				int configAttempts = this.config.getPassConfigMaxLoginAttempts();
				if(DBentity.getFalseConnectionAttempts() >= configAttempts) return -3;//blocked account
				else return 0;
			} else { // good username wrong password
				int NumOfTries = DBentity.getFalseConnectionAttempts();
				DBentity.setFalseConnectionAttempts(++NumOfTries);
				this.UserDao.save(DBentity);
				int configAttempts = this.config.getPassConfigMaxLoginAttempts();
				if( DBentity.getFalseConnectionAttempts() >= configAttempts) return -3;//blocked account
				return -2; //wrong but not blocked
			}
		} else {  // wrong username
			return -1; //wrong username
		}
	}
	
	
	public int loginNative(UserBoundary user) {
		Optional<UserEntity> exist = this.UserDao.findById(user.getUsername());
		if (exist.isPresent()) { //good username
			UserEntity DBentity = exist.get();
			String salt = DBentity.getSalt();
			//String hash = calcSaltedHash(user.getPassword(),salt);
			List<Object[]> objects = this.UserDao.loginNative(user.getUsername(),user.getPassword());
			if(objects.size() == 1) { //if found
				return 0;
			}
		}
		return -1;
	}
	
	
	
	@Override
	public int nativeQuerylogin(UserBoundary user) { //work with native query
		//inputValidation();
		config.ReadJSON(configfile);
		Optional<UserEntity> exist = this.UserDao.findById(user.getUsername());
		if (exist.isPresent()) { //good username
			UserEntity DBentity = exist.get();
			String compared = calcSaltedHash(user.getPassword(),DBentity.getSalt());
			if(compared.compareTo(DBentity.getHashCode()) == 0) { 
				//good username good password
				int configAttempts = this.config.getPassConfigMaxLoginAttempts();
				if(DBentity.getFalseConnectionAttempts() >= configAttempts) return -3;//blocked account
				else return 0;
			} else { // good username wrong password
				int NumOfTries = DBentity.getFalseConnectionAttempts();
				DBentity.setFalseConnectionAttempts(++NumOfTries);
				this.UserDao.save(DBentity);
				int configAttempts = this.config.getPassConfigMaxLoginAttempts();
				if( DBentity.getFalseConnectionAttempts() >= configAttempts) return -3;//blocked account
				return -2; //wrong but not blocked
			}
		} else {  // wrong username
			return -1; //wrong username
		}
	}

	@Override
	public int changePassword(ResetPasswordBoundary ResetPasswordBoundary) {
		
		Optional<UserEntity> exist = this.UserDao.findById(ResetPasswordBoundary.getUsername());
		if (exist.isPresent()) { //good username
			// CHECK NEW PASSWORD
			UserEntity DBentity = exist.get();
			String[] pastPass;
			if(DBentity.getPastPasswordsHashCodes() == null) {
				pastPass = new String[0];
			} else {
				pastPass = (String[])DBentity.getPastPasswordsHashCodes().toArray();
			}
			int passStatus = checkPass(ResetPasswordBoundary.getNewPassword(),
					ResetPasswordBoundary.getRepeatPassword(),
					pastPass,
					DBentity.getSalt());
			if (passStatus != 0) return passStatus; // only 0 = ok
			ArrayList<String> passwords = DBentity.getPastPasswordsHashCodes();
			if(passwords == null) passwords = new ArrayList<>();
			passwords.add(DBentity.getHashCode());
			DBentity.setHashCode(calcSaltedHash(ResetPasswordBoundary.getNewPassword(),DBentity.getSalt()));
			this.UserDao.save(DBentity);
			return passStatus;
		} else {
			return -1; //Username does not exist
		}
	}
	
	private String calcSaltedHash(String pass,String salt) {
		//calculation
		String saltPlusPass = salt + pass;
		//set Entity field
		return encryptThisString(saltPlusPass);
	}

	private String generateRandomHash() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[10];
        random.nextBytes(bytes);
        return new String(bytes);
	}

	public String encryptThisString(String input)
	{
		try {
			// getInstance() method is called with algorithm SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");

			// digest() method is called
			// to calculate message digest of the input string
			// returned as array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);

			// Add preceding 0s to make it 32 bit
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			// return the HashText
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean sendConfirmationEmail(String username) { //creating message
	    
		Optional<UserEntity> Entityexist = this.UserDao.findById(username);
		Optional<CustomerEntity> Customerexist = this.CustomerDao.findById(username);
		if (Entityexist.isPresent() && Customerexist.isPresent()) { //if exist	
			
			UserEntity ue = Entityexist.get();
			CustomerEntity ce = Customerexist.get();
			String email = ce.getEmail();
			
			//generate random code
			Random rnd = new Random();
		    int number = rnd.nextInt(999999);
		    String code = String.format("%06d", number);
			
			//sending mail: 
			SimpleMailMessage message = new SimpleMailMessage();  
			message.setFrom("communicationsltd1000@gmail.com");  
			message.setTo(email);  
			message.setSubject("Confirmation Code");
			message.setText(code);  
			Sender.send(message);

			//save code in user DB:
			ue.setEmailConfirmationCode(code);
			this.UserDao.save(ue);
			return true;
		} else {
			return false;
		}
	}

	public boolean checkConfirmationCode(String username, String code) {
		Optional<UserEntity> exist = this.UserDao.findById(username);
		if (exist.isPresent()) { //if exist
			UserEntity DBentity = exist.get();
			if(DBentity.getEmailConfirmationCode().compareTo(code) ==0) {
				return true;
			}
		}
		return false;
	}
	
	public List<UserBoundary> getAllUsers() {
		Iterable<UserEntity> allEntities = this.UserDao.findAll();
		List<UserBoundary> rv = new ArrayList<>();
		for (UserEntity entity : allEntities) {
			UserBoundary boundary = new UserBoundary(entity.getUsername(),entity.getHashCode());		
			rv.add(boundary);
		}		
		return rv;
	}

}
