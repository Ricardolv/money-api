package com.richard.money.api.resource;

import com.richard.money.api.model.Category;
import com.richard.money.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Category category, HttpServletResponse response) {
        Category categorySave = categoryRepository.save(category);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(categorySave.getCode()).toUri();

        response.setHeader("Location", uri.toASCIIString());
    }

    @GetMapping("/{code}")
    public Category searchCategoryByCode(@PathVariable Long code) {
        return categoryRepository.findOne(code);
    }

}
