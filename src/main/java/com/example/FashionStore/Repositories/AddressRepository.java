package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Address;
import com.example.FashionStore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findByUserUserId(Integer userId);

    boolean existsByAddressAndCityAndUserUserId(String address,String city, Integer userId);
    Address findByAddressAndCityAndUserUserId(String address,String city, Integer userId);
}
