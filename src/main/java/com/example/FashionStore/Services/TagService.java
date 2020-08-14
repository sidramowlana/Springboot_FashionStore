package com.example.FashionStore.Services;

import com.example.FashionStore.Models.ProductTag;
import com.example.FashionStore.Models.Tag;
import com.example.FashionStore.Repositories.ProductTagRepository;
import com.example.FashionStore.Repositories.TagRepository;
import com.example.FashionStore.Response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private TagRepository tagRepository;
    private ProductTagRepository productTagRepository;

    public TagService(TagRepository tagRepository, ProductTagRepository productTagRepository) {
        this.tagRepository = tagRepository;
        this.productTagRepository = productTagRepository;
    }

    //add new tag
    public ResponseEntity<MessageResponse> addNewTag(Tag newTag) {
        if (tagRepository.existsByTag(newTag.getTag())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Tag already exist!!!"));
        }
        Tag tag = new Tag(
                newTag.getTag()
        );
        tagRepository.save(tag);
        return ResponseEntity.ok().body(new MessageResponse("Successfully Tag Added"));
    }

    //delete a tag
    public ResponseEntity<?> deleteTag(Integer id) {
        if (tagRepository.existsById(id)) {
            if(!productTagRepository.existsByTagTagId(id)) {
                tagRepository.deleteById(id);
                return ResponseEntity.ok().body(new MessageResponse("Successfully category deleted"));
            }
            else
            {
                return ResponseEntity.badRequest().body(new MessageResponse("Cannot delete category"));
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("No category found"));
    }

    //get all tags
    public List<Tag> getAllTags() {
//        List<Tag> tagsList = tagRepository.findAll();
        List<Tag> tagsList = tagRepository.findAllByOrderByTagIdDesc();

        return tagsList;
    }

    public List<ProductTag> getAllProductTags() {
        List<ProductTag> list = productTagRepository.findAll();
        return list;
    }

    public List<ProductTag> getAllProductsByTagId(Integer tagId){
        List<ProductTag> list = productTagRepository.findByTagTagId(tagId);
        System.out.println(list);
        return list;
    }
}