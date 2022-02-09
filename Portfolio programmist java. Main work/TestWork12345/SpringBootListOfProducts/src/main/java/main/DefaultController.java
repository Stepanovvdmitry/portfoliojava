package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class DefaultController {
    @RestController
    public class GeoController {

        @Autowired
        private ProductRepositry productRepositry;
        @Autowired
        private ListRepositrory listRepositrory;

        @GetMapping("/product")
        public List<Product> getAllProducts() {
            return productRepositry.getAll();
        }
        @GetMapping("/list")
        public List<ListOfProducts> getAllLists() {
            return listRepositrory.getAll();
        }
        @GetMapping("/list/{id}")
        public Product getListById(@PathVariable("id") int id) {
            return listRepositrory.getById(id).getProduct();
        }

        @PostMapping("/product")
        @ResponseStatus(HttpStatus.CREATED)
        public Product createProduct(@RequestBody Product product) {
            return productRepositry.create(product);
        }

        @PostMapping("/list")
        @ResponseStatus(HttpStatus.CREATED)
        public ListOfProducts createList(@RequestBody ListOfProducts listOfProducts) {
            return listRepositrory.create(listOfProducts);
        }

        @PostMapping("//list/{id}")
        @ResponseStatus(HttpStatus.CREATED)
        public ListOfProducts createList(@RequestBody ListOfProducts listOfProducts, @PathVariable("id") int id ) {
            return listRepositrory.create(listOfProducts, id);
        }


    }
}
