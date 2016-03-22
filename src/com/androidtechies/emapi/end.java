package com.androidtechies.emapi;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.json.JSONObject;

import com.androidtechies.utils.TableData;

/**
 * Servlet implementation class end
 */
@WebServlet({"/end","/jhgdfjs"})
public class end extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public end() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	try
	{	Class.forName(TableData.DB_DRIVERS);
	Connection con=DriverManager.getConnection(TableData.CONNECTION_URL,TableData.USERNAME,TableData.PASSWORD);
	PrintWriter out=response.getWriter();
	JSONObject message=new JSONObject();
	
	String TIME_SERVER = "0.in.pool.ntp.org";   
   	NTPUDPClient timeClient = new NTPUDPClient();
   	InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
   	TimeInfo timeInfo = timeClient.getTime(inetAddress);
   	long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
   	//long returnTime = timeInfo.getMessage().getReceiveTimeStamp().getTime();
   	//Date time = new Date(returnTime);
   	Timestamp etime=new Timestamp(returnTime);
	
	
	Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
	String gatesid=request.getParameter("gatesid");
	String eventid=request.getParameter("eventid");
	ResultSet rs=st.executeQuery("select * from "+eventid+"  where gatesid='"+gatesid+"'");
	int flag; 
	if(rs.next())
	{		flag=2;
		rs.previous();
		while(rs.next())
		{
		if(rs.getString(3).equals("2007-01-01 00:00:00"))
		{
			st.executeUpdate("update "+eventid+" set etime='"+etime+"' where gatesid='"+gatesid+"' and etime='2007-01-01 00:00:00'");
			message.put("message","Scan successful");	
			out.println(message.toString());
			flag=1;
			break;
			
		}
		
		}
		if(flag==2)
		{
				
				message.put("message","The user already exited");	
				out.println(message.toString());
			
		}
	}
	else
	{
		message.put("message","Start time of the user doesn't exist");	
		out.println(message.toString());
	}
	
	
	con.close();
		
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		JSONObject message=new JSONObject();
		try{
		message.put("message","Device not connected to network");
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
		
	}
	
	}
	}


