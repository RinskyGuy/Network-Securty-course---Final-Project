package app.logic;

import java.util.List;

import app.boundaries.InternetPackageBoundary;

public interface ManagerService {
	public InternetPackageBoundary createInternetPackage();
	//public List<UserBoundary> getAllUsers(String adminSpace,String adminEmail);
	//public void deleteAllUsers(String adminSpace,String adminEmail);
	//public ItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,ItemBoundary update);
	//public List<ItemBoundary> getAllItems(String userSpace, String userEmail );
	//public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId);
	//public void deleteAllItems(String adminSpace, String adminEmail);
}
