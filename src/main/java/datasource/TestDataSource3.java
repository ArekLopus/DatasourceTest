package datasource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;


//@Named("testDataSource")
@SessionScoped
@SuppressWarnings("unused")
public class TestDataSource3 implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<User> userList = new ArrayList<User>();
	
	@Inject
	TestDataSource3PersitenceEJB pe;
	

	public void saveNewEntity() {
		pe.saveNewEntity();
	}
	
	public void getThemAll() {
		pe.getThemAll();
	}

	public List<User> getUserList() {
		return pe.getUserList();
	}

	public void setUserList(List<User> userList) {
		pe.setUserList(userList);
	}
	
	
	
	
}
