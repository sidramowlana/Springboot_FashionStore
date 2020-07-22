package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.ProductTag;
import com.example.FashionStore.Models.Tag;
import com.example.FashionStore.Repositories.ProductTagRepository;
import com.example.FashionStore.Repositories.TagRepository;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/tags")
@RestController
public class TagController {

    private TagService tagService;
    private TagRepository tagRepository;
    private ProductTagRepository productTagRepository;

    @Autowired
    public TagController(TagService tagService, TagRepository tagRepository,ProductTagRepository productTagRepository) {
        this.tagService = tagService;
        this.tagRepository = tagRepository;
        this.productTagRepository = productTagRepository;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/createTag")
    public ResponseEntity<MessageResponse> addTag(@RequestBody Tag newTag){
        return tagService.addNewTag(newTag);
    }
    //check if the user also needs this
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin/all")
    public List<Tag> getAllTags(){
        return tagService.getAllTags();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/product-tags/all")
    public List<ProductTag> getAllProductTags(){
        return tagService.getAllProductTags();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/product-tags/{tagId}")
    public List<ProductTag> getAllProductsByTagId(@PathVariable Integer tagId){
        return tagService.getAllProductsByTagId(tagId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{tagId}")
    public void deleteTagById(@PathVariable Integer tagId){
        tagService.deleteTag(tagId);
    }
}
