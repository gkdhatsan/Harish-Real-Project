package io.automation.pojo;

import lombok.Data;

@Data
public class Preference {

	public PrimaryCurrency primaryCurrency;
    public boolean hasProductCatalog;
    public String quantityPrecision;
    public boolean allowWholeSale;
}
