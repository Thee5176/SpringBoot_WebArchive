package com.thee5176.webarchive.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thee5176.webarchive.Repository.LinkRepository;
import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.dto.LinkDTO;
import com.thee5176.webarchive.model.Link;
import com.thee5176.webarchive.model.Tag;

import mapper.LinkDTOMapper;

@Service
public class LinkService {
	@Autowired
	LinkRepository linkRepository;

	@Autowired
	TagRepository tagRepository;

	public Link saveLinkWithTag(LinkDTO dto) throws RuntimeException {
		
		// Check if link with the same name already exists
		if ( !linkRepository.existsByName(dto.name()) ) {
			// Check if tag exist in the database
			Tag tag = tagRepository.findById(dto.tagId())
					.orElseThrow(() -> new RuntimeException("Tag with id " + dto.tagId() + " not created yet"));
			// Create a new Link entity from the DTO
			Link link = LinkDTOMapper.map(dto);
			// Set the tag for the link entity
			link.setTag(tag);
			
			return linkRepository.save(link);
			
		} else {
			throw new RuntimeException("Bookmark already created");
		}
	}
}
