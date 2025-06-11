package mapper;

import org.springframework.stereotype.Service;

import com.thee5176.webarchive.dto.LinkDTO;
import com.thee5176.webarchive.model.Link;

@Service
public class LinkDTOMapper {
	// Converts a Link entity to a LinkDTO
	public static LinkDTO map(Link entity) {
		return new LinkDTO(
				entity.getName(), 
				entity.getUrl(), 
				entity.getDescription(), 
				entity.getTag().getId()
				);
	}
	
	// Converts a LinkDTO to a Link entity
	public static Link map(LinkDTO dto) {
		Link link = new Link();
		link.setName(dto.name());
		link.setUrl(dto.url());
		link.setDescription(dto.description());
		// Tag is set to null here, as it will be set later in the service layer
		link.setTag(null);
		
		return link;
	}
}


