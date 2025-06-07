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
import org.springframework.web.servlet.ModelAndView;

import com.thee5176.webarchive.Repository.LinkRepository;
import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.Service.LinkService;
import com.thee5176.webarchive.model.Link;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@Controller
public class LinkController {
	@Autowired
	LinkRepository linkRepository;
	
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	LinkService linkService;
	
	@GetMapping("/bookmark")
	@Operation(summary = "Get all bookmark link from schema", description = "Returns all bookmark links stored in the database.")
	public ModelAndView getAllBookmarks(ModelAndView mav) {
		List<Link> bookmarkList = linkRepository.findAll();
		mav.addObject("object_list",bookmarkList);
		mav.setViewName("main");
		return mav;
	}
	
	@GetMapping("/bookmark/name/{name}")
	@Operation(summary = "Get bookmark with name", description = "Returns a product as per the name")
	public ModelAndView getBookmark(@PathVariable String name, ModelAndView mav) {
		Link bookmark = linkRepository.findByName(name)
				.orElseThrow();
		mav.addObject("object", bookmark);
		return mav;
	}
	
	@GetMapping("/bookmark/id/{id}")
	@Operation(summary = "Get bookmark with id", description = "Returns a product as per the id")
	public ModelAndView getLinkById(@PathVariable long id, ModelAndView mav) {
		Link bookmark = linkRepository.findById(id)
				.orElseThrow();
		mav.addObject("object", bookmark);
		return mav;
	}
	
	@PostMapping("/link/create")
	public ResponseEntity<String> createLink(@RequestBody Link link) {
		try {
				linkService.saveLinkWithTag(link);
				return new ResponseEntity<String>("New Bookmark created successfully", HttpStatus.CREATED);
			}
		catch (RuntimeException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("bookmark/update/{id}")
	@Operation(summary = "Update a product by id", description = "Returns a product as per the id")
	public ResponseEntity<?> updateLink(@RequestBody Link link, @PathVariable long id) {
		//新しいのを古いの代わりに上書き：Update処理はRequestBodyからオブジェクトを受け取て、IDを設定して保存する
		if ( linkRepository.existsById(id) ) {
			link.setId(id);
			linkRepository.save(link);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("bookmark/delete/{id}")
	@Operation(summary = "Delete a bookmark by id", description = "Delete a product as per the name")
	public ResponseEntity<?> deleteLink(@PathVariable long id) {
		if ( linkRepository.existsById(id) ) {
			linkRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
