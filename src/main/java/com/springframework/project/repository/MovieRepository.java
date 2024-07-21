package com.springframework.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springframework.project.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{

}
