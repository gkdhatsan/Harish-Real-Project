package io.automation.pojo;

import java.util.*;

import io.testbase.TestBase;
import lombok.Data;

@Data
public class CompanyAPI {

	public long id;
	public String name;
	public String displayAs;
	public CountryOfBusiness countryOfBusiness;
	public String parentCompany;
	public CompanyType companyType;
	public AccountingMethod accountingMethod;
	public Preference preference;
	public Address address;
	public ContactInfo contactInfo;
	public String taxInfo;
	public int firstMonthOfFiscalYear;
	public int firstMonthOfIncomeTaxYear;
	public String logo;
	public boolean hasProductCatalog;
	public int quantityPrecision;
	public int businessType;
	public boolean allowWholeSale;
	public boolean deleted;
	public boolean branch;

	public CompanyAPI generateCompanyApi(Map<String, Object> data) {

		CompanyAPI company = new CompanyAPI();
		CountryOfBusiness countryOfBusiness = new CountryOfBusiness();
		CompanyType companyType = new CompanyType();
		AccountingMethod accountingMethod = new AccountingMethod();
		Preference preference = new Preference();
		PrimaryCurrency currency = new PrimaryCurrency();
		Address address = new Address();
		Country country = new Country(); 
		ContactInfo contactInfo = new ContactInfo();
		Phone phone = new Phone();
		Mobile mobile = new Mobile();

		String testName = data.get("TestName").toString();
		String id = data.get("Id").toString();
		String name = data.get("Name").toString();
		String displayas = data.get("displayas").toString();

		String countryofbusinessid = data.get("countryofbusinessid").toString();

		String parentcompany = data.get("parentcompany").toString();

		String companytypeid = data.get("companytypeid").toString();

		String accountingmethodid = data.get("accountingmethodid").toString();

		String primarycurrencyid = data.get("primarycurrencyid").toString();
		String hasproductcatalog = data.get("hasproductcatalog").toString();
		String quantityprecision = data.get("quantityprecision").toString();
		String allowwholesale = data.get("allowwholesale").toString();

		String addressLine1 = data.get("addressLine1").toString();
		String addressLine2 = data.get("addressLine2").toString();
		String city = data.get("city").toString();
		String district = data.get("district").toString();
		String state = data.get("state").toString();
		String country1 = data.get("country").toString();
		String zipcode = data.get("zipcode").toString();
//		String createdby = data.get("createdby").toString();
//		String lastmodifiedby = data.get("lastmodifiedby").toString();

		
		String phonenumber = data.get("phonenumber").toString();
		String isocode = data.get("isocode").toString();

		String mobilenumber = data.get("mobilenumber").toString();
		String isocode1 = data.get("isocode1").toString();

		String fax = data.get("fax").toString();
		String email = data.get("email").toString();
		String website = data.get("website").toString();

		String taxinfo = data.get("taxinfo").toString();
		String firstmonthofficalyear = data.get("firstmonthoffiscalyear").toString();
		String firstmonthofincometaxyear = data.get("firstmonthofincometaxyear").toString();
		String logo = data.get("").toString();
		String hasproductcatalog1 = data.get("hasproductcatalog1").toString();
		String quantityprecision1 = data.get("quantityprecision1").toString();
		String businesstype = data.get("businesstype").toString();
		String allowwholesale1 = data.get("allowwholesale1").toString();
		String isdeleted = data.get("isdeleted").toString();
		String isbranch = data.get("isbranch").toString();

		if (testName.equalsIgnoreCase("addCompany") || testName.equalsIgnoreCase("updateCompany")) {
			email = TestBase.randomEmailId().toString();
			name = name + " " + TestBase.randomWord();
		}

		company.setId(Long.parseLong(id));
		company.setName(name);
		company.setDisplayAs(displayas);

		countryOfBusiness.setId(countryofbusinessid);
		company.setCountryOfBusiness(countryOfBusiness);

		company.setParentCompany(parentcompany);

		companyType.setId(companytypeid);
		company.setCompanyType(companyType);

		accountingMethod.setId(accountingmethodid);
		company.setAccountingMethod(accountingMethod);

		currency.setId(primarycurrencyid);
		preference.setPrimaryCurrency(currency);
		preference.setHasProductCatalog(hasproductcatalog.equalsIgnoreCase("false") ? true : false);
		preference.setQuantityPrecision(quantityprecision);
		preference.setAllowWholeSale(allowwholesale.equalsIgnoreCase("false") ? true : false);
		company.setPreference(preference);

		address.setAddressLine1(addressLine1);
		address.setAddressLine2(addressLine2);
		address.setCity(city);
		address.setDistrict(district);
		address.setState(state);
		country.setId(country1);
		address.setCountry(country);
		address.setZipcode(zipcode);
//		address.setCreatedBy(Integer.parseInt(createdby));
//		address.setLastModifiedBy(Integer.parseInt(lastmodifiedby));
		company.setAddress(address);

		phone.setNumber(phonenumber);
		phone.setIsoCode(isocode);
		mobile.setNumber(mobilenumber);
		mobile.setIsoCode1(isocode1);
		contactInfo.setPhone(phone);
		contactInfo.setMobile(mobile);
		contactInfo.setEmail(email);
		contactInfo.setFax(fax);
		contactInfo.setWebsite(website);
		company.setContactInfo(contactInfo);

		company.setTaxInfo(taxinfo);
		company.setFirstMonthOfFiscalYear(Integer.parseInt(firstmonthofficalyear));
		company.setFirstMonthOfIncomeTaxYear(Integer.parseInt(firstmonthofincometaxyear));
		company.setLogo(logo);
		company.setHasProductCatalog(hasproductcatalog1.equalsIgnoreCase("false") ? true : false);
		company.setQuantityPrecision(Integer.parseInt(quantityprecision1));
		company.setBusinessType(Integer.parseInt(businesstype));
		company.setAllowWholeSale(allowwholesale1.equalsIgnoreCase("false") ? true : false);
		company.setDeleted(isdeleted.equals("false"));
		company.setBranch(isbranch.equalsIgnoreCase("false"));
		return company;

	}

}
