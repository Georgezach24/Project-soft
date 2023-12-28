package gr.conference.usersystem;

/**
*
* @author Giorgos Zachos
*/


import javax.persistence.*;


@Entity
@Table(name="users")
public class User{
   
   @Id
   @Column(name="username", nullable = false)
   private String username;
   
   @Column(name="name")
   private String name;
   
   @Column(name="surname")
   private String surname;
   
   @Column(name="password", nullable = false)
   private String password;
   
   @Column(name="email")
   private String email;
   
   @Column(name="phone")
   private String phone;
   
   @Column(name="user_status")
   private String user_status;
   
   @Column(name="role")
   private String role;

   public String getUsername() {
       return username;
   }

   public void setUsername(String username) {
       this.username = username;
   }

   public String getPassword() {
       return password;
   }

   public void setPassword(String password) {
       this.password = password;
   }

   public String getEmail() {
       return email;
   }

   public void setEmail(String email) {
       this.email = email;
   }

   public String getPhone() {
       return phone;
   }

   public void setPhone(String phone) {
       this.phone = phone;
   }

   public String getUser_status() {
       return user_status;
   }

   public void setUser_status(String user_status) {
       this.user_status = user_status;
   }

   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }

   public String getSurname() {
       return surname;
   }

   public void setSurname(String surname) {
       this.surname = surname;
   }

   public String getRole() {
       return role;
   }

   public void setRole(String role) {
       this.role = role;
   }
   
   
   
   
}
