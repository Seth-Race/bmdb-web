package com.bmdb.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.bmdb.business.Movie;

public interface MovieRepo extends CrudRepository<Movie, Integer> {

	List<Movie> findAllByRating(String rating);
	
	
}
