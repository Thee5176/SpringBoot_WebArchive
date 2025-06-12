package com.thee5176.webarchive.Service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thee5176.webarchive.Repository.LinkRepository;
import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.dto.LinkDTO;
import com.thee5176.webarchive.model.Link;
import com.thee5176.webarchive.model.Tag;

import jakarta.transaction.Transactional;
import mapper.LinkDTOMapper;

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
			// Check if tag exist in the database
			Tag tag = tagRepository.findById(linkDto.tagId())
					.orElseThrow(() -> new RuntimeException("Tag with id " + linkDto.tagId() + " not created yet"));
			// Create a new Link entity from the DTO
			Link link = LinkDTOMapper.map(linkDto);
			link.setCreatedAt(Instant.now());
			link.setUpdatedAt(Instant.now());
			// Set the tag for the link entity
			link.setTag(tag);

			return linkRepository.saveAndFlush(link);

		} else {
			throw new RuntimeException("Bookmark already created");
		}
	}
	
	@Transactional
	public Link updateLinkWithTag(LinkDTO linkDto) throws RuntimeException {

		// Check if link with the same name already exists
		if (!linkRepository.existsByName(linkDto.name())) {
			// Check if tag exist in the database
			Tag tag = tagRepository.findById(linkDto.tagId())
					.orElseThrow(() -> new RuntimeException("Tag with id " + linkDto.tagId() + " not created yet"));
			// Create a new Link entity from the DTO
			Link link = LinkDTOMapper.map(linkDto);
			link.setUpdatedAt(Instant.now());
			// Set the tag for the link entity
			link.setTag(tag);

			return linkRepository.saveAndFlush(link);

		} else {
			throw new RuntimeException("Bookmark already created");
		}
	}
}
