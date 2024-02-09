package com.example.demo.dao;

import com.example.demo.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserStorage {
    private boolean isUser;
    private static EntityManagerFactory getEntity(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        return entityManagerFactory;
    }

    public boolean createAccount(User user)
    {
//        EntityManagerFactory entityManagerFactory =  getEntity();
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//
//        String email = user.getEmail();
//        if(email!=null)
//        {
//            List<String> userList = new ArrayList<>();
//            if(userList.contains(email))
//            {
//                entityManager.getTransaction().commit();
//                entityManager.close();
//                entityManagerFactory.close();
//                return false;
//            }
//            else {
//                userList.add(email);
//                entityManager.persist(user);
//                entityManager.getTransaction().commit();
//                entityManager.close();
//                entityManagerFactory.close();
//                return true;
//            }
//        }
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        entityManagerFactory.close();
//        return false;

        EntityManagerFactory entityManagerFactory =  getEntity();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String email= user.getEmail();
        Query query = entityManager.createQuery("from User where email = :email");
        query.setParameter("email", email);
        List<User> userList = query.getResultList();

        if(userList.isEmpty())
        {entityManager.persist(user);
            isUser = true;
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
            return true;}
        else {
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
            return false;
        }

    }

    public boolean login(String email,String password) {
        EntityManagerFactory entityManagerFactory = getEntity();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("from User where email = :email");
        query.setParameter("email", email);
        List<User> userList = query.getResultList();

        if(userList.isEmpty())
        {
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();

        }
        else {
            if( userList.get(0).getPassword().equals(password))
            {
                isUser = true;
                entityManager.getTransaction().commit();
                entityManager.close();
                entityManagerFactory.close();
                return  true;
            }
        }
        return false;
    }


}
