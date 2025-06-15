package com.thee5176.webarchive.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.dto.TagDTO;
import com.thee5176.webarchive.mapper.TagDTOMapper;
import com.thee5176.webarchive.model.Tag;

@Service
public class TagService {
	@Autowired
	TagRepository tagRepository;
	
	public Tag createTag(TagDTO tagDto) {
		if ( !tagRepository.existsByName(tagDto.name()) ) {
			Tag tag = TagDTOMapper.map(tagDto);
			return tagRepository.saveAndFlush(tag);
		} else {
			throw new RuntimeException("Tag already Exist");
		}
	}
}
