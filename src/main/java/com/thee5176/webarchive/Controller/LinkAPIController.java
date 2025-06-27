package com.thee5176.webarchive.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thee5176.webarchive.Repository.LinkRepository;
import com.thee5176.webarchive.Service.LinkService;
import com.thee5176.webarchive.dto.LinkDTO;
import com.thee5176.webarchive.model.Link;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin(value={"http://localhost:5173"})
public class LinkAPIController {
	@Autowired 
	LinkRepository linkRepository;
	
	@Autowired
	LinkService linkService;
	
	@GetMapping("/bookmark/api")
	@Operation(summary = "Get all bookmarks", description = "Returns a list of all bookmarks")
	public List<Link> getBookmarksListView() {
		return linkRepository.findAll();
	}
	
	@GetMapping("/bookmark/api/name/{name}")
	@Operation(summary = "Get bookmark with name", description = "Returns a product as per the name")
	public Link getBookmarkByNameAPI(@PathVariable String name) {
		return linkRepository.findByName(name)
				.orElseThrow(() -> new RuntimeException("Bookmark with name " + name + " not found"));
	}
	

	@GetMapping("/bookmark/api/id/{id}")
	@Operation(summary = "Get bookmark with id", description = "Returns a product as per the id")
	public Link getBookmarkByIdAPI(@PathVariable long id) {
		return linkRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Bookmark with id " + id + " not found"));
	}
	
	@PutMapping("bookmark/api/update/{id}")
	@Operation(summary = "Update a product by id", description = "Returns a product as per the id")
	public ResponseEntity<?> updateLink(@RequestBody Link link, @PathVariable long id) {
		// 新しいのを古いの代わりに上書き：Update処理はRequestBodyからオブジェクトを受け取て、IDを設定して保存する
		if (linkRepository.existsById(id)) {
			link.setId(id);
			linkRepository.saveAndFlush(link);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/bookmark/api/create")
	public ResponseEntity<String> createBookmarkAPI(
			@ModelAttribute("bookmarkForm") @Validated LinkDTO linkDto, BindingResult result) {
		try {
			linkService.createLinkWithTag(linkDto);
			return new ResponseEntity<String>("New Bookmark created successfully", HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("bookmark/api/delete/{id}")
	@Operation(summary = "Delete a bookmark by id", description = "Delete a product as per the name")
	public ResponseEntity<?> deleteLink(@PathVariable long id) {
		if (linkRepository.existsById(id)) {
			linkRepository.deleteById(id);
			linkRepository.flush();
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
