package com.thee5176.webarchive.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thee5176.webarchive.Repository.LinkRepository;
import com.thee5176.webarchive.Repository.TagRepository;
import com.thee5176.webarchive.model.Link;
import com.thee5176.webarchive.model.Tag;

@Service
public class LinkService {
	@Autowired
	LinkRepository linkRepository;

	@Autowired
	TagRepository tagRepository;

	public Link saveLinkWithTag(Link link) throws RuntimeException {
//      if (linkRepository.existsByIdOrName(link.getId(), link.getName())) {  
//		    Tag tag = link.getTag();   
//        	if (tagRepository.existsByIdOrName(tag.getId(), tag.getName() ) ) {
//        		// Tagが存在している　かつ　まだ作成していなかったLink
//                tag = tagRepository.findById(tag.getId());
//                link.setTag(tag);
//                return linkRepository.save(link);
//            
//        	} else {
//        		// 入力Linkはもう作成していた
//        		throw new RuntimeException("");
//        	}
//			
//		} else {
//			// Tagが存在していない
//			throw new RuntimeException("Tag not not");
//		}
		
		if ( !linkRepository.existsByIdOrName(link.getId(), link.getName() ) ) {
			Tag tag = link.getTag();
			tag = tagRepository.findById(tag.getId()).orElseThrow(() -> new RuntimeException("Tag not found"));
			link.setTag(tag);
			return linkRepository.save(link);
			
		} else {
			throw new RuntimeException("Bookmark already created");
		}
	}
}
