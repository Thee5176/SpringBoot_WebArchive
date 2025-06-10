package mapper;

import org.springframework.stereotype.Service;

import com.thee5176.webarchive.dto.TagDTO;
import com.thee5176.webarchive.model.Tag;

@Service
public class TagDTOMapper {
	public static TagDTO map(Tag entity) {
		return new TagDTO(
				entity.getName(),
				entity.getDescription()
		);
	}
	
	public static Tag map(TagDTO dto) {
		Tag tag = new Tag();
		tag.setName(dto.name());
		tag.setDescription(dto.description());
		return tag;
	}
}
