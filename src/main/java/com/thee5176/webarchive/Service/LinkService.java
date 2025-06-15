package com.thee5176.webarchive.Service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thee5176.webarchive.Repository.LinkRepository;
import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.dto.LinkDTO;
import com.thee5176.webarchive.mapper.LinkDTOMapper;
import com.thee5176.webarchive.model.Link;
import com.thee5176.webarchive.model.Tag;

import jakarta.transaction.Transactional;

@Service
public class LinkService {
	@Autowired
	LinkRepository linkRepository;

	@Autowired
	TagRepository tagRepository;

	@Transactional
	public Link createLinkWithTag(LinkDTO linkDto) throws RuntimeException {

		// Check if link with the same name already exists
		if (!linkRepository.existsByName(linkDto.name())) {
			// Create a new Link entity from the DTO
			Link link = LinkDTOMapper.map(linkDto);
			// Set the tag for the link entity
			Tag tag = tagRepository.findById(linkDto.tagId())
					.orElseThrow(() -> new RuntimeException("Tag with id " + linkDto.tagId() + " not created yet"));
			link.setTag(tag);
			link.setCreatedAt(Instant.now());
			link.setUpdatedAt(Instant.now());

			return linkRepository.saveAndFlush(link);

		} else {
			throw new RuntimeException("Bookmark already created");
		}
	}
	
	@Transactional
	public Link updateLinkWithTag(long id,LinkDTO linkDto) throws RuntimeException {

		// Check if tag exist in the database
		Link link = linkRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Bookmark with id " + id + " not created yet"));
		// Create a new Link entity from the DTO
		link = LinkDTOMapper.map(linkDto);
		link.setId(id);
		// Set the tag for the link entity
		Tag tag = tagRepository.findById(linkDto.tagId())
					.orElseThrow(() -> new RuntimeException("Tag with id " + linkDto.tagId() + " not created yet"));
		link.setTag(tag);
		link.setUpdatedAt(Instant.now());

		return linkRepository.saveAndFlush(link);
	}
}
