package com.springframework.project.service;

import java.io.IOException;
import java.util.List;

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
		
//		Uploading the file 
		
		String uploadedFileName = fileService.uploadFile(path, file);
		
//		Setting the value of poster as filename 
		
		movieDto.setPoster(uploadedFileName);
		
//		Map DTO to movie object
		
		Movie movie = new Movie(
				
				movieDto.getMovieId(),
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

	@Override
	public MovieDto getMovie(Integer movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MovieDto> getAllMovies() {
		// TODO Auto-generated method stub
		return null;
	}

}
