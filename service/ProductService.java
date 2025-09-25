package com.charan.e_commerse_web.service;

import com.charan.e_commerse_web.model.Product;
import com.charan.e_commerse_web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepository prodrepo;

    public List<Product> findAll() {
        return prodrepo.findAll();
    }
}
