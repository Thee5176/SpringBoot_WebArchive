package com.thee5176.webarchive.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.Service.TagService;
import com.thee5176.webarchive.dto.TagDTO;
import com.thee5176.webarchive.model.Tag;

@RestController
public class TagAPIController {
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	TagService tagService;
	
	@GetMapping("/tag/api")
	public List<Tag> getTagListView() {
		return tagRepository.findAll();
	}
	
	@GetMapping("/tag/api/name/{name}")
	public Tag getListByName(@PathVariable String name) {
		return tagRepository.findByName(name)
				.orElseThrow(() -> new RuntimeException("Tag not found"));
	}
	
	@PutMapping("/tag/api/update/{id}")
	public ResponseEntity<?> updateTag(@RequestBody Tag tag, @PathVariable long id) {
		if ( tagRepository.existsById(id) ) {
			tag.setId(id);
			tagRepository.save(tag);
			return ResponseEntity.ok("OK");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/tag/api/create")
	public ResponseEntity<String> createTag(@ModelAttribute TagDTO tag) {
		try {
			tagService.createTag(tag);
			return new ResponseEntity<String>("New Tag created succesfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping("/tag/api/delete/{id}")
	public ResponseEntity<?> deleteTag(@PathVariable long id) {
		if ( tagRepository.existsById(id) ) {
			tagRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
