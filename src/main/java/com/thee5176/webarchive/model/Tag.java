package com.thee5176.webarchive.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "tags")
public class Tag {

	@Id
	@NotNull
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Tag ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
	private long id;

	@NotEmpty(message = "タッグ名：入力してください！")
	@Size(min=2, max=15, message="タッグ名：2-15文字を抑えてください！")
	@Column(name = "name", nullable = false, unique = true)
	@Schema(description = "Tag name", example = "Programming")
	private String name;

	@Size(min=2, max=50, message="詳細：2-50文字を抑えてください！")
	@Column(name = "description", nullable = true, unique = false)
	@Schema(description = "Tag description", example = "All about programming languages")
	private String description;
}
