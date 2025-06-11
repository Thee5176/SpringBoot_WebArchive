package com.thee5176.webarchive.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.dto.TagDTO;
import com.thee5176.webarchive.model.Tag;

import mapper.TagDTOMapper;

@Service
public class TagService {
	@Autowired
	TagRepository tagRepository;
	
	public Tag createTag(TagDTO dto) {
		if ( !tagRepository.existsByName(dto.name()) ) {
			Tag tag = TagDTOMapper.map(dto);
			return tagRepository.saveAndFlush(tag);
		} else {
			throw new RuntimeException("Tag already Exist");
		}
	}
}
