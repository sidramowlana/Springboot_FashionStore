package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.Tag;
import com.example.FashionStore.Repositories.TagRepository;
import com.example.FashionStore.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/admin/tags")
@RestController
public class TagController {

    private TagService tagService;
    private TagRepository tagRepository;

    @Autowired
    public TagController(TagService tagService, TagRepository tagRepository) {
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/createTag")
    public ResponseEntity<?> addTag(@Valid @RequestBody Tag newTag){
        return tagService.addNewTag(newTag);
    }
    //check if the user also needs this
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/all")
    public List<Tag> getAllTags(){
        return tagService.getAllTags();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/all/{tagId}")
    public ResponseEntity<?> getTagById(@PathVariable Integer tagId){
        return tagService.getTagById(tagId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{tagId}")
    public void deleteTagById(@PathVariable Integer tagId){
        tagService.deleteTag(tagId);
    }
}
