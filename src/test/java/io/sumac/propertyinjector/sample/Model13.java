package io.sumac.propertyinjector.sample;

import io.sumac.propertyinjector.annotations.Property;

public class Model13 {

	private String str;

	public Model13(@Property(name = "test.found.string") String str) {
		this.str = str;
	}

	public Model13() {
	}

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
