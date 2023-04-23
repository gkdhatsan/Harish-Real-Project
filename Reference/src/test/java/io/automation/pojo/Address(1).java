package io.automation.pojo;

import lombok.Data;

@Data
public class Address {

	public String addressLine1;
	public String addressLine2;
	public String city;
	public String district;
	public String state;
	public Country country;
	public String zipcode;
	public int createdBy;
	public int lastModifiedBy;

}
