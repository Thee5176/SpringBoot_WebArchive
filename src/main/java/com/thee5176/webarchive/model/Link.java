package com.thee5176.webarchive.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "links")
public class Link {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Schema(description = "Name of the link", example = "Bealdung")
	@Column(nullable = false, unique = true)
	private String name;

	@Schema(description = "URL of the link", example = "https://www.baeldung.com/intro-to-project-lombok")
	@Column(nullable = false, unique = true)
	private String url;

	@ManyToOne
	@JoinColumn(name = "tag_id", nullable = true)
	private Tag tag;

	@Schema(description = "Description of the link", example = "Introduction to Project Lombok")
	@Column(nullable = true)
	private String description;
}
