package com.wm.brta;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.*;
import org.apache.log4j.Logger;

/**
 * @author kmahagao
 *
 */
public class SMParser extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(SMParser.class);
	
	private HttpServletRequest request = null;
	private String userName = "";
	private String appID = "";
	private String[] soloRoles = null;
	private String[] status = null;
	private HashMap groupData = new HashMap();
	private Hashtable<String,String> appUsersData = new Hashtable<String, String>();
	BufferedReader br = null;
       
	public SMParser(HttpServletRequest request, String appID)
	{
		this.request = request;
		this.appID = appID;
		logger.debug("In SMParser constructor, appID="+appID);
		init();
	}

	// This is removed as we are reading user info LDAP headers.
	/*
	public String getUserByEmail(String email){
		return appUsersData.get(email);
	}
	*/
	
	// returns MarketArea Description for the given MarketArea code.
	public String getMarketAreaDescription(String MarketArea){
		return appUsersData.get(MarketArea);
	}

	// returns the 'sm_user' header data... convenience method for legacy apps, should
	// probably not be used by new apps being developed.
	public String getUserName()
	{
		return userName;
	}
	// returns an array with any and all statuses....  typically would have a length of 1
	public String[] getStatus()
	{
		return status;
	}
	// returns a distinct array of Group(FacID)s
	public String[] getGroups()
	{
		return (String[])groupData.keySet().toArray(new String[0]);
	}

	// This returns the roles associated with a specific group(facID)
	public String[] getRoles(String group)
	{
		Vector roles = (Vector)groupData.get(group);
		if (roles==null) return new String[0];
		return (String[])roles.toArray(new String[0]);
	}
	// This method returns only those roles that don't belong to a group(FacID)
	// This is an 'exception' type circumstance (rare).
	public String[] getRoles()
	{
		return soloRoles;
	}

	public void init()
	{
		String sm_groups = "";
		try
		{
			userName = request.getHeader("sm_user").replace('\n',' ').trim();
			logger.debug("In SMParser init, sm_user="+userName);
			
			HttpSession session = this.request.getSession();
			session.setAttribute("LoggedUser",userName );
			
			
			
			String s1 = request.getHeader("sm_employeetype").replace('\n',' ').trim();
			String s2 = request.getHeader("sm_email").replace('\n',' ').trim();
			String s3 = request.getHeader("sm_title").replace('\n',' ').trim();
			String s4 = request.getHeader("sm_employeeid").replace('\n',' ').trim();
			String s5 = request.getHeader("sm_fname").replace('\n',' ').trim();
			String s6 = request.getHeader("sm_lname").replace('\n',' ').trim();

			
			logger.debug(" In SMParser init, Other Headers"+s1+"::"+s2+"::"+s3+"::"+s4+"::"+s5+"::"+s6);
			
		    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		    //InputStream inputStream = classLoader.getResourceAsStream("siteminder_roles.csv");
		    InputStream inputStream = classLoader.getResourceAsStream("MarketArea_Names.csv");
		    
			String line = "";
			String cvsSplitBy = ",";

				br = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = br.readLine()) != null) {
				        // use comma as separator
					String[] country = line.split(cvsSplitBy);
					//String value = country[1]+"|"+country[2]+"|"+country[3]+"|"+country[4]+"|"+country[5]+"|"+country[6]+"|"+country[7]+"|"+country[8];
					//logger.debug("Loading MarketArea - Description details into Hashtable.");
					appUsersData.put(country[0].toLowerCase().trim(),country[1]);
					

				}		
		} catch (FileNotFoundException fe) {
			logger.error("In SMParser init, EXCP 1"+ fe.getMessage());
		} catch (IOException ioe) {
			logger.error("In SMParser init, EXCP 2"+ ioe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ie) {
					logger.error("In SMParser init, EXPC 3"+ie.getMessage());
				}
			}
		}
		try
		{
			sm_groups = request.getHeader("sm_groups").replace('\n',' ').trim();
			logger.info("In SMParser init,sm_groups="+sm_groups);
		}
		catch(Exception e){
			logger.error("In SMParser init, EXPC 4,while getting groups"+e.getMessage());
		}
		
		Vector appData = parseSM(sm_groups);
		popStatus(appData);
		popSoloRoles(appData);
		popGroups(appData);
	}

	private Vector parseSM(String sm_groups)
	{
		String[] pieces = sm_groups.split("\\^");
		Vector retval = new Vector(10,5);
		String thisApp = "ou="+appID+",ou=apps";
		for (int i=0;i<pieces.length;i++)
		{
			if (pieces[i].indexOf(thisApp) < 0 ) continue;
			retval.add(pieces[i]);
		}
		return retval;
	}
	private void popStatus(Vector appData)
	{
		Vector tmpData = new Vector(2,1);
		for (int i=0;i<appData.size();i++)
		{
			String piece = (String)appData.get(i);
			if (piece.indexOf("ou=status") < 0 ) continue;
			String[] pairs = piece.split(",");
			String[] nvs = pairs[0].split("=");
			tmpData.add(nvs[1]);
			appData.remove(i--);
		}
		status = new String[tmpData.size()];
		logger.debug("In SMParser popStatus, status="+Arrays.toString(status));
		tmpData.copyInto(status);
	}
	private void popSoloRoles(Vector appData)
	{
		Vector tmpData = new Vector(2,1);
		for (int i=0;i<appData.size();i++)
		{
			String piece = (String)appData.get(i);
			String[] pairs = piece.split(",");
			if (!pairs[1].equals("ou=roles")) continue;
			String[] nvs = pairs[0].split("=");
			tmpData.add(nvs[1]);
			appData.remove(i--);
		}
		soloRoles = new String[tmpData.size()];
		logger.debug("In SMParser popSoloRoles, SoloRoles="+Arrays.toString(soloRoles));
		tmpData.copyInto(soloRoles);

	}
	private void popGroups(Vector appData)
	{

		for (int i=0;i<appData.size();i++)
		{
			String piece = (String)appData.get(i);
			String[] pairs = piece.split(",");
			if (!pairs[2].equals("ou=roles")) continue;
			String[] nvs = pairs[0].split("=");
			String group = nvs[1];
			nvs = pairs[1].split("=");
			String role = nvs[1];
			Vector roleList = (Vector)groupData.get(group);
			if (roleList==null)
			{
				roleList = new Vector(2,1);
				groupData.put(group,roleList);
			}
			roleList.add(role);
			appData.remove(i--);
		}

	}

}
