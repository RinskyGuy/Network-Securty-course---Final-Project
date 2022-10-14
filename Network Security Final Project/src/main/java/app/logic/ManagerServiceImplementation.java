package app.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.boundaries.InternetPackageBoundary;
import app.data.CustomerDao;
import app.data.InternetPackageDao;
import app.data.UserDao;


@Service
public class ManagerServiceImplementation implements ManagerService {
	
	private UserDao userDao;
	private CustomerDao CustomerDao;
	private InternetPackageDao InternetPackageDao;
	
	@Autowired
	public ManagerServiceImplementation(UserDao userDao) {
		super();
		this.userDao = userDao;
		this.CustomerDao = CustomerDao;
		this.InternetPackageDao = InternetPackageDao;
	}

	@Override
	public InternetPackageBoundary createInternetPackage() {
		// TODO Auto-generated method stub
		return null;
	}
	


}