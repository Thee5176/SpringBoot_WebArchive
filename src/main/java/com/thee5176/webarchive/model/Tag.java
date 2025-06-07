package com.thee5176.webarchive.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tags")
public class Tag {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Schema(description = "Name of the Tag", example = "Spring")
	@Column(nullable = false, unique = true)
	private String name;

	@Schema(description = "Description of the Tag", example = "Source about Spring Framework")
	@Column(nullable = true)
	private String description;
}
