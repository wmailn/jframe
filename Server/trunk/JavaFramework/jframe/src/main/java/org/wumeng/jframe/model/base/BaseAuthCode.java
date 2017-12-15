package org.wumeng.jframe.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseAuthCode<M extends BaseAuthCode<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCode(java.lang.Integer code) {
		set("code", code);
		return (M)this;
	}
	
	public java.lang.Integer getCode() {
		return getInt("code");
	}

	public M setValidTime(java.lang.Long validTime) {
		set("valid_time", validTime);
		return (M)this;
	}
	
	public java.lang.Long getValidTime() {
		return getLong("valid_time");
	}

	public M setPhone(java.lang.String phone) {
		set("phone", phone);
		return (M)this;
	}
	
	public java.lang.String getPhone() {
		return getStr("phone");
	}

}
