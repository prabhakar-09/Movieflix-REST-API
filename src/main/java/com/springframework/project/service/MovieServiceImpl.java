package com.springframework.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
//import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springframework.project.dto.MovieDto;
import com.springframework.project.entity.Movie;
import com.springframework.project.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService{
	
	private final MovieRepository movieRepository;
		
	private final FileService fileService;
	
	@Value("${project.poster}")
	private String path;
	
	@Value("${base.url}")
	private String baseUrl;
	
	public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) { // Constructor injection
		this.movieRepository = movieRepository;
		this.fileService = fileService;
		
	}

	@Override
	public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
		
		if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
			
			throw new RuntimeException("File with same name already exists");
		}
		
//		Uploading the file 
		
		String uploadedFileName = fileService.uploadFile(path, file);
		
//		Setting the value of poster as filename 
		
		movieDto.setPoster(uploadedFileName);
		
//		Map DTO to movie object
		
		Movie movie = new Movie(
				
				null,
				movieDto.getTitle(),
				movieDto.getDirector(),
				movieDto.getStudio(),
				movieDto.getMovieCast(),
				movieDto.getReleaseYear(),
				movieDto.getPoster()
		);
		
//		Save the movie object
		
		Movie savedMovie = movieRepository.save(movie);
		
//		Generating poster URL
		
		String  posterUrl = baseUrl + "/file" + uploadedFileName;
		
//		Mapping movie object to DTO object
		
		MovieDto response = new MovieDto(
		
				savedMovie.getMovieId(),
				savedMovie.getTitle(),
				savedMovie.getDirector(),
				savedMovie.getStudio(),				
				savedMovie.getMovieCast(),
				savedMovie.getReleaseYear(),
				savedMovie.getPoster(),
				posterUrl
		);
				
		
		return response;
	}

//	Getting movie with the ID
	@Override
	public MovieDto getMovie(Integer movieId) {
//		Check if the data exists in the DB with given ID & fetch it.
		
		Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("Movie not found."));		
		
//		Generate poster url 
		String posterUrl = baseUrl + "/file/"+ movie.getPoster();
		
//		Map to movie DTO object & return 
		MovieDto response = new MovieDto(
				
				movie.getMovieId(),
				movie.getTitle(),
				movie.getDirector(),
				movie.getStudio(),				
				movie.getMovieCast(),
				movie.getReleaseYear(),
				movie.getPoster(),
				posterUrl
		);
		return response;
	}

	@Override
	public List<MovieDto> getAllMovies() {
//		Fetch all the data 
		List<Movie> movies = movieRepository.findAll();
		
		List<MovieDto> movieDtos = new ArrayList<>();
		
//		Iterate through list & generate the poster url & map it to DTO
		
		for(Movie movie : movies) {
			
			String posterUrl = baseUrl + "/file/"+ movie.getPoster();
			
			MovieDto response = new MovieDto(
					
					movie.getMovieId(),
					movie.getTitle(),
					movie.getDirector(),
					movie.getStudio(),				
					movie.getMovieCast(),
					movie.getReleaseYear(),
					movie.getPoster(),
					posterUrl
			);
			
			movieDtos.add(response);
		}
		
		return movieDtos;
	}

	@Override
	public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
		
		return null;
	}

	@Override
	public String deleteMovie(Integer movieId) {
		// TODO Auto-generated method stub
		return null;
	}

}
