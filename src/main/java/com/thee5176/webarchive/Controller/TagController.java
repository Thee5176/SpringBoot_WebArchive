package com.thee5176.webarchive.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.Service.TagService;
import com.thee5176.webarchive.dto.TagDTO;
import com.thee5176.webarchive.model.Tag;

@RestController
@Controller
public class TagController {
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	TagService tagService;
	
	@GetMapping("/tag")
	public ModelAndView getTagListView(ModelAndView mav) {
		mav.setViewName("tag/main");
		List<Tag> tagList = tagRepository.findAll();
		mav.addObject("title", "Tag List");
		mav.addObject("object_list", tagList);
		return mav;
	}
	
	@GetMapping("/tag/name/{name}")
	public Tag getListByName(@PathVariable String name) {
		return tagRepository.findByName(name)
				.orElseThrow(() -> new RuntimeException("Tag not found"));
	}
	
	@PostMapping("/tag/create")
	public ResponseEntity<String> createTag(@ModelAttribute TagDTO tag) {
		try {
			tagService.createTag(tag);
			return new ResponseEntity<String>("New Tag created succesfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("/tag/update/{id}")
	public ResponseEntity<?> updateTag(@RequestBody Tag tag, @PathVariable long id) {
		if ( tagRepository.existsById(id) ) {
			tag.setId(id);
			tagRepository.save(tag);
			return ResponseEntity.ok("OK");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/tag/delete/{id}")
	public ResponseEntity<?> deleteTag(@PathVariable long id) {
		if ( tagRepository.existsById(id) ) {
			tagRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
