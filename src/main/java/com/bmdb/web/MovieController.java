package com.bmdb.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import com.bmdb.business.Movie;
import com.bmdb.db.MovieRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/movies")
public class MovieController {

	@Autowired
	private MovieRepo movieRepo;

	@GetMapping("/")
	public Iterable<Movie> getAll() {
		return movieRepo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Movie> get(@PathVariable Integer id) {
		return movieRepo.findById(id);
	}

	@PostMapping("/")
	public Movie add(@RequestBody Movie movie) {
		return movieRepo.save(movie);
	}

	@PutMapping("/")
	public Movie update(@RequestBody Movie movie) {
		return movieRepo.save(movie);
	}

	@DeleteMapping("/{id}")
	public Optional<Movie> delete(@PathVariable int id) {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			try {
				movieRepo.deleteById(id);
			} catch (DataIntegrityViolationException dive) {
				// catch exception when movie exists as FK for another table
				System.err.println(dive.getRootCause().getMessage());
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Foreign Key Constraint Issue - Movie ID:" + id + " is referred to elsewhere.");
			} catch (Exception e) {
				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Exception caught during movie delete");
			}
		} else {
			System.err.println("Movie delete error - No movie found for ID:" + id);
		}
		return movie;
	}

	@GetMapping("/rating/get")
	public Iterable<Movie> getAllByMovieRating(@RequestParam String rating) {
		if (rating == null || rating.equals("")) {
			rating = "PG-13";
		}
		return movieRepo.findAllByRating(rating);
	}
	
}
