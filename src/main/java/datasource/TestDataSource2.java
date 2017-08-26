package datasource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

//@Named("testDataSource")
@SessionScoped
@SuppressWarnings("unused")
public class TestDataSource2 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int counter = 1;
	private List<User> userList = new ArrayList<User>();
	
	@Inject
	transient Logger logger;
	
	@PersistenceContext
    private EntityManager em;
	
	@Transactional(value=Transactional.TxType.REQUIRES_NEW)
    public void saveNewEntity() {

        User u1 = new User();
        u1.setName("John");
        u1.setSurname("Doe "+counter);
        
        em.persist(u1);
        
        counter ++;
        
        logger.info("Entity Added: "+u1);
        
    }
    
    public void getThemAll() {
    	userList = getAllEntities();

    	logger.info("Users: "+userList.size());
		//userList.stream().forEach(e -> logger.info(e::toString));
	}
    
    
    private List<User> getAllEntities() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
    
    
}
