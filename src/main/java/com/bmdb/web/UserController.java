package com.bmdb.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.User;
import com.bmdb.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepo userRepo;

	@GetMapping("/")
	public Iterable<User> getAll() {
		return userRepo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<User> get(@PathVariable Integer id) {
		return userRepo.findById(id);
	}
	
	@PostMapping("/login")
	public Optional<User> login(@RequestBody User u) {
		return userRepo.findByUsernameAndPassword(u.getUsername(), u.getPassword());
	}

	@PostMapping("/")
	public User add(@RequestBody User user) {
		return userRepo.save(user);
	}

	@PutMapping("/")
	public User update(@RequestBody User user) {
		return userRepo.save(user);
	}

	@DeleteMapping("/{id}")
	public Optional<User> delete(@PathVariable int id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			try {
				userRepo.deleteById(id);
			} catch (DataIntegrityViolationException dive) {
				// catch exception when user exists as FK for another table
				System.err.println(dive.getRootCause().getMessage());
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Foreign Key Constraint Issue - User ID:" + id + " is referred to elsewhere.");
			} catch (Exception e) {
				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Exception caught during user delete");
			}
		} else {
			System.err.println("User delete error - No user found for ID:" + id);
		}
		return user;
	}
	
}
