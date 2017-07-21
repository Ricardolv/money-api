package com.richard.money.api.resource;

import com.richard.money.api.event.ResourceCreateEvent;
import com.richard.money.api.model.Category;
import com.richard.money.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Category> listAll() {
        return this.categoryService.findAll();
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category>  create(@Valid @RequestBody Category category, HttpServletResponse response) {
        Category categorySave = this.categoryService.save(category);
        this.publisher.publishEvent(new ResourceCreateEvent(this, response, categorySave.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySave);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Category> searchCategoryByCode(@PathVariable Long code) {
        Category category = this.categoryService.findOne(code);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

}
