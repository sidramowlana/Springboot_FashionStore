package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.ProductTag;
import com.example.FashionStore.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Integer> {

    ProductTag findByTag(Tag tag);
//   List<VehicleRentEquipments> findByRent(Rent rentId);

    Boolean existsByTag(Tag tagId);
}
