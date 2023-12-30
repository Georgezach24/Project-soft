package gr.conference.sys;

/**
*
* @author Giorgos Zachos
*/

//import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class UserDBHandler{

	
   public static EntityManagerFactory ENITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("sys");
   public static int loginTries = 2; 
   
   public static boolean isPasswordValid(String password) 
   {
       String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!*@#$%^&+=]).{8,}$";
       return password.matches(passwordPattern);
   }
   
   public static boolean isUsernameValid(String username) 
   {
       String usernamePattern = "^[a-zA-Z][a-zA-Z0-9_]{4,}$";
       return username.matches(usernamePattern);
   }

  
  
   public static void registerUser(String username, String password, String password2, String email, String phone) {
   EntityManager em = ENITY_MANAGER_FACTORY.createEntityManager();
   EntityTransaction et = em.getTransaction();
   JFrame j = new JFrame();
   try {
       et.begin();
       // Check if the user already exists
       String query = "SELECT COUNT(u) FROM User u WHERE u.username = :username";
       TypedQuery<Long> countQuery = em.createQuery(query, Long.class);
       countQuery.setParameter("username", username);
       long existingUserCount = countQuery.getSingleResult();

       if (existingUserCount == 0 && !username.isBlank() && !password.isBlank()
    		   && isPasswordValid(password) && isUsernameValid(username)
    		   && password.equals(password2)) 
       {
           // User does not exist, proceed with registration
           User newUser = new User();
           newUser.setName(null);
           newUser.setSurname(null);
           newUser.setUsername(username);
           newUser.setPassword(password);
           newUser.setEmail(email);
           newUser.setPhone(phone);
           newUser.setUser_status("ACTIVE");
           newUser.setRole("USER");
           

           em.persist(newUser);
           et.commit();
       } 
   } catch (Exception e) {
       e.printStackTrace();
   } finally {
       if (et.isActive()) {
           et.rollback();
       }
       em.close();
   }
}
   
   

public static boolean loginUser(String username , String password)
   {
       EntityManager em = ENITY_MANAGER_FACTORY.createEntityManager();
       String query = "SELECT u FROM User u WHERE u.username =:username AND u.password =:password";
       TypedQuery<User> tq = em.createQuery(query, User.class);
       tq.setParameter("username", username);
       tq.setParameter("password", password);
       User logedUser = null;
       try{
    	   logedUser = tq.getSingleResult();
           loginTries = 2;
           return true;
       }catch(Exception e)
       {
    	   System.out.println("User dosent exsist!");
           loginTries -- ;  
           
       }
       finally{
           em.close();
           
       }
       return false;
   }
   
}
