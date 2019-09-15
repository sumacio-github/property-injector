package io.sumac.propertyinjector.sample;

import io.sumac.propertyinjector.annotations.Property;

public class Model17 {

	@Property(name = "test.found.string")
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return "Model13 [str=" + str + "]";
	}

}
