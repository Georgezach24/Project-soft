package gr.conference.usersys;

/**
*
* @author Giorgos Zachos
*/


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;


public class UserDBHandler{

	
   public static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("sys");
   public static int loginTries = 3; 
   
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

   public static void registerAdmin() {
       EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
       EntityTransaction et = em.getTransaction();

       if (!isAdminRegistered("admin")) {
           try {
               et.begin();
               User adminUser = new User();
               adminUser.setUsername("admin");
               adminUser.setPassword("admin");
               adminUser.setRole("ADMIN");
               em.persist(adminUser);
               et.commit();
           } catch (Exception e) {
               if (et != null && et.isActive()) {
                   et.rollback();
               }
           } finally {
               em.close();
           }
       } else {
           System.out.println("Admin already registered!");
       }
   }

   public static boolean isAdminRegistered(String username) {
	    EntityManager em = Persistence.createEntityManagerFactory("sys").createEntityManager();
	    try {
	        String query = "SELECT COUNT(u) FROM User u WHERE u.username = :username AND u.role = :role";
	        TypedQuery<Long> countQuery = em.createQuery(query, Long.class);
	        countQuery.setParameter("username", username);
	        countQuery.setParameter("role", "ADMIN");
	        long adminCount = countQuery.getSingleResult();
	        return adminCount > 0;
	    } finally {
	        if (em != null && em.isOpen()) {
	            em.close();
	        }
	    }
	}

  
   public static void registerUser(String username, String password, String password2, String email, String phone) {
	    EntityManager em = Persistence.createEntityManagerFactory("sys").createEntityManager();
	    EntityTransaction et = em.getTransaction();
	    try {
	        et.begin();

	        String query = "SELECT COUNT(u) FROM User u WHERE u.username = :username";
	        TypedQuery<Long> countQuery = em.createQuery(query, Long.class);
	        countQuery.setParameter("username", username);
	        long existingUserCount = countQuery.getSingleResult();

	        if (existingUserCount == 0 && !username.isBlank() && !password.isBlank()
	                && isPasswordValid(password) && isUsernameValid(username)
	                && password.equals(password2)) {
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
       EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
       String role = "USER";
       String role2 = "PC CHAIR";
       String query = "SELECT u FROM User u WHERE u.username =:username AND u.password =:password AND u.role =:role OR u.role =:role2";
       TypedQuery<User> tq = em.createQuery(query, User.class);
       tq.setParameter("username", username);
       tq.setParameter("password", password);
       tq.setParameter("role", role);
       tq.setParameter("role2", role2);
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
   
   public static boolean updatePassword(String username, String oldPassword, String newPassword) {
	    EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();

	        String query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
	        TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
	        getUserQuery.setParameter("username", username);
	        getUserQuery.setParameter("password", oldPassword);

	        User userToUpdate = getUserQuery.getSingleResult();

	        if (newPassword != null && isPasswordValid(newPassword)) {
	            userToUpdate.setPassword(newPassword);
	            em.merge(userToUpdate);
	            et.commit();
	            return true;
	        } else {
	           
	            et.rollback();
	            return false;
	        }

	    } catch (NoResultException e) {
	        
	        et.rollback();
	        return false;
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (et.isActive()) {
	            et.rollback();
	        }
	    } finally {
	        em.close();
	    }

	    return false;
	}

   
   public static boolean loginAdmin(String username , String password)
   {
       EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
       String role = "ADMIN";
       String query = "SELECT u FROM User u WHERE u.username =:username AND u.password =:password AND u.role =:role";
       TypedQuery<User> tq = em.createQuery(query, User.class);
       tq.setParameter("username", username);
       tq.setParameter("password", password);
       tq.setParameter("role", role);
       User logedUser = null;
       try{
    	   logedUser = tq.getSingleResult();
           loginTries = 2;
           return true;
       }catch(Exception e)
       {
    	   System.out.println("ADMIN LOGIN ERROR");
           loginTries -- ;  
           
       }
       finally{
           em.close();   
       }
       return false;
   }
   
   public static boolean updateUserInfo(String username, String newUsername ,String newName, String newSurname, String newEmail, String newPhone) {
	    EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
	    EntityTransaction et = em.getTransaction();
	    try {
	        et.begin();

	        String query = "SELECT u FROM User u WHERE u.username = :username";
	        TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
	        getUserQuery.setParameter("username", username);
	        User userToUpdate = getUserQuery.getSingleResult();
	        if (!username.isBlank() && isUsernameValid(newUsername)) 
	        {
	        	if(newUsername != null) {
	        		userToUpdate.setUsername(newUsername);
	        	}
	            if (newName != null) {
	                userToUpdate.setName(newName);
	            }
	            if (newSurname != null) {
	                userToUpdate.setSurname(newSurname);
	            }
	            if (newEmail != null) {
	                userToUpdate.setEmail(newEmail);
	            }
	            if (newPhone != null) {
	                userToUpdate.setPhone(newPhone);
	            }
	        }

	            em.merge(userToUpdate);
	            et.commit();
	            
	            return true;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (et.isActive()) {
	            et.rollback();
	        }
	    } finally {
	        em.close();
	    }
	    
	    return false;
	}
   
   public static boolean deleteUser(String username) {
       EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
       EntityTransaction et = em.getTransaction();

       try {
           et.begin();

           String query = "SELECT u FROM User u WHERE u.username = :username";
           TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
           getUserQuery.setParameter("username", username);
           User userToDelete = getUserQuery.getSingleResult();

           em.remove(userToDelete);
           et.commit();

           return true;
       } catch (Exception e) {
           e.printStackTrace();
           if (et.isActive()) {
               et.rollback();
           }
       } finally {
           em.close();
       }

       return false;
   }
   
   public static boolean updateStatus(String username , String status) {
	    EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
	    EntityTransaction et = em.getTransaction();
	    try {
	        et.begin();

	        String query = "SELECT u FROM User u WHERE u.username = :username";
	        TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
	        getUserQuery.setParameter("username", username);
	        User userToUpdate = getUserQuery.getSingleResult();
	        userToUpdate.setUser_status(status);
	        	

	            em.merge(userToUpdate);
	            et.commit();
	            
	            return true;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (et.isActive()) {
	            et.rollback();
	        }
	    } finally {
	        em.close();
	    }
	    
	    return false;
	}

}
