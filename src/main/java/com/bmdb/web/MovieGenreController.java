package com.bmdb.web;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.Genre;
import com.bmdb.business.MovieGenre;
import com.bmdb.db.GenreRepo;
import com.bmdb.db.MovieGenreRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/movie-genre")
public class MovieGenreController {
	
	@Autowired
	public MovieGenreRepo movieGenreRepo;
	@Autowired
	public GenreRepo genreRepo;
	
	@GetMapping("/")
	public Iterable<MovieGenre> getAll(){
		return movieGenreRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<MovieGenre> get(@PathVariable Integer id) {
		return movieGenreRepo.findById(id);
	}
	
	@PostMapping("/")
	public MovieGenre add(@RequestBody MovieGenre movieGenre) {
		return movieGenreRepo.save(movieGenre);
	}
	
	@PutMapping("/")
	public MovieGenre update(@RequestBody MovieGenre movieGenre) {
		return movieGenreRepo.save(movieGenre);
	}
	
	@DeleteMapping("/{id}")
	public Optional<MovieGenre> delete(@PathVariable int id) {
		Optional<MovieGenre> movieGenre = movieGenreRepo.findById(id);
		if (movieGenre.isPresent()) {
			try {
				movieGenreRepo.deleteById(id);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Exception caught during moviegenre delete");
			}
		} else {
			System.err.println("MovieGenre delete error - No moviegenre found for ID:" + id);
		}
		return movieGenre;
	}

	@GetMapping("/genre/get")
	public List<MovieGenre> getAllByGenreStr(@RequestParam String genreStr) {
		Genre genre = genreRepo.findByName(genreStr);
		return movieGenreRepo.findAllMovieByGenreId(genre.getId());
		
	}

}
