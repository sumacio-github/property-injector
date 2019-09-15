package io.sumac.propertyinjector.sample;

import java.util.Date;

import io.sumac.propertyinjector.annotations.Property;

public class Model11 {

	private Date date;

	public Date getDate() {
		return date;
	}

	@Property(name = "test.found.string")
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Model10 [date=" + date + "]";
	}

}
