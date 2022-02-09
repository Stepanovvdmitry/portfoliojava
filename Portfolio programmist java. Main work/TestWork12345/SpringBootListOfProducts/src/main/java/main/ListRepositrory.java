package main;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ListRepositrory {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ProductRepositry productRepositry;

    public List<ListOfProducts> getAll() {
        return entityManager.createQuery("from List l order by l.id desc", ListOfProducts.class).getResultList();
    }

    public ListOfProducts getById(int id) {
        ListOfProducts listOfProducts = entityManager.find(ListOfProducts.class, id);
        return listOfProducts;
    }

    public ListOfProducts create(ListOfProducts listOfProducts) {
        return listOfProducts;
    }

    public ListOfProducts create(ListOfProducts listOfProducts, int id) {
        if (productRepositry.getById(id) != null) {
            listOfProducts.setProduct(productRepositry.getById(id));
            entityManager.persist(listOfProducts.getProduct());
            return listOfProducts;
        } else {
            throw new IllegalArgumentException("Продукты с таким id нет: " + id);
        }
    }
}
