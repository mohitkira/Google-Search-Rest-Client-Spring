package com.velocitos.googlesearch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.velocitos.googlesearch.dto.LocationDTO;

/**
 * Json Reader utility
 * 
 * @author manasa
 *
 */
public class JsonUtil {

	/**
	 * Read the json string
	 * 
	 * @param rd
	 * @return
	 * @throws IOException
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * Reading json url as JSONObject
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public List<LocationDTO> getDetails(String url) throws IOException {
		JSONObject json;
		JSONArray locationResults = null;
		List<LocationDTO> locationlist = new ArrayList<LocationDTO>();
		try {
			json = readJsonFromUrl(url);
			locationResults = (JSONArray) json.get("results");
			JSONObject locations = new JSONObject();

			for (int i = 0; i < locationResults.length(); i++) {

				JSONObject jsonobj = locationResults.getJSONObject(i);
				JSONObject geoMetryObject = jsonobj.getJSONObject("geometry");
				locations = geoMetryObject.getJSONObject("location");
				double lat = locations.getDouble("lat");
				double lon = locations.getDouble("lng");
				String jsonObject = jsonobj.getString("formatted_address");

				LocationDTO locationDTO = new LocationDTO();
				locationDTO.setLat(lat);
				locationDTO.setLng(lon);
				locationDTO.setAddress(jsonObject);

				locationlist.add(locationDTO);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return locationlist;

	}

}
