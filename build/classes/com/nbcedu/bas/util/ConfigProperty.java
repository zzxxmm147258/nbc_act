package com.nbcedu.bas.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("configProperty")
public class ConfigProperty {

	@Value("#{configProperties['save_path']}")
	private String save_path;
	@Value("#{configProperties['error_image']}")
	private String error_image;

	public String getSave_path() {
		return save_path;
	}

	public void setSave_path(String save_path) {
		this.save_path = save_path;
	}

	public String getError_image() {
		return error_image;
	}

	public void setError_image(String error_image) {
		this.error_image = error_image;
	}

}
