package com.thee5176.webarchive.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.Service.TagService;
import com.thee5176.webarchive.dto.AlertMessage;
import com.thee5176.webarchive.dto.TagDTO;
import com.thee5176.webarchive.model.Tag;

@Controller
public class TagController {
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	TagService tagService;
	
	@GetMapping("/tag")
	public ModelAndView getTagListView(ModelAndView mav) {
		mav.setViewName("base");
		mav.addObject("dynamicFragment", "/tag/listView");
		List<Tag> tagList = tagRepository.findAll();
		mav.addObject("object_list", tagList);
		mav.addObject("title", "タッグ一覧");
		return mav;
	}
	
	@GetMapping("/tag/form")
	// dynamic tag selector
	public ModelAndView getTagForm(ModelAndView mav) {
			mav.setViewName("base");
			TagDTO emptyDto = new TagDTO(null, null);
			mav.addObject("formData", emptyDto);
			mav.addObject("dynamicPath", "create");
			mav.addObject("dynamicFragment", "/tag/formView");

		return mav;
	}

	@PostMapping("/tag/create")
	public ModelAndView createTag(
		@ModelAttribute("formData") @Validated TagDTO tagDto,
		BindingResult result,
		AlertMessage alertMessage,
		ModelAndView mav) {
		if (!result.hasErrors()) {
			try {
				tagService.createTag(tagDto);
				
				//No Exception -> return redirect
				mav.setViewName("redirect:/tag");
			
				alertMessage = new AlertMessage("success","New Tag created successfully");
			} catch (RuntimeException e) {
				//Exception -> render form with previous data
				mav.addObject("formData", tagDto);
				
				//return form
				mav.setViewName("base");
				mav.addObject("dynamicPath", "create");
				mav.addObject("dynamicFragment","/tag/formView");

				alertMessage = new AlertMessage("danger",e.getMessage());
			}

		} else {
			// Validation Error -> Show Error Message(View Layer)
			mav.addObject("formData", tagDto);
			mav.setViewName("base");
			mav.addObject("dynamicPath", "create");
			mav.addObject("dynamicFragment","/tag/formView");
			
			//return form
			List<Tag> objectList = tagRepository.findAll();
			mav.addObject("object_list", objectList);
		}
		
		return mav;
	}
	
	@GetMapping("/tag/delete/{id}")
	public ModelAndView deleteTag(@PathVariable long id,
		AlertMessage alertMessage) {
		if ( tagRepository.existsById(id) ) {
			
			tagRepository.deleteById(id);
			tagRepository.flush();
			
			alertMessage = new AlertMessage("success", "Tag deleted successfully");
		} else {
			alertMessage = new AlertMessage("danger", "Tag with id " + id + " not exist");
		}
		return new ModelAndView("redirect:/tag");
	}
}
