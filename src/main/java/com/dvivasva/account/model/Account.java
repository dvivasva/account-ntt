package com.dvivasva.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Account {

	@Id
	private String id;
	private String type;
	private String number;
	private double availableBalance;
	private Date dateCreation;
	private String status;
	private String customerId;



}
