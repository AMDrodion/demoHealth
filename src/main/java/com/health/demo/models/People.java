package com.health.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "people")
@Data
public class People {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
	private String fatherName;

	private Integer diagnosisId;
	private Integer wardId;
}
