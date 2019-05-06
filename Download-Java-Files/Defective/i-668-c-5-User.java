package com.gcp.registration.domain;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


/**
 * @author Anuj Kumar
 * 
 *         This class is domain to create User table
 */
@Data
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;

	@NotBlank(message = "LogonId is required")
	@Column(name = "logonId", unique = true)
	private String logonId;

	@Size(min = 8, max = 50, message = "Please use 8 to 50 characters")
	@NotBlank(message = "Password is required")
	private String password;

	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date created_At;

	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date updated_At;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(unique = true)
	@Valid
	private Address address;

	@PrePersist
	protected void onCreate() {
		this.created_At = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updated_At = new Date();
	}
}
