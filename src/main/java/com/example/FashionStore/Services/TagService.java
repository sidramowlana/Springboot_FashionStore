package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Tag;
import com.example.FashionStore.Repositories.TagRepository;
import com.example.FashionStore.Response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    //add new tag
    public ResponseEntity<?> addNewTag(Tag newTag) {
        if (tagRepository.existsByTag(newTag.getTag())){
            return ResponseEntity.badRequest().body(new MessageResponse("Tag already exist!!!"));
        }
        Tag tag = new Tag(
                newTag.getTag()
        );
        tagRepository.save(tag);
        return ResponseEntity.ok().body(new MessageResponse("Successfully Tag Added"));
    }

    //delete a tag
    public void deleteTag(Integer id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
        }
    }

    //get all tags
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    //get a tag by name
    public ResponseEntity<?> getTagByName(String tagName) {
        if (!tagRepository.existsByTag(tagName)) {
            return ResponseEntity.ok().body(new MessageResponse("Tag not available!!!"));
        } else {
            Tag tag = tagRepository.findByTag(tagName);
            return ResponseEntity.ok().body(tag);
        }
    }

    // get equipment by id
    public ResponseEntity<?> getTagById(Integer id) {
        if (!tagRepository.existsById(id)) {
            return ResponseEntity.ok().body(new MessageResponse("Tag not available!!!"));
        } else {
            Tag tag = tagRepository.findById(id).get();
            return ResponseEntity.ok().body(tag);
        }
    }
}
