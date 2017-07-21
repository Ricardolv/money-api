package com.richard.money.api.resource;

import com.richard.money.api.model.Category;
import com.richard.money.api.repository.CategoryRepository;
import com.richard.money.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {


    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> listAll() {
        return categoryService.findAll();
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category>  create(@RequestBody Category category, HttpServletResponse response) {
        Category categorySave = categoryService.save(category);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(categorySave.getCode()).toUri();

        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(categorySave);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Category> searchCategoryByCode(@PathVariable Long code) {
        Category category = categoryService.findOne(code);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

}
