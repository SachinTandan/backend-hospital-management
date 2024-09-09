package com.example.hospitalmanagement.Repo;


import com.example.hospitalmanagement.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UsersEntity, Integer> {

    UsersEntity findByUsername(String username);
}