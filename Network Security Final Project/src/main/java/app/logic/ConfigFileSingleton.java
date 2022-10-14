package app.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import app.json.simple.JSONObject;
import app.json.simple.parser.JSONParser;
import app.json.simple.parser.ParseException;

public class ConfigFileSingleton {

	private final String DICTIONARY_PATH = "src/main/java/dictionary.txt";
	// static variable single_instance of type Singleton
	private static ConfigFileSingleton single_instance = null;

	// variable of type String
	private int passConfigMinLength;
	private int passConfigLowChar;
	private int passConfigCapitalChar;
	private int passConfigDigits;
	private int passConfigSpecialChar;
	private int passConfigPassHistory;
	private int passConfigMaxLoginAttempts;
	private String passConfigDictionaryPath;

	// static method to create instance of Singleton class
	public static ConfigFileSingleton getInstance(File file) {
		if (single_instance == null)
			single_instance = new ConfigFileSingleton(file);

		return single_instance;
	}

	private ConfigFileSingleton(File file) {
		ReadJSON(file);
	}

	private void parsePassConfigObject(JSONObject passConfig) {
		// Get configuration MinLength
		this.passConfigMinLength = Integer.parseInt((String) passConfig.get("MinLength"));

		// Get configuration MinLength
		this.passConfigLowChar = Integer.parseInt((String) passConfig.get("LowChar"));

		// Get configuration MinLength
		this.passConfigCapitalChar = Integer.parseInt((String) passConfig.get("CapitalChar"));

		// Get configuration MinLength
		this.passConfigDigits = Integer.parseInt((String) passConfig.get("Digits"));

		// Get configuration MinLength
		this.passConfigSpecialChar = Integer.parseInt((String) passConfig.get("SpecialChar"));

		// Get configuration MinLength
		this.passConfigPassHistory = Integer.parseInt((String) passConfig.get("PassHistory"));

		// Get configuration Dictionary path
		this.passConfigDictionaryPath = (String) passConfig.get("DictionaryPath");
		
		this.passConfigMaxLoginAttempts = Integer.parseInt((String) passConfig.get("LoginAttempts"));
	}

	@SuppressWarnings("unchecked")
	public static void writeDefaultConfig(String path) {
		JSONObject passConfig = new JSONObject();
		passConfig.put("MinLength", "10");
		passConfig.put("LowChar", "1");
		passConfig.put("CapitalChar", "1");
		passConfig.put("Digits", "1");
		passConfig.put("SpecialChar", "1");
		passConfig.put("PassHistory", "3");
		passConfig.put("DictionaryPath", path);
		passConfig.put("LoginAttempts", "3");

		try (FileWriter file = new FileWriter(path, false)) { // false = overwrites to file

			// We can write any JSONArray or JSONObject instance to the file
			file.write(passConfig.toJSONString());
			file.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ReadJSON(File file) {
		if (single_instance == null && !file.exists()) {
			writeDefaultConfig(file.getPath());
		}
		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(file)) {
			// Read JSON files
			Object obj = jsonParser.parse(reader);

			JSONObject passConfig = (JSONObject) obj;

			// Iterate over employee array
			parsePassConfigObject(passConfig);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public int validateNewPassword(String password, String repeatPass, File file, String[] pastPasswords, String salt) {
		if (!password.equals(repeatPass))
			return 1; // 1 = passwords aren't match
		ReadJSON(file);
		int lowCaseNum = 0;
		int capitalNum = 0;
		int digits = 0;
		int specialChars = 0;

		// count password for capital, lowercase, digits and special chars

		for (int i = 0; i < password.length(); i++) {
			if (Character.isLowerCase(password.charAt(i)))
				lowCaseNum++;
			if (Character.isUpperCase(password.charAt(i)))
				capitalNum++;
			if (Character.isDigit(password.charAt(i)))
				digits++;
			if (password.substring(i, i + 1).matches("[^A-Za-z0-9]"))
				specialChars++;
		}

		if (password.length() < this.passConfigMinLength)
			return 2; // 2= Minimum length not fullfiled
		if (lowCaseNum < this.passConfigLowChar)
			return 3; // 3 = Minimum low Character not fullfiled
		if (capitalNum < this.passConfigCapitalChar)
			return 4; // 4 = Minimum capital case not fullfileld
		if (digits < this.passConfigDigits)
			return 5; // 5 = Minimum number of digits not fullfiled
		if (specialChars < this.passConfigSpecialChar)
			return 6; // 6 = Minimum special chars
		if (getPassHistory(password, pastPasswords, salt, this.passConfigPassHistory))
			return 7; // 7 = Password history not fullfiled
		if (!getDictionaryResult(password))
			return 8; // 8 = Password is dictionary password

		return 0; // 0 = good password
	}

	private boolean getPassHistory(String newPassword, String[] pastSaltedHashes, String salt, int count) {
		String hashtext = hashThisText(salt + newPassword);

		for (int i = 0; i < Math.min(count, pastSaltedHashes.length); i++) {
			if (hashtext.equals(pastSaltedHashes[i])) {
				return true;
			}
		}
		return false;
	}


	public int getPassConfigMaxLoginAttempts() {
		return passConfigMaxLoginAttempts;
	}

	public void setPassConfigMaxLoginAttempts(int passConfigMaxLoginAttempts) {
		this.passConfigMaxLoginAttempts = passConfigMaxLoginAttempts;
	}

	private String hashThisText(String text) {
		// getInstance() method is called with algorithm SHA-1
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// digest() method is called
		// to calculate message digest of the input string
		// returned as array of byte
		byte[] messageDigest = md.digest(text.getBytes());

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, messageDigest);

		// Convert message digest into hex value
		String hashtext = no.toString(16);

		// Add preceding 0s to make it 32 bit
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}

	private boolean getDictionaryResult(String newPassword) {
		File dic = new File(DICTIONARY_PATH);
		try {
			@SuppressWarnings("resource")
			Scanner s = new Scanner(dic);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				if (line.equals(newPassword)) {
					return false;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

}
