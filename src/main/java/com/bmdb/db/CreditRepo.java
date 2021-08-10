package com.bmdb.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bmdb.business.Credit;

public interface CreditRepo extends CrudRepository<Credit, Integer> {

	// Custom query - get all credits by movie id
	List<Credit> findAllByMovieId(int id);
	
}
