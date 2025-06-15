package com.thee5176.webarchive.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thee5176.webarchive.Repository.LinkRepository;
import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.Service.LinkService;
import com.thee5176.webarchive.dto.AlertMessage;
import com.thee5176.webarchive.dto.LinkDTO;
import com.thee5176.webarchive.mapper.LinkDTOMapper;
import com.thee5176.webarchive.model.Link;
import com.thee5176.webarchive.model.Tag;

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
	public ModelAndView getBookmarksListView(
		@ModelAttribute("alertMessage") AlertMessage alertMessage,
		ModelAndView mav) {
		mav.setViewName("base");
		List<Link> bookmarkList = linkRepository.findAll();
		mav.addObject("title", "Webブックマーク一覧");
		mav.addObject("object_list", bookmarkList);
		mav.addObject("dynamicFragment", "/bookmark/listView");
		
		mav.addObject("alert", alertMessage);
		return mav;
	}

	@GetMapping("/bookmark/id/{id}")
	public ModelAndView getDetailView(
			@PathVariable long id,
			ModelAndView mav
		) {
		mav.setViewName("bookmark/detail_modal");
		Link link = linkRepository.findById(id)
				.orElseThrow();
		mav.addObject("object", link);
		return mav;
	}


	@GetMapping("/bookmark/form")
	// dynamic tag selector
	public ModelAndView getBookmarkForm(
		ModelAndView mav
		) {
		List<Tag> objectList = tagRepository.findAll();
		mav.addObject("object_list", objectList);

		LinkDTO emptyDto = new LinkDTO("", "", "", 0L);
		mav.addObject("formData", emptyDto);
		mav.setViewName("base");
		mav.addObject("dynamicPath", "create");
		mav.addObject("dynamicFragment", "/bookmark/formView");
		return mav;
	}

	@PostMapping("/bookmark/create")
	public ModelAndView createBookmark(
			@ModelAttribute("formData") @Validated LinkDTO linkDto, 
			BindingResult result,
			RedirectAttributes redirectAttributes,
			AlertMessage alertMessage,
			ModelAndView mav
		) {
		if (!result.hasErrors()) {
			try {
				linkService.createLinkWithTag(linkDto);
				
				alertMessage = new AlertMessage("success","New Bookmark created successfully");
				redirectAttributes.addAttribute("alert", alertMessage);
				//No Exception -> return redirect
				mav.setViewName("redirect:/bookmark");
			} catch (RuntimeException e) {
				//Exception -> render form with previous data
				mav.addObject("formData", linkDto);
				
				//return form
				List<Tag> objectList = tagRepository.findAll();
				mav.setViewName("base");
				mav.addObject("object_list", objectList);
				mav.addObject("dynamicPath", "create");
				mav.addObject("dynamicFragment","/bookmark/formView");

				alertMessage = new AlertMessage("danger",e.getMessage());
			} finally {
				mav.addObject("alert",alertMessage);
			}

		} else {
			// Validation Error -> Show Error Message(View Layer)
			mav.addObject("formData", linkDto);
			mav.setViewName("base");
			mav.addObject("dynamicPath", "create");
			mav.addObject("dynamicFragment","/bookmark/formView");
			
			//return form
			List<Tag> objectList = tagRepository.findAll();
			mav.addObject("object_list", objectList);
		}
		
		return mav;
	}

	@GetMapping("bookmark/form/{id}")
	public ModelAndView getBookmarkUpdateForm(
		@PathVariable long id,
		AlertMessage alertMessage,
		ModelAndView mav
	) {
		mav.setViewName("base");
		mav.addObject("dynamicFragment", "/bookmark/formView");
		mav.addObject("dynamicPath", "update/" + id);
		mav.addObject("title","ブックマーク更新");
		List<Tag> objectList = tagRepository.findAll();
		mav.addObject("object_list", objectList);

		try {
			LinkDTO retrievedLinkDto = LinkDTOMapper.map(
				linkRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Bookmark with id" + id + "not exist"))
			);
			mav.addObject("formData", retrievedLinkDto);
		} catch (Exception e) {
			alertMessage = new AlertMessage("danger", e.getMessage());
			mav.setViewName("redirect:/bookmark");
		}

		return mav;
	}

	@PostMapping("bookmark/update/{id}")
	public ModelAndView updateBookmark(
		@ModelAttribute("formData") @Validated LinkDTO linkDto,
		@PathVariable long id,
		BindingResult result,
		AlertMessage alertMessage,
		ModelAndView mav) {
		
		if (!result.hasErrors()) {
			try {
				linkService.updateLinkWithTag(id, linkDto);
				
				// No Exception -> redirect to listView
				mav.setViewName("redirect:/bookmark");
				alertMessage = new AlertMessage("success","Bookmark id" + id +"updated successfully");
			} catch (Exception e) {
				// Exception -> rerender update form
				mav.setViewName("base");
				List<Tag> objectList = tagRepository.findAll();
				mav.addObject("object_list", objectList);
				mav.addObject("formData", linkDto);
				mav.addObject("dynamicFragment", "/bookmark/formView");
				mav.addObject("dynamicPath", "update/" + id);
				alertMessage = new AlertMessage("danger", e.getMessage());
			} finally {
				mav.addObject("alert", alertMessage);
			}
		} else {
			// Validation Error -> rerender update form
			mav.setViewName("base");
			List<Tag> objectList = tagRepository.findAll();
			mav.addObject("object_list", objectList);
			mav.addObject("formData", linkDto);
			mav.addObject("dynamicFragment", "/bookmark/formView");
			mav.addObject("dynamicPath", "update/" + id);
		}
		
		return mav;
	}

	@GetMapping("bookmark/delete/{id}")
	public String deleteLink(
		@PathVariable long id,
		AlertMessage alertMessage,
		Model model) {
		if (linkRepository.existsById(id)) {
			
			linkRepository.deleteById(id);
			linkRepository.flush();
			
			alertMessage = new AlertMessage("success","Bookmark deleted successfully");
		} else {
			alertMessage = new AlertMessage("danger","Bookmark with id " + id + " not exist");
		}
		model.addAttribute(alertMessage);
		return "redirect:/bookmark";
	}
}
