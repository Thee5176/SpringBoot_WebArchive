package com.thee5176.webarchive.model;

import java.time.Instant;
import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	@Schema(description = "Link ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
	private long id;

	@Column(name = "name", nullable = false, unique = true)
	@Schema(description = "Website name", example = "GitHub")
	private String name;

	@Column(name = "url", nullable = false, unique = true)
	@Schema(description = "Website URL", example = "https://github.com")
	private String url;
	
	@Column(name = "description", nullable = true, unique = false)
	@Schema(description = "Link description", example = "A platform for hosting and reviewing code.")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "tag_id", nullable = false, unique = false)
	@Schema(description = "Tag for the link")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Tag tag;

	@Column(name = "created_at", nullable = true, unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Created timestamp", example = "2024-06-10T12:00:00Z", accessMode = Schema.AccessMode.READ_ONLY)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = true, unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Updated timestamp", example = "2024-06-10T12:00:00Z", accessMode = Schema.AccessMode.READ_ONLY)
	private Instant updatedAt;

}
