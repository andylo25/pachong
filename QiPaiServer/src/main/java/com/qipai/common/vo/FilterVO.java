package com.qipai.common.vo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class FilterVO implements Serializable{

	private String type;
	private String comparison;
	private String value;
	private String field;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComparison() {
		if(StringUtils.isBlank(comparison)){
			if(type.startsWith("like")){
				return " like ? ";
			}else{
				return "= ? ";
			}
		}else if("date".equals(type)){
			return comparison+" str_to_date(?,'%Y-%m-%d %H:%i:%s') ";
		}
		return comparison+" ? ";
	}
	public void setComparison(String comparison) {
		if(StringUtils.isBlank(comparison)){
			this.comparison = "=";
		}else if("gt".equals(comparison)){
			this.comparison = ">";
		}else if("ge".equals(comparison)){
			this.comparison = ">=";
		}else if("le".equals(comparison)){
			this.comparison = "<=";
		}else if("ne".equals(comparison)){
			this.comparison = "!=";
		}else if("eq".equals(comparison)){
			this.comparison = "=";
		}else if("lt".equals(comparison)){
			this.comparison = "<";
		}else{
			this.comparison = comparison;
		}
	}
	public String getValue() {
		if(StringUtils.isBlank(comparison)){
			if("like".equals(type)){
				return "%"+value+"%";
			}else if("likel".equals(type)){
				return "%"+value;
			}else if("liker".equals(type)){
				return "value"+"%";
			}
		}
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
}
