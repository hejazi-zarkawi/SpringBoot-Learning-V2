package com.hejazi.securityApp.securityApp.repositories;

import com.hejazi.securityApp.securityApp.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity,Long> {

}
