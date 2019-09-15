package io.sumac.propertyinjector.sample;

import io.sumac.propertyinjector.annotations.Property;

public class Model5 {

	private String notFoundString;

	public String getNotFoundString() {
		return notFoundString;
	}

	@Property(name = "test.not_found.string")
	public void setNotFoundString(String notFoundString) {
		this.notFoundString = notFoundString;
	}

}
