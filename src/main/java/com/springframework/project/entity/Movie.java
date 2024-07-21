package com.springframework.project.entity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name ="Movie")
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer movieId;
	
//	Adding database level validation
	@Column(nullable= false, length=200)
	@NotBlank(message = "Please provide movie title")
	private String title;
	
	@Column(nullable= false)
	@NotBlank(message= "Please provide movie director")
	private String director;
	
	@Column(nullable= false)
	@NotBlank(message= "Please provide movie studio")
	private String studio;
	
	@ElementCollection
	@CollectionTable(name= "movie_cast")
	private Set<String> movieCast; // On DB level we create new table for this with foreign key movieId
	
	@Column(nullable= false)
	@NotBlank(message= "Please provide movie's release year")
	private Integer releaseYear;
	
	@Column(nullable= false)
	@NotBlank(message="Please provide movie's poster")
	private String poster;
}
