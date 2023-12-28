
package com.conference.users;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jakarta.persistence.PersistenceContextType;

public class UserDBHandler{

	
	public static EntityManagerFactory ENITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("Conference");
    public static int loginTries = 2; 
    private static int flag = 0;
    
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

   
   
    public static void registerUser(String username, String password, String email, String phone) {
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

        if (existingUserCount == 0 && !username.isBlank() && !password.isBlank()) {
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
            flag = 1;
            JOptionPane.showMessageDialog(j, "User registered successfully!");
        } else {
            JOptionPane.showMessageDialog(j, "Unable to register user. User may already exist or invalid input.");
            
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(j, "Error registering User");
    } finally {
        if (et.isActive()) {
            et.rollback();
        }
        em.close();
    }
}
    
    public static boolean registerCompleted()
    {
    	if(flag == 1)
    	{
    		return true;
    	}
    	
    	return false;
    }
}
