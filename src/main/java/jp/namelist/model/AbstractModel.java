package jp.namelist.model;

import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class AbstractModel implements Comparable<AbstractModel> {

	private String name;
	
	public AbstractModel(String name) {
		this.name = name;
	}
	
	public boolean matchName(String pattern) {
		return Pattern.matches(pattern, name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int compareTo(AbstractModel o) {
		return name.compareTo(o.getName());
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
