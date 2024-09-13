package gr.conference.usersys;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class UserDBHandler {

    public static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("sys");
    public static int loginTries = 3;

    // Method to validate password format
    public static boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!*@#$%^&+=]).{8,}$";
        return password.matches(passwordPattern);
    }

    // Method to validate username format
    public static boolean isUsernameValid(String username) {
        String usernamePattern = "^[a-zA-Z][a-zA-Z0-9_]{4,}$";
        return username.matches(usernamePattern);
    }

    // Unified login method that checks user role (either "ADMIN" or "USER")
    public static String loginUser(String username, String password) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);

        try {
            User user = query.getSingleResult();
            // Compare hashed input password with stored hashed password
            if (user.getPassword().equals(hashPassword(password))) {
                // Return the role of the user (either "ADMIN" or "USER")
                return user.getRole();
            } else {
                return null; // Incorrect password
            }
        } catch (NoResultException e) {
            return null; // No user found
        } finally {
            em.close();
        }
    }

    // Register admin with hashed password
    public static void registerAdmin() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();

        if (!isAdminRegistered("admin")) {
            try {
                et.begin();
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(hashPassword("admin")); // Hash the password before storing
                adminUser.setRole("ADMIN");
                em.persist(adminUser);
                et.commit();
            } catch (Exception e) {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
    }

    public static boolean isAdminRegistered(String username) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
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

    // Register new user with hashed password
    public static void registerUser(String username, String password, String password2, String email, String phone) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
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
                newUser.setPassword(hashPassword(password)); // Hash the password before storing
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

    // Update user information
    public static boolean updateUserInfo(String oldUsername, String newUsername, String newName, String newSurname, String newEmail, String newPhone) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            // Fetch user by the old username
            String query = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
            getUserQuery.setParameter("username", oldUsername);
            User userToUpdate = getUserQuery.getSingleResult();

            // Update user information
            if (newUsername != null && !newUsername.isBlank() && isUsernameValid(newUsername)) {
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

            // Merge updated user info back into the database
            em.merge(userToUpdate);
            et.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (et.isActive()) {
                et.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // Update user password
    public static boolean updatePassword(String username, String oldPassword, String newPassword) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            // Fetch user by username and old password
            String query = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
            getUserQuery.setParameter("username", username);

            User userToUpdate = getUserQuery.getSingleResult();

            // Check if the old password matches
            if (userToUpdate.getPassword().equals(hashPassword(oldPassword)) && isPasswordValid(newPassword)) {
                userToUpdate.setPassword(hashPassword(newPassword)); // Hash the new password before storing
                em.merge(userToUpdate);
                et.commit();
                return true;
            } else {
                et.rollback();
                return false;
            }
        } catch (NoResultException e) {
            if (et.isActive()) {
                et.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // Update user status
    public static boolean updateStatus(String username, String status) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            // Fetch user by username
            String query = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
            getUserQuery.setParameter("username", username);

            User userToUpdate = getUserQuery.getSingleResult();
            userToUpdate.setUser_status(status); // Update user status

            em.merge(userToUpdate);
            et.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (et.isActive()) {
                et.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // Delete user by username
    public static boolean deleteUser(String username) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            // Fetch user by username
            String query = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> getUserQuery = em.createQuery(query, User.class);
            getUserQuery.setParameter("username", username);

            User userToDelete = getUserQuery.getSingleResult();
            em.remove(userToDelete); // Delete the user

            et.commit();
            return true;
        } catch (NoResultException e) {
            if (et.isActive()) {
                et.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // Utility method to hash the password using SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
