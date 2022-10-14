package app.controllers;

import app.boundaries.EmailBoundary;
import app.boundaries.RegisterBoundary;
import app.boundaries.ResetPasswordBoundary;
import app.boundaries.UserBoundary;
import app.logic.CustomerServiceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {
	private CustomerServiceImplementation CustomerServiceImplementation;

	@Autowired
	public CustomerController(CustomerServiceImplementation CustomerServiceImplementation) {
		this.CustomerServiceImplementation = CustomerServiceImplementation;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "index.html";
	}

	@PostMapping(value = "/signup")
	public String signUp(RegisterBoundary newRegister) { // sign Up function
		int isOk = this.CustomerServiceImplementation.signUp(newRegister);
		if (isOk == 0) {
			return "redirect:/";
		} else {
			return "redirect:/register_error.html";
		}
	}

	@GetMapping(value = "/fillDB")
	public String fillDB() { // fill DB
		this.CustomerServiceImplementation
				.signUp(new RegisterBoundary("Admin", "admin@email.com", "Admin1Admin&", "Admin1Admin&"));
		this.CustomerServiceImplementation
				.signUp(new RegisterBoundary("User1", "user1@email.com", "user1USER&%X", "user1USER&%X"));
		this.CustomerServiceImplementation
				.signUp(new RegisterBoundary("User2", "user2@email.com", "user2USER&%X", "user2USER&%X"));
		this.CustomerServiceImplementation
				.signUp(new RegisterBoundary("User3", "user3@email.com", "user3USER&%X", "user3USER&%X"));
		this.CustomerServiceImplementation
				.signUp(new RegisterBoundary("User4", "user4@email.com", "user4USER&%X", "user4USER&%X"));
		this.CustomerServiceImplementation
				.signUp(new RegisterBoundary("<img src=\"https://image.shutterstock.com/image-photo/mountains-under-mist-morning-amazing-260nw-1725825019.jpg\">", "user4@email.com",
						"user4USER&%X", "user4USER&%X"));
		return "redirect:/";
	}

	@PostMapping(value = "/signin")
	public String signIn(String username, String password, Model model) { // sign In function
		int isOk = this.CustomerServiceImplementation.login(new UserBoundary(username, password));
		if (isOk == 0) {
			model.addAttribute("name", "123456789");
			return "redirect:/welcome.html?username=" + username;
		} else if (isOk == -3) {
			return "redirect:/error.html"; // blocked
		} else {
			return "redirect:/wrong_connection.html"; // wrong details but not blocked
		}
	}

	@PostMapping(value = "/signinattack")
	public String signInattack(String username, String password, Model model) { // sign In function
		int isOk = this.CustomerServiceImplementation.loginNative(new UserBoundary(username, password));
		if (isOk == 0) {
			model.addAttribute("name", "123456789");
			return "redirect:/welcome.html";
		} else if (isOk == -3) {
			return "redirect:/error.html"; // blocked
		} else {
			return "redirect:/wrong_connection.html"; // wrong details but not blocked
		}
	}

	@PostMapping(value = "/resetPassForgot")
	public String resetPass(ResetPasswordBoundary ResetPasswordBoundary) {
		int status = this.CustomerServiceImplementation.changePassword(ResetPasswordBoundary);
		if (status == 0) {
			return "redirect:/index.html"; // allow change password
		} else {
			return "redirect:/reset_pass_forgot.html";
		}
	}

	@PostMapping(value = "/sendMailCode")
	public String sendEmailcode(EmailBoundary boundary) {
		// do something with mail
		String username = boundary.getUsername();
		boolean isOk = this.CustomerServiceImplementation.sendConfirmationEmail(username);
		if (isOk) {
			return "redirect:/forgot_pass_check_code.html";
		} else {
			return "redirect:/forgot_pass.html";
		}

	}

	@PostMapping(value = "/checkMailCode")
	public String checkEmailcode(EmailBoundary boundary) {
		String username = boundary.getUsername();
		String code = boundary.getCode();
		boolean isOk = this.CustomerServiceImplementation.checkConfirmationCode(username, code);
		if (isOk) {
			return "redirect:/reset_pass_forgot.html"; // allow change password
		} else {
			return "redirect:/forgot_pass_check_code.html";
		}

	}

	
	 @GetMapping(value = "/getAllUsers")
	 @ResponseBody
	 public List<UserBoundary> getUsers() { 
		 
		 	
		 return this.CustomerServiceImplementation.getAllUsers(); 
//		 return "123";
	 }
	 

//	@ModelAttribute("users")
//	public List<UserBoundary> users() {
//
//		System.out.println(this.CustomerServiceImplementation.getAllUsers());
//		return this.CustomerServiceImplementation.getAllUsers();
//	}
}