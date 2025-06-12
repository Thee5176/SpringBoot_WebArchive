package com.thee5176.webarchive.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thee5176.webarchive.Repository.LinkRepository;
import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.Service.LinkService;
import com.thee5176.webarchive.dto.LinkDTO;
import com.thee5176.webarchive.model.Link;
import com.thee5176.webarchive.model.Tag;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class LinkController {
	@Autowired
	LinkRepository linkRepository;

	@Autowired
	TagRepository tagRepository;

	@Autowired
	LinkService linkService;

	@GetMapping("/bookmark")
	public ModelAndView getBookmarksListView(ModelAndView mav) {
		mav.setViewName("bookmark/main");
		List<Link> bookmarkList = linkRepository.findAll();
		mav.addObject("title", "Webブックマーク一覧");
		mav.addObject("object_list", bookmarkList);
		return mav;
	}

	@GetMapping("/bookmark/id/{id}")
	public ModelAndView getDetailView(@PathVariable long id, ModelAndView mav) {
		mav.setViewName("bookmark/detail_modal");
		Link link = linkRepository.findById(id)
				.orElseThrow();
		mav.addObject("object", link);
		return mav;
	}


	@GetMapping("/form/bookmark")
	// dynamic tag selector
	public ModelAndView getBookmarkForm(ModelAndView mav) {
		mav.setViewName("bookmark/form");
		List<Tag> objectList = tagRepository.findAll();
		mav.addObject("object_list", objectList);

		return mav;
	}

	@PostMapping("/bookmark/create")
	public ModelAndView createBookmark(
			@ModelAttribute("bookmarkForm") @Validated LinkDTO link, 
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		Map<String,String> alert = new HashMap<>();
		if (!result.hasErrors()) {
			try {
					linkService.createLinkWithTag(link);
					
					alert.put("bscolor", "success");
					alert.put("message", "New Bookmark created successfully");
				} catch (RuntimeException e) {
					alert.put("bscolor", "danger");
					alert.put("message", e.getMessage());
				}
		} else {
			return new ModelAndView("redirect:/bookmark");
		}
		
		redirectAttributes.addFlashAttribute(alert);
		return new ModelAndView("redirect:/bookmark");
	}

	@DeleteMapping("bookmark/delete/{id}")
	@Operation(summary = "Delete a bookmark by id", description = "Delete a product as per the name")
	public ModelAndView deleteLink(@PathVariable long id,
			RedirectAttributes redirectAttributes) {
		Map<String,String> alert = new HashMap<>();
		if (linkRepository.existsById(id)) {
			
			linkRepository.deleteById(id);
			linkRepository.flush();
			
			alert.put("bscolor", "success");
			alert.put("message", "Bookmark deleted successfully");
		} else {
			alert.put("bscolor", "danger");
			alert.put("message", "Bookmark with id " + id + " not exist");
		}
		redirectAttributes.addFlashAttribute(alert);
		return new ModelAndView("redirect:/bookmark");
	}
}
