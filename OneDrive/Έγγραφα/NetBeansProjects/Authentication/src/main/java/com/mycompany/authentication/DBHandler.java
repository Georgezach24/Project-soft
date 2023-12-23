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
    
    public static void registerUser(String username , String password , String email , String phone)
    {
        EntityManager em = ENITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try{        
        et = em.getTransaction();
        et.begin();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        em.persist(user);
        et.commit();
            et = em.getTransaction();
            et.begin();
        }catch(Exception e)
        {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,"Error registering User");
        } 
        finally{
            em.close();
        }
    }
    
    public static void loginUser(String username , String password)
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
        }catch(Exception e)
        {
            e.printStackTrace();
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,"Error Loging User");
        }
        finally{
            em.close();
        }
        
    }
}
