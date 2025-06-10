package com.thee5176.webarchive.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thee5176.webarchive.model.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
	public List<Link> findAll();
//	public List<Link> findByTagId(long tag_id);
	
	public Optional<Link> findById(long id);
	public Optional<Link> findByName(String name);
	
	public void deleteByName(String name);
	public void deleteById(long id);
	
	public boolean existsByName(String name);
	public boolean existsByIdOrName(long id, String name);
}
