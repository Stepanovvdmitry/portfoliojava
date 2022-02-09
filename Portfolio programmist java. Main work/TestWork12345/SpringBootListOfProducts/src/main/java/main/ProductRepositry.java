package main;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductRepositry {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> getAll() {
        return entityManager.createQuery("from Product p order by p.id desc", Product.class).getResultList();
    }

    public Product getById(int id) {
        return entityManager.find(Product.class, id);
    }

    public Product create(Product product) {
        for (ListOfProducts listOfProducts : product.getListOfProducts()) {
            listOfProducts.setProduct(product);
        }
        entityManager.persist(product);
        return product;
    }
}
