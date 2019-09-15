package io.sumac.propertyinjector.sample;

import java.util.Date;

import io.sumac.propertyinjector.annotations.Property;

public class Model10 {

	@Property(name = "test.found.string")
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Model10 [date=" + date + "]";
	}

}
