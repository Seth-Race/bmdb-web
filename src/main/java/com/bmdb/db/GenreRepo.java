package com.bmdb.db;

import org.springframework.data.repository.CrudRepository;

import com.bmdb.business.Genre;

public interface GenreRepo extends CrudRepository<Genre, Integer> {

	Genre findByName(String genreName);
	
}
