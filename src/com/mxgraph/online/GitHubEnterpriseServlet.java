/**
 * Copyright (c) 2006-2017, JGraph Ltd
 * Copyright (c) 2006-2017, Gaudenz Alder
 */
package com.mxgraph.online;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation ProxyServlet
 */
@SuppressWarnings("serial")
public class GitHubEnterpriseServlet extends HttpServlet
{

	/**
	 * Name of env var containing the secret
	 */
	public static final String CLIENT_SECRET_ENV_VAR = "CLIENT_SECRET";

	/**
	 * Name of env var containing the client URL
	 */
	public static final String CLIENT_URL_ENV_VAR = "CLIENT_URL";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GitHubEnterpriseServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String client = request.getParameter("client_id");
		String code = request.getParameter("code");
		
		if (client != null && code != null)
		{
			String url = System.getenv(CLIENT_URL_ENV_VAR);
			String secret = System.getenv(CLIENT_SECRET_ENV_VAR);

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "draw.io");

			String urlParameters = "client_id=" + client + "&client_secret="
					+ secret + "&code=" + code;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer res = new StringBuffer();

			while ((inputLine = in.readLine()) != null)
			{
				res.append(inputLine);
			}
			in.close();
			
			response.setStatus(con.getResponseCode());
			
			OutputStream out = response.getOutputStream();

			// Creates XML for stencils
			PrintWriter writer = new PrintWriter(out);

			// Writes JavaScript and adds function call with
			// stylesheet and stencils as arguments 
			writer.println(res.toString());

			writer.flush();
			writer.close();
		}
		else
		{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			/*
			 * This debug will disclose your secret!
			 */
			/*
			OutputStream out = response.getOutputStream();

			// Creates XML for stencils
			PrintWriter writer = new PrintWriter(out);

			// Writes JavaScript and adds function call with
			// stylesheet and stencils as arguments 
			writer.println("test");
			writer.println(CLIENT_URL_ENV_VAR);
			writer.println(CLIENT_SECRET_ENV_VAR);
			String url = System.getenv(CLIENT_URL_ENV_VAR);
			String secret = System.getenv(CLIENT_SECRET_ENV_VAR);
			writer.println(url);
			writer.println(secret);

			writer.flush();
			writer.close();
			*/
		}
	}

}
