package com.thee5176.webarchive.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thee5176.webarchive.Repository.LinkRepository;
import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.Service.LinkService;
import com.thee5176.webarchive.dto.AlertMessage;
import com.thee5176.webarchive.dto.LinkDTO;
import com.thee5176.webarchive.model.Link;
import com.thee5176.webarchive.model.Tag;

import mapper.LinkDTOMapper;

import org.springframework.stereotype.Controller;

@Controller
public class LinkController {
	@Autowired
	LinkRepository linkRepository;

	@Autowired
	TagRepository tagRepository;

	@Autowired
	LinkService linkService;

	@GetMapping("/bookmark")
	public ModelAndView getBookmarksListView(ModelAndView mav) {
		mav.setViewName("base");
		List<Link> bookmarkList = linkRepository.findAll();
		mav.addObject("title", "Webブックマーク一覧");
		mav.addObject("object_list", bookmarkList);
		mav.addObject("dynamicFragment","/bookmark/listView");
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


	@GetMapping(value={"/bookmark/form","/bookmark/form/{modalFlag}"})
	// dynamic tag selector
	public ModelAndView getBookmarkForm(@PathVariable Optional<String> modalFlag,
			ModelAndView mav) {
		List<Tag> objectList = tagRepository.findAll();
		mav.addObject("object_list", objectList);

		LinkDTO emptyDto = new LinkDTO("", "", "", 0L);
		mav.addObject("formData", emptyDto);
		mav.setViewName("base");
		mav.addObject("dynamicFragment", "/bookmark/formView");
		return mav;
	}

	@PostMapping("/bookmark/create")
	public ModelAndView createBookmark(
			@ModelAttribute("formData") @Validated LinkDTO linkDto, 
			BindingResult result,
			RedirectAttributes redirectAttributes,
			ModelAndView mav, 
			AlertMessage alertMessage
		) {
			if (!result.hasErrors()) {
				try {
					linkService.createLinkWithTag(linkDto);
					
					//No Exception -> return redirect
					mav.setViewName("redirect:/bookmark");
				
					alertMessage = new AlertMessage("success","New Bookmark created successfully");
				} catch (RuntimeException e) {
					//Exception -> render form with previous data
					mav.addObject("formData", linkDto);
					mav.setViewName("base");
					
					//return form
					List<Tag> objectList = tagRepository.findAll();
					mav.addObject("object_list", objectList);
					mav.addObject("dynamicFragment","/bookmark/formView");

					alertMessage = new AlertMessage("danger",e.getMessage());
				} finally {
					mav.addObject("alert",alertMessage);
				}

		} else {
			// Validation Error -> Show Error Message(View Layer)
			mav.addObject("formData", linkDto);
			mav.setViewName("base");
			
			//return form
			List<Tag> objectList = tagRepository.findAll();
			mav.addObject("object_list", objectList);
			mav.addObject("dynamicFragment","/bookmark/formView");
		}
		
		return mav;
	}

	@GetMapping("bookmark/update/{id}")
	public ModelAndView updateBookmark(
		@PathVariable long id,
		AlertMessage alertMessage,
		ModelAndView mav
	) {
		mav.setViewName("base");
		mav.addObject("dynamicFragment", "/bookmark/formView");

		List<Tag> objectList = tagRepository.findAll();
		mav.addObject("object_list", objectList);
		try {
			LinkDTO retrievedLinkDto = LinkDTOMapper.map(
				linkRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Bookmark with id" + id + "not exist")));
			mav.addObject("formData", retrievedLinkDto);
		} catch (Exception e) {
			alertMessage = new AlertMessage("danger", e.getMessage());
			mav.setViewName("redirect:/bookmark");
		}

		return mav;
	}

	@DeleteMapping("bookmark/delete/{id}")
	public ModelAndView deleteLink(@PathVariable long id,
		AlertMessage alertMessage,
		ModelAndView mav) {
		if (linkRepository.existsById(id)) {
			
			linkRepository.deleteById(id);
			linkRepository.flush();
			
			alertMessage = new AlertMessage("success","Bookmark deleted successfully");
		} else {
			alertMessage = new AlertMessage("danger","Bookmark with id " + id + " not exist");
		}
		mav.addObject(alertMessage);
		mav.setViewName("redirect:/bookmark");
		return mav;
	}
}
