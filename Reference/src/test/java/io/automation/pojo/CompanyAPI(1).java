package io.automation.pojo;

import java.util.*;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.testbase.TestBase;
import lombok.Data;

@Data
public class CompanyAPI {

	public String name;
	public String displayAs;
	public CountryOfBusiness countryOfBusiness;
	public ParentCompany parentCompany;
	public CompanyType companyType;
	public AccountingMethod accountingMethod;
	public Preference preference;
	public Address address;
	public ContactInfo contactInfo;
	public TaxInfo taxInfo;
	public int firstMonthOfFiscalYear;
	public int firstMonthOfIncomeTaxYear;
	public String logo;
	public boolean hasProductCatalog;
	public int quantityPrecision;
	public int businessType;
	public boolean allowWholeSale;
	public User user;
	public boolean deleted;
	public boolean branch;

	public String generateCompanyApi(Map<String, Object> data) {

		CompanyAPI company = new CompanyAPI();
		CountryOfBusiness countryOfBusiness = new CountryOfBusiness();
		ParentCompany parentCompany = new ParentCompany();
		CompanyType companyType = new CompanyType();
		AccountingMethod accountingMethod = new AccountingMethod();
		Preference preference = new Preference();
		PrimaryCurrency currency = new PrimaryCurrency();
		Address address = new Address();
		Country country = new Country(); 
		ContactInfo contactInfo = new ContactInfo();
		TaxInfo taxInfo = new TaxInfo();
		Phone phone = new Phone();
		Mobile mobile = new Mobile();
		User user = new User();

		String testName = data.get("TestName").toString();
		String id = data.get("Id").toString();
		String name = data.get("Name").toString();
		String displayas = data.get("displayAs").toString();

		String countryofbusinessid = data.get("countryofbusinessid").toString();

		String parentcompanyid = data.get("parentcompanyid").toString();

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
		
		String phonenumber = data.get("phonenumber").toString();
		String isocode = data.get("isocode").toString();

		String mobilenumber = data.get("mobilenumber").toString();
		String isocode1 = data.get("isocode1").toString();

		String fax = data.get("fax").toString();
		String email = data.get("email").toString();
		String website = data.get("website").toString();
		
		String pan = data.get("pan").toString();
		String cst = data.get("cst").toString();
		String gst = data.get("gst").toString();
		String tin = data.get("tin").toString();
		String tin1 = data.get("tin1").toString();
		
		String firstmonthoffiscalyear = data.get("firstmonthoffiscalyear").toString();
		String firstmonthofincometaxyear = data.get("firstmonthofincometaxyear").toString();
		String logo = data.get("logo").toString();
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

		user.setId(Integer.parseInt(id));
		company.setUser(user);
		company.setName(name);
		company.setDisplayAs(displayas);

		countryOfBusiness.setId(Integer.parseInt(countryofbusinessid));
		company.setCountryOfBusiness(countryOfBusiness);

		if (ObjectUtils.isEmpty(parentcompanyid)) {
			company.setParentCompany(null);
		}else {
			parentCompany.setId(Integer.parseInt(parentcompanyid));
			company.setParentCompany(parentCompany);
		}

		companyType.setId(Integer.parseInt(companytypeid));
		company.setCompanyType(companyType);

		accountingMethod.setId(Integer.parseInt(accountingmethodid));
		company.setAccountingMethod(accountingMethod);

		currency.setId(Integer.parseInt(primarycurrencyid));
		preference.setPrimaryCurrency(currency);
		preference.setHasProductCatalog(hasproductcatalog.equalsIgnoreCase("false") ? true : false);
		preference.setQuantityPrecision(Integer.parseInt(quantityprecision));
		preference.setAllowWholeSale(allowwholesale.equalsIgnoreCase("false") ? true : false);
		company.setPreference(preference);

		address.setAddressLine1(addressLine1);
		address.setAddressLine2(addressLine2);
		address.setCity(city);
		address.setDistrict(district);
		address.setState(state);
		country.setId(Integer.parseInt(country1));
		address.setCountry(country);
		address.setZipcode(zipcode);
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
		
		taxInfo.setPan(Integer.parseInt(pan));
		taxInfo.setCst(Integer.parseInt(cst));
		taxInfo.setGst(Integer.parseInt(gst));
		taxInfo.setTin(Integer.parseInt(tin));
		taxInfo.setTin1(Integer.parseInt(tin1));
		company.setTaxInfo(taxInfo);
		
		company.setFirstMonthOfFiscalYear(!ObjectUtils.isEmpty(Integer.parseInt(firstmonthoffiscalyear))?Integer.parseInt(firstmonthoffiscalyear):1);
		company.setFirstMonthOfIncomeTaxYear(!ObjectUtils.isEmpty(Integer.parseInt(firstmonthofincometaxyear))?Integer.parseInt(firstmonthofincometaxyear):1);
		company.setLogo(null);
		company.setHasProductCatalog(hasproductcatalog1.equalsIgnoreCase("false") ? true : false);
		company.setQuantityPrecision(Integer.parseInt(quantityprecision1));
		company.setBusinessType(Integer.parseInt(businesstype));
		company.setAllowWholeSale(allowwholesale1.equalsIgnoreCase("false") ? true : false);
		company.setDeleted(isdeleted.equalsIgnoreCase("false"));
		company.setBranch(isbranch.equalsIgnoreCase("true")? true : false);
//		return company;
		
		ObjectMapper obj = new ObjectMapper();
		try {
			return obj.writeValueAsString(company);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return "";
		}

	}

}
