package com.thee5176.webarchive.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thee5176.webarchive.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	public List<Tag> findAll();
	
	public Optional<Tag> findById(long id);
	public Optional<Tag> findByName(String name);
	
	public void deleteByName(String name);
	public void deleteById(long id);
	
	public boolean existsByName(String name);
	public boolean existsByIdOrName(long id, String name);
}
