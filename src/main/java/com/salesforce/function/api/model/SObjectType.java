package com.salesforce.function.api.model;

/**
 * 
 * Standard Salesforce ObjectTypes. 
 * 
 * TODO: Dynamically generate this, or use the Wsdl2Java results 
 *
 * @since 218
 */
public enum SObjectType {
	Account("Account", "001"),
	Attachment("Attachment", "00P"),
	Contact("Contact", "003");
    
	
	private final String value;
	private final String keyPrefix;
	
	private SObjectType(String value, String keyPrefix) {
		this.value = value;
		this.keyPrefix = keyPrefix;
	}
	
	/**
	 * 
	 * @return
	 */
	public String value() {
		return this.value;
	}
	
	/**
	 * See <a href="https://help.salesforce.com/articleView?id=000005995&language=en_US&type=1">Standard Field Record ID Prefix Decoder
</a>
	 * 
	 * @return the 3 letter case senstiive key prefix  
	 */
	public String keyPrefix() {
	    return this.keyPrefix;
	}
}
