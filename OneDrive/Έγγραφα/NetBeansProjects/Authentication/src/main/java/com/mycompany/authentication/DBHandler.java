/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.authentication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
    }
}
