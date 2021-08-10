package com.bmdb.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bmdb.business.MovieCollection;

public interface MovieCollectionRepo extends CrudRepository<MovieCollection, Integer> {

	List<MovieCollection> findAllByUserId(int id);

}
