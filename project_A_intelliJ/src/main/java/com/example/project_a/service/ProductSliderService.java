package com.example.project_a.service;

import com.example.project_a.model.Media;
import com.example.project_a.model.Product;
import com.example.project_a.model.ProductSlider;
import com.example.project_a.repository.ProductSliderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class ProductSliderService {

    @Autowired
    private ProductSliderRepository productSliderRepository;

    @Transactional
    public ProductSlider addSlider(Media media, Product product) {
        ProductSlider slider = new ProductSlider();
        slider.setMedia(media);
        slider.setProduct(product);
        return productSliderRepository.save(slider);
    }

    public List<ProductSlider> getSlidersByProduct(Product product) {
        return productSliderRepository.findByProduct(product);
    }

    public void removeSlider(ProductSlider slider) {
        productSliderRepository.delete(slider);
    }
}
