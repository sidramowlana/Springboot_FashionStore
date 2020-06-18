package com.example.FashionStore.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;
    private String tag;

    public Tag(String tag) {
        this.tag = tag;
    }
}
