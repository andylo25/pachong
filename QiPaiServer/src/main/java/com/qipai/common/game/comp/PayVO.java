package com.qipai.common.game.comp;

import java.io.Serializable;

public class PayVO implements Serializable {

	private String merchant_code = "";
	private String notify_type = "";
	private String notify_id = "";
	private String interface_version = "";
	private String sign_type = "";
	private String dinpaySign = "";
	private String order_no = "";
	private String order_time = "";
	private String order_amount = "";
	private String trade_no = "";
	private String trade_time = "";
	private String trade_status = "";
	private String bank_seq_no = "";
	private String extra_return_param = "";
	
	private String service_type;
	private String pay_type;
	private String input_charset;
	private String notify_url;
	private String product_name;
	private String product_code;
	private String product_desc;
	private String product_num;
	private String show_url;
	private String client_ip;
	private String bank_code;
	private String redo_flag;
	private String extend_param;
	private String return_url;
	
	
	public String getMerchant_code() {
		return merchant_code;
	}
	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getInterface_version() {
		return interface_version;
	}
	public void setInterface_version(String interface_version) {
		this.interface_version = interface_version;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getDinpaySign() {
		return dinpaySign;
	}
	public void setDinpaySign(String dinpaySign) {
		this.dinpaySign = dinpaySign;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getTrade_time() {
		return trade_time;
	}
	public void setTrade_time(String trade_time) {
		this.trade_time = trade_time;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getBank_seq_no() {
		return bank_seq_no;
	}
	public void setBank_seq_no(String bank_seq_no) {
		this.bank_seq_no = bank_seq_no;
	}
	public String getExtra_return_param() {
		return extra_return_param;
	}
	public void setExtra_return_param(String extra_return_param) {
		this.extra_return_param = extra_return_param;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getInput_charset() {
		return input_charset;
	}
	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_desc() {
		return product_desc;
	}
	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}
	public String getProduct_num() {
		return product_num;
	}
	public void setProduct_num(String product_num) {
		this.product_num = product_num;
	}
	public String getShow_url() {
		return show_url;
	}
	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}
	public String getClient_ip() {
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getRedo_flag() {
		return redo_flag;
	}
	public void setRedo_flag(String redo_flag) {
		this.redo_flag = redo_flag;
	}
	public String getExtend_param() {
		return extend_param;
	}
	public void setExtend_param(String extend_param) {
		this.extend_param = extend_param;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	
	
	
}
