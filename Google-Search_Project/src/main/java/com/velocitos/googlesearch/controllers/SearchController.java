package com.velocitos.googlesearch.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.velocitos.googlesearch.dto.LocationDTO;
import com.velocitos.googlesearch.util.JsonUtil;

@Controller
public class SearchController {

	@RequestMapping(value = "/searchaddress", method = RequestMethod.GET)
	public String welcome(@RequestParam("address") String address,
			@RequestParam("zipcode") String zipcode, ModelMap model) {

		List<LocationDTO> locationList= null;
		String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
				+ address + "&zipcode=" + zipcode;
		JsonUtil jsonObject = new JsonUtil();
		try {
			locationList = jsonObject.getDetails(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("locationbo", locationList);

		return "map";

	}

}
