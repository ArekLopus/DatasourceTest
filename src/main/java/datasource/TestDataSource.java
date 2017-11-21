package datasource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;


@Named
@SessionScoped
@SuppressWarnings("unused")
public class TestDataSource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int counter = 1;
	private List<User> userList = new ArrayList<User>();
	
	//Must be transient (because it is Session scope) or -> org.jboss.weld.exceptions.IllegalProductException: WELD-000054:
	// Producers cannot produce non-serializable instances for injection into non-transient fields of passivating beans
	@Inject
	transient Logger logger;
	
	@Resource
	UserTransaction ut;
	
	@PersistenceContext(unitName="testPU")
	//@PersistenceContext
    private EntityManager em;

    public void saveNewEntity() throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {

        User u1 = new User();
        u1.setName("John");
        u1.setSurname("Doe "+counter);
        
        ut.begin();
        em.persist(u1);
        ut.commit();
        
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
