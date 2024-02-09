package com.example.demo.dao;

import com.example.demo.models.Admin;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminStorage {
    private static boolean isAdmin;
    private static EntityManagerFactory getEntity(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        return entityManagerFactory;
    }

    public boolean createAdminAccount(Admin admin)
    {
        EntityManagerFactory entityManagerFactory =  getEntity();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        String email= admin.getEmail();
        Query query = entityManager.createQuery("from Admin where email = :email");
        query.setParameter("email", email);
        List<Admin> adminList = query.getResultList();

        if(adminList.isEmpty())
        {entityManager.persist(admin);
            isAdmin = true;
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

    public boolean adminLogin(String email, String password)
    {
        EntityManagerFactory entityManagerFactory = getEntity();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("from Admin where email = :email");
        query.setParameter("email",email);
        List<Admin> adminList = query.getResultList();
        if (adminList.isEmpty())
        {entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
        }
        else {
            if (adminList.get(0).getPassword().equals(password))
            {
                isAdmin = true;
                entityManager.getTransaction().commit();
                entityManager.close();
                entityManagerFactory.close();
                return  true;
            }
        }
        return false;
    }


    public boolean addProduct(Product product)
    {
        EntityManagerFactory entityManagerFactory =  getEntity();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        if(isAdmin) {
            int productId = product.getProductId();
            Query query = entityManager.createQuery("from Product where productId = :productId");
            query.setParameter("productId", productId);
            List<Product> productList = query.getResultList();

            if (productList.isEmpty()) {
                entityManager.persist(product);
                entityManager.getTransaction().commit();
                entityManager.close();
                entityManagerFactory.close();
                return true;
            } else {
                entityManager.getTransaction().commit();
                entityManager.close();
                entityManagerFactory.close();
                return false;
            }
        }
        return false;
    }

    public ResponseEntity<List<Product>> getProduct()
    {
        EntityManagerFactory entityManagerFactory =  getEntity();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("from Product");
            if (isAdmin)
              return new ResponseEntity<>(query.getResultList(), HttpStatus.OK);

        } catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public boolean updateProduct(Product product)
    {
        EntityManagerFactory entityManagerFactory = getEntity();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            int productId = product.getProductId();
            Query query = entityManager.createQuery("from Product where productId = :productId");
            query.setParameter("productId",productId);
            List<Product> productList = query.getResultList();

            String name = product.getName();
            float price = product.getPrice();
            if (!productList.isEmpty() && isAdmin)
            {
                Query query1 = entityManager.createQuery("update Product set name = :name, price = :price where productId= :productId");
                query1.setParameter("productId", productId);
                query1.setParameter("name",name);
                query1.setParameter("price",price);
                query1.executeUpdate();
                return true;
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
        }
        return false;
    }

    public ResponseEntity<String> deleteProduct(int productId)
    {
        EntityManagerFactory entityManagerFactory = getEntity();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("from Product where productId = :productId");
            query.setParameter("productId",productId);
            List<Product> productList = query.getResultList();

            if(!productList.isEmpty() && isAdmin)
            {
                Query query1 = entityManager.createQuery("delete from Product where productId =: productId");
                query1.setParameter("productId",productId);
                return new ResponseEntity<>("Product deleted successfully",HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Product not exist",HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally {
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();
        }
        return new ResponseEntity<>("Something went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
