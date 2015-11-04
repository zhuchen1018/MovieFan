package com.myapp.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.myapp.storage.entity.WebPageEntity;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*
 * 1 fetch file contents from url
 * 2 use tidy to clean the html files
 * 3 XPathEvaluator use it to get DOM
 * 4 or Crawler use it to download the page
 */

public class HttpClient 
{
	private final static int GETtimeout = 10000;
	private final static int HEADtimeout = 5000;
	private final static int defaultTimeout = 10000;
	
	private final static int HTTPS = 1;
	private final static int HTTP = 2;
	private final static int INVALID_PROTOCOL  = 3;
	
	private final static String GET = "GET";
	private final static String HEAD = "HEAD";

	/*for extracting href token in HTML files*/
	private final static Pattern href_pattern  = Pattern.compile("[Hh][Rr][Ee][Ff]\\s*=\\s*\"(.*?)\"");

	/*TODO:for extracting link token in XML files*/
	//private final static Pattern link_pattern; 

	public static int decideProtocol(URL url) throws IOException 
	{
        if (url.getProtocol().equalsIgnoreCase("https")) 
        { 
        	return HTTPS;
        } 
        else if (url.getProtocol().equalsIgnoreCase("http")) 
        {
        	return HTTP;
        }
        print("decideProtocol: INVALID_PROTOCOL: " + url.getPath());
        return INVALID_PROTOCOL;
	}

	/*
	 * send request with socket 
	 */
	public static void sendRequst(String path, String host, Socket socket, String method) throws UnknownHostException, IOException
	{
		String headers = method + " " + path + " HTTP/1.1\r\n";
		headers = headers + "Host: " + host+ "\r\n";
		headers = headers + "Connection: close\r\n\r\n";

		print("sendRequest: "); 
		print(headers);

		PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		out.write(headers);
		out.flush();
	}

	public static String readContent(BufferedReader in) throws IOException 
	{
		String line;
		String content = "";
		while((line = in.readLine())!=null)
		{
			if(line.trim().isEmpty())
			{
				continue;
			}
			content += line;
			content += "\n";
		}
		return content;
	}

	public static void writeFile(String name, String content) throws IOException
	{
		File file = new File(name);
		if (!file.exists()) 
		{
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	}

	public static String stripNonValidXMLCharacters(String in) 
	{
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.
        if (in == null || ("".equals(in))) return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) 
        {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }    

	public static Document parseDOM(String url, String content) throws ParserConfigurationException, SAXException, IOException
	{
		content = stripNonValidXMLCharacters(content);
		Document doc;
		if(url.endsWith(".xml"))
		{
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(content));
			doc = db.parse(is);
			doc.getDocumentElement().normalize();
		}
		else /*like HTML*/
		{
			/*
			 * clean the format
			 */
			Tidy tidy = new Tidy(); 
			tidy.setInputEncoding("UTF-8");
			tidy.setOutputEncoding("UTF-8");
			tidy.setXmlOut(true);
			tidy.setSmartIndent(true);
			tidy.setShowWarnings(false);
			tidy.setXmlTags(true);
			tidy.setForceOutput(true);
			InputStream is = new ByteArrayInputStream(content.getBytes());
			doc = tidy.parseDOM(is,null);
			doc.getDocumentElement().normalize();
		}
		return doc;
	}

	public static String readFiletoString(File file) throws IOException 
	{
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");
	    try 
	    {
	        while(scanner.hasNextLine()) 
	        {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } 
	    finally 
	    {
	        scanner.close();
	    }
	}

	public static HttpURLConnection getHttpURLConnection(URL url_ins, int timeout, String method) throws IOException
	{
        HttpURLConnection uc  = (HttpURLConnection) url_ins.openConnection();
        uc.setConnectTimeout(timeout);
        uc.setRequestMethod(method);
        uc.connect();
        return uc;
	}

	public static String fetchFileContent(String url) 
	{
		try 
		{
			File 	f = new File(url);
			if(!f.exists()) 
			{
				BufferedReader in = getBufferFromUrl(url, GET); 
				return readContent(in);
			}
			else /*if file exists*/ 
			{
				return readFiletoString(f); 
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static int getTimeout(String method)
	{
		if(method.equals(GET)) 
		{
			return GETtimeout;
		}
		else if(method.equals(HEAD))
		{
			return HEADtimeout; 
		}
		return defaultTimeout;
	}

	public static BufferedReader getBufferFromUrl(String url, String method) throws UnknownHostException, IOException
	{
		int timeout;
		if(method.equals(GET) || method.equals(HEAD))
		{
			if(method.equals(GET))
			{
				timeout = GETtimeout;
			}
			else
			{
				timeout = HEADtimeout;
			}
		}
		else
		{
			print("inValid request method: " + method);
			return null;
		}
		
		URL addr = new URL(url);
		int protocol = decideProtocol(addr);
		if(protocol == HTTP)
		{
			String host = addr.getHost();
			String path = addr.getPath();
			if(path.trim().isEmpty())
			{
				path = "/";
			}
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(host, 80), timeout);
			sendRequst(path, host, socket, method);
			InputStream in;
			try
			{
				in = socket.getInputStream();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				socket.close();
				return null;
			}
			return new BufferedReader(new InputStreamReader(in));
		}
		else if(protocol == HTTPS)
		{
			 /* 
			  * Notice: don't use this to fetch HEAD, cause only contains headers
			 */
			if(method.equals(HEAD))
			{
				return null;
			}

			HttpURLConnection uc = getHttpURLConnection(addr, timeout, method);
			boolean redirect = false;
			try
			{
				int sc = uc.getResponseCode();
				if(sc != HttpURLConnection.HTTP_OK) 
				{
					print(url + " response code: " + sc);
					if(!needRedirect(sc))
					{
						return null;
					}
					String new_url = uc.getHeaderField("Location");
					if(new_url != null && !new_url.equalsIgnoreCase(url))
					{
						return getBufferFromUrl(new_url, method); 
					}
				}
			}
			catch(IOException e)
			{
				print("getResponseCode failed....");
				e.printStackTrace();
				return null;
			}
			try
			{
				InputStream in;
				in = (InputStream) uc.getInputStream();
				return new BufferedReader(new InputStreamReader(in));
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * redirect status code: 301-303 
	 * @param status code 
	 * @return
	 */
	private static boolean needRedirect(int sc) 
	{
		return sc == HttpURLConnection.HTTP_MOVED_PERM
				|| sc == HttpURLConnection.HTTP_MOVED_TEMP 
				|| sc == HttpURLConnection.HTTP_SEE_OTHER;
	}

	public static Document downloadFileDOM(String url) 
	{
		try 
		{
			File f = new File(url);
			if(!f.exists()) 
			{
				BufferedReader in = getBufferFromUrl(url, GET); 
				String content = readContent(in);
				return parseDOM(url, content);
			}
			else /*if file exists*/ 
			{
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setIgnoringComments(true);
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(f);
				doc.getDocumentElement().normalize();
				return doc;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Find all the ref links within a string 
	 */
	public static ArrayList<String> getRefUrls(String page_content) 
	{
		ArrayList<String>links = new ArrayList<String>();
		Matcher m = href_pattern.matcher(page_content);
		while(m.find()) 
		{
			String url = m.group(1);
			print("get href: ==== " + url);
			links.add(url);
		}
		return links;
	}
	
	public static void print(String s)
	{
		System.out.println(s);
	}

	/*
	 *send HEAD to url 
	 *return response headers 
	 */
	public static Map<String, List<String>> fetchHEAD(String url) throws IOException 
	{
		URL addr = new URL(url);
		int protocol = decideProtocol(addr);
		if(protocol == HTTP)
		{
			BufferedReader reader = getBufferFromUrl(url, HEAD);
			return parseHeaders(reader); 
		}
		else if(protocol == HTTPS)
		{
			HttpURLConnection uc = getHttpURLConnection(addr, HEADtimeout, HEAD);
			try
			{
				int sc = uc.getResponseCode();
				if(sc != HttpURLConnection.HTTP_OK) 
				{
					print(url + " response code: " + sc);
					if(!needRedirect(sc))
					{
						return null;
					}
					String new_url = uc.getHeaderField("Location");
					if(new_url != null && !new_url.equalsIgnoreCase(url))
					{
						return fetchHEAD(new_url); 
					}
				}
				return uc.getHeaderFields();
			}
			catch(IOException e)
			{
				return null;
			}
		}
		return null;
	}
	
	
	private static int readInitialLine(BufferedReader reader) throws IOException 
	{
		String line;
		while((line = reader.readLine()) != null)
		{
			if(!line.isEmpty())
			{
				break;
			}
		}
		if(line == null)
		{
			print("line is null............");
			return -1;
		}

		//eg: HTTP/1.1 200 OK
		String res[] = line.split(" ");
		if (res.length < 3) 
		{
			print("Invalid header: initial line length != 3");
			return -1;
		}
		try
		{
			int status_code = Integer.parseInt(res[1]);
			print("status code: " + status_code);
			return status_code;
		}
		catch(NumberFormatException e)
		{
			print("Parse status_code error: " + res[1]);
			return -1;
		}
	}

	public static Map<String, List<String>> parseHeaders(BufferedReader reader) throws IOException 
	{
		if(reader == null)
		{
			return null;
		}
		int sc = readInitialLine(reader);
		if(sc == -1)
		{
			return null;
		}

		boolean redir  = false; 
		if(sc != HttpURLConnection.HTTP_OK)
		{
			redir = needRedirect(sc);
			if(!redir)
			{
				return null;
			}
		}
		String line; 
		String last_header = "";
		Map<String, List<String>>headers = new HashMap<String, List<String>>();

		//print("Show headers:");
		while((line = reader.readLine()) != null)
		{
			line = line.trim();
			if(line.isEmpty()) 
			{
				continue;
			}
			//print(line);
			String parts[] = line.split(":", 2);
			if (parts.length > 1) 
			{ 
				//eg: header1: v1, v2 
				List<String> values = headers.get(parts[0]);
				if (values == null) 
				{
					values = new ArrayList<String>();
				}
				values.add(parts[1].trim());
				headers.put(parts[0], values);
				last_header = parts[0];
			} 
			else 
			{ 
				//eg: value1, value2 
				List<String> values = headers.get(last_header);
				if(values == null)
				{
					print("ERROR Header: " + line);
					return null;
				}
				values.add(parts[0].trim());
				headers.put(last_header, values);
			}
		}
		return headers;
	}
}
