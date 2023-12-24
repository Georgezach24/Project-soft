/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.authentication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Giorgos Zachos
 */
public class DBHandler extends JFrame{
    public static EntityManagerFactory ENITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("Authentication");
    public static int loginTries = 2;
    
    public static void registerUser(String username , String password , String email , String phone , JFrame j)
    {
        EntityManager em = ENITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try
        {
            JFrame f = new JFrame();
            et = em.getTransaction();
            et.begin();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setPhone(phone);
//--------- Χρήση στον έλεγχο για το αν υπάρχει ήδη εγγεγραμμένος χρήστης.-----
            String query = "SELECT u FROM User u WHERE u.username =:username "; 
            TypedQuery<User> tq = em.createQuery(query, User.class);
            tq.setParameter("username", username);          
//-----------------------------------------------------------------------------
            if(!user.getUsername().isBlank() && 
            !user.getPassword().isBlank() && 
            !user.getEmail().isBlank() && 
            tq.getSingleResult().getUsername().isBlank())
            {
                em.persist(user);
                et.commit();
                et = em.getTransaction();
                et.begin();
                JOptionPane.showMessageDialog(f, "User registered succesfully!");
                j.dispose();
                StartingWindow sw = new StartingWindow();
                sw.setVisible(true);

            }
            else if (!user.getUsername().isBlank() && !user.getPassword().isBlank() && 
            !user.getEmail().isBlank())
            {
                JOptionPane.showMessageDialog(f,"You cannot Register without providing the adequate data");
            }
            else
            {
                JOptionPane.showMessageDialog(f,"User is already register, try to login");
            }

        }   
        catch(Exception e)
        {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,"Error registering User");
        } 
        finally
        {
            em.close();
        }
    }
    
    public static void loginUser(String username , String password , JFrame j)
    {
        EntityManager em = ENITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT u FROM User u WHERE u.username =:username AND u.password =:password";
        TypedQuery<User> tq = em.createQuery(query, User.class);
        tq.setParameter("username", username);
        tq.setParameter("password", password);
        User user = null;
        try{
            user = tq.getSingleResult();
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Login succesfull");
            j.dispose();
            AuthenticatedUserMainWindow aumw = new AuthenticatedUserMainWindow();
            aumw.setVisible(true);
            loginTries = 2;
        }catch(Exception e)
        {
            e.printStackTrace();
            loginTries -- ;
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,"Invalid username or password");
        }
        finally{
            em.close();
        }
        
    }
}
