package com.thee5176.webarchive.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		mav.setViewName("base");
		List<Tag> tagList = tagRepository.findAll();
		mav.addObject("title", "タッグ一覧");
		mav.addObject("object_list", tagList);
		mav.addObject("dynamicFragment","/tag/listView");
		return mav;
	}
	
	@GetMapping(value={"/tag/form","/tag/form/{modalFlag}"})
	// dynamic tag selector
	public ModelAndView getTagForm(@PathVariable Optional<String> modalFlag,
			ModelAndView mav) {
				if( modalFlag.isPresent() && modalFlag.equals("modal")) {
					mav.setViewName("/tag/formView");
					mav.addObject("modalFlag", true);
				} else {
					mav.setViewName("base");
					mav.addObject("dynamicFragment", "/tag/formView");
				}
				return mav;
	}

	@PostMapping("/tag/create")
	public ModelAndView createTag(@ModelAttribute TagDTO tag,
			RedirectAttributes redirectAttributes) {
		Map<String,String> alert = new HashMap<>();
		try {
			
			tagService.createTag(tag);
			
			alert.put("bscolor", "success");
			alert.put("message", "New Bookmark created successfully");
		} catch (Exception e) {
			alert.put("bscolor", "danger");
			alert.put("message", e.getMessage());
		}
		redirectAttributes.addFlashAttribute(alert);
		return new ModelAndView("redirect:/tag");
	}
	
	@DeleteMapping("/tag/delete/{id}")
	public ModelAndView deleteTag(@PathVariable long id,
			RedirectAttributes redirectAttribute) {
		if ( tagRepository.existsById(id) ) {
			
			tagRepository.deleteById(id);
			tagRepository.flush();
			
		} else {
			throw new RuntimeException("Bookmark with id " + id + " not exist");
		}
		return new ModelAndView("redirect:/tag");
	}
}
