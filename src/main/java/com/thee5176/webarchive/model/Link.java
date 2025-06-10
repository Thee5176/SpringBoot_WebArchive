package com.thee5176.webarchive.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "links")
public class Link {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Schema(description = "Name of the link", example = "Bealdung")
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Schema(description = "URL of the link", example = "https://www.baeldung.com/intro-to-project-lombok")
	@Column(name = "url", nullable = false, unique = true)
	private String url;

	@ManyToOne
	@JoinColumn(name = "tag_id", nullable = true, unique = false)
	private Tag tag;

	@Schema(description = "Description of the link", example = "Introduction to Project Lombok")
	@Column(name = "description", nullable = true, unique = false)
	private String description;

	@Column(name = "created_at", nullable = true, unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at", nullable = true, unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

}
