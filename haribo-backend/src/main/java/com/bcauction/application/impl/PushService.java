package com.bcauction.application.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class PushService {
	public void MessageSend(String work, String token, String msg) throws Exception {
		final String apiKey = "AAAAySG2Uzs:APA91bGWcNCQCAN3mz2enFFmSdjIL5iinXa6XyPSuDIbnK596MEhQY5IZ3z7B53bkXvNVp-i5xbF8WQN43cHGd0MC25x74mPOjqIZTQvJ_OjlduClzKC9FmtNw5GzrZSomG5bO8jtufc";
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String iconURL = "https://item.kakaocdn.net/do/ece533cdf7d604ac0440418f8cad3028f43ad912ad8dd55b04db6a64cddaf76d";

		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=" + apiKey);

		conn.setDoOutput(true);

		String input = "{\"notification\" : {\"title\" : \" Aution B \",\"icon\" : \"" + iconURL + "\", \"body\" : \""
				+ work + msg + "\"}, \"to\":\"" + token + "\"}";

		OutputStream os = conn.getOutputStream();

		os.write(input.getBytes("UTF-8"));
		os.flush();
		os.close();

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + input);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());

	}

}
