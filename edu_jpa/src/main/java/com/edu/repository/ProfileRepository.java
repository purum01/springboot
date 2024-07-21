package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
