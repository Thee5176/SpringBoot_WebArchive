package com.thee5176.webarchive.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.model.Tag;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@Controller
public class TagController {
	@Autowired
	TagRepository tagRepository;
	
	@Operation(summary="Get all tags from schema")
	@GetMapping("/tags")
	public List<Tag> getTags() {
		return tagRepository.findAll();
	}
	
	@GetMapping("/tags/name/{name}")
	public Tag getListByName(@PathVariable String name) {
		return tagRepository.findByName(name)
				.orElseThrow(() -> new RuntimeException("Tag not found"));
	}
	
	@PostMapping("/tags/create")
	public ResponseEntity<String> createTag(@RequestBody Tag tag) {
		if ( !tagRepository.existsByIdOrName(tag.getId(), tag.getName()) ) {
			tagRepository.save(tag);
			return new ResponseEntity<String>("New Tag created succesfully", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error Tag already exist", HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("/tags/update/{id}")
	public ResponseEntity<?> updateTag(@RequestBody Tag tag, @PathVariable long id) {
		if ( tagRepository.existsById(id) ) {
			tag.setId(id);
			tagRepository.save(tag);
			return ResponseEntity.ok("OK");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/tags/delete/{id}")
	public ResponseEntity<?> deleteTag(@PathVariable long id) {
		if ( tagRepository.existsById(id) ) {
			tagRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
