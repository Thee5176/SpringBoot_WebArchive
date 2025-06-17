package com.thee5176.webarchive.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import com.thee5176.webarchive.dto.NullFormData;
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
		
		// display alert message
		if (alertMessage != null) {
			mav.addObject("alert", alertMessage);
		}
		return mav;
	}

	@GetMapping("/bookmark/id/{id}")
	public ModelAndView getDetailView(
			@PathVariable long id,
			RedirectAttributes redirectAttributes,
			ModelAndView mav
		) {
		try {
			Link link = linkRepository.findById(id)
				.orElseThrow(()  -> new RuntimeException("Bookmark id" + id +"not found"));
			mav.addObject("object", link);
			mav.setViewName("bookmark/detail_modal");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger",e.getMessage()));
			mav.setViewName("redirect:/bookmark");
		}
		return mav;
	}


	@GetMapping("/bookmark/form")
	// dynamic tag selector
	public ModelAndView getBookmarkForm(
		@ModelAttribute("inputData") LinkDTO linkDto,
		@ModelAttribute("alertMessage") AlertMessage alertMessage,
		ModelAndView mav
	) {
		// display user input data
		if (linkDto != null) {
			mav.addObject("formData", linkDto);
		} else {
			mav.addObject("formData", new NullFormData());
		}

		// display alert message
		if (alertMessage != null) {
			mav.addObject("alert", alertMessage);
		} 

		List<Tag> objectList = tagRepository.findAll();
		mav.addObject("object_list", objectList);
		mav.setViewName("base");
		mav.addObject("dynamicPath", "create");
		mav.addObject("dynamicFragment", "/bookmark/formView");
		mav.addObject("formTitle", "ブックマーク作成");
		return mav;
	}

	@PostMapping("/bookmark/create")
	public ModelAndView createBookmark(
			@ModelAttribute("formData") @Validated LinkDTO linkDto, 
			BindingResult result,
			RedirectAttributes redirectAttributes,
			ModelAndView mav
		) {
		if (!result.hasErrors()) {
			try {
				linkService.createLinkWithTag(linkDto);
				//No Exception -> return redirect
				
				redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("success","New Bookmark created successfully"));
				mav.setViewName("redirect:/bookmark");
			} catch (RuntimeException e) {
				//Exception -> render form with previous data
				
				redirectAttributes.addFlashAttribute("inputData", linkDto);
				redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", e.getMessage()));
				redirectAttributes.addFlashAttribute("result", result);
				mav.setViewName("redirect:/bookmark/form");
			}

		} else {
			//validation error -> return form-create with input data
			mav.addObject("formData", linkDto);

			mav.setViewName("base");
			mav.addObject("dynamicFragment", "/bookmark/formView");
			mav.addObject("dynamicPath", "create");
			mav.addObject("formTitle", "ブックマーク作成");
		}
		
		return mav;
	}

	@GetMapping("bookmark/form/{id}")
	public ModelAndView getBookmarkUpdateForm(
		@PathVariable long id,
		@ModelAttribute("inputData") LinkDTO linkDto,
		@ModelAttribute("alertMessage") AlertMessage alertMessage,
		RedirectAttributes redirectAttributes,
		ModelAndView mav
	) {
		try {
			// retreive data from Repository
			if (linkDto == null || linkDto.name() == null) {
				LinkDTO retrievedLinkDto = LinkDTOMapper.map(
					linkRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Bookmark with id" + id + "not exist"))
					);
				mav.addObject("formData", retrievedLinkDto);
			} else {
				mav.addObject("formData", linkDto);
			}
			
			// display alert message
			if (alertMessage != null) {
				mav.addObject("alert", alertMessage);
			}
			
			
			// Tag selection option fields
			List<Tag> objectList = tagRepository.findAll();
			mav.addObject("object_list", objectList);
			// render form
			mav.setViewName("base");
			mav.addObject("dynamicFragment", "/bookmark/formView");
			mav.addObject("dynamicPath", "update/" + id);
			mav.addObject("formTitle", "ブックマーク更新");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", e.getMessage()));
			mav.setViewName("redirect:/bookmark");
		}

		return mav;
	}

	@PostMapping("bookmark/update/{id}")
	public ModelAndView updateBookmark(
		@PathVariable long id,
		@ModelAttribute("formData") @Validated LinkDTO linkDto,
		BindingResult result,
		RedirectAttributes redirectAttributes,
		ModelAndView mav) {
		
		if (!result.hasErrors()) {
			try {
				linkService.updateLinkWithTag(id, linkDto);
				// No Exception -> redirect to listView
				
				redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("success","Bookmark id" + id +" updated successfully"));
				mav.setViewName("redirect:/bookmark");
			} catch (Exception e) {
				// Exception -> rerender update form

				redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", e.getMessage()));
				redirectAttributes.addFlashAttribute("inputData", linkDto);
				mav.setViewName("redirect:/bookmark/form/" + id);
			}
		} else {
			// Validation Error -> rerender update form

			mav.addObject("formData", linkDto);
			mav.setViewName("base");
			mav.addObject("dynamicFragment", "/bookmark/formView");
			mav.addObject("dynamicPath", "update/" + id);
			mav.addObject("formTitle", "ブックマーク更新");
		}
		
		return mav;
	}

	@GetMapping("bookmark/delete/{id}")
	public String deleteLink(
		@PathVariable long id,
		RedirectAttributes redirectAttributes,
		Model model) {
		if (linkRepository.existsById(id)) {
			
			linkRepository.deleteById(id);
			linkRepository.flush();
			
			redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("success","Bookmark deleted successfully"));
		} else {
			redirectAttributes.addFlashAttribute("alertMessage",new AlertMessage("danger","Bookmark with id " + id + " not exist"));
		}
		return "redirect:/bookmark";
	}
}
