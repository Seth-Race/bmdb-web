package com.bmdb.db;

import org.springframework.data.repository.CrudRepository;

import com.bmdb.business.User;

public interface UserRepo extends CrudRepository<User, Integer>{

}
