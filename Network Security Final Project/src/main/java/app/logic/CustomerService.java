package app.logic;

import app.boundaries.CustomerBoundary;
import app.boundaries.RegisterBoundary;
import app.boundaries.ResetPasswordBoundary;
import app.boundaries.UserBoundary;

public interface CustomerService {
	public int signUp(RegisterBoundary details);
	public int login(UserBoundary user);
	public int nativeQuerylogin(UserBoundary user);
	public CustomerBoundary purchasePackage();
	public int changePassword(ResetPasswordBoundary ResetPasswordBoundary);
}
