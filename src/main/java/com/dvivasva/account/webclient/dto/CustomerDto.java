package com.dvivasva.account.webclient.dto;

import lombok.Data;

@Data
public class CustomerDto {
	private String id;
	private String name;
	private String lastname;
	private int dni;
	private String typeCustomer;
	private String profile;

}
