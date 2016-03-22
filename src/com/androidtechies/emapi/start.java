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
 * Servlet implementation class start
 */

@WebServlet({ "/start","/sofmsvnjfskdfjs" })
public class start extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public start() {
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
		     int flag=0;
		     PrintWriter out=response.getWriter();
				JSONObject message=new JSONObject();

           	String TIME_SERVER = "0.in.pool.ntp.org";   
           	NTPUDPClient timeClient = new NTPUDPClient();
           	InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
           	TimeInfo timeInfo = timeClient.getTime(inetAddress);
           	long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
           	//long returnTime = timeInfo.getMessage().getReceiveTimeStamp().getTime();
           	//Date time = new Date(returnTime);
           	Timestamp stime=new Timestamp(returnTime);
           	
           	
			Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						 ResultSet.CONCUR_READ_ONLY);
			Statement st1=con.createStatement();
			
			
			
			String eventid=request.getParameter("eventid");
			String	gatesid1=request.getParameter("gatesid");
			ResultSet rs=null,rs1=null;
			rs1=st1.executeQuery("select gatesid from gatesreg where gatesid='"+gatesid1+"'");
			System.out.println(gatesid1);
			rs=st.executeQuery("select * from "+eventid+"");
			if(rs1.next())
			{			
		
			
				if(!rs.next())
				{
					st.executeUpdate("insert into "+eventid+" values ('"+gatesid1+"','"+stime+"','2007-1-1')");	
					message.put("message", "scan successful");
					out.println(message.toString());
				}
			else{
						Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs=st2.executeQuery("select * from "+eventid+" where gatesid='"+gatesid1+"'");
				if(!rs.next())
				{

					st.executeUpdate("insert into "+eventid+" values ('"+gatesid1+"','"+stime+"','2007-01-01 00:00:00')");
					message.put("message", "scan successful");
					out.println(message.toString());
				
				}
				else{	
							flag=2;
							rs.previous();
						while(rs.next())
						{
					
									if(rs.getString(3).equals("2007-01-01 00:00:00"))
									{
				
										flag=1;
										break;
									}
									
							}
					
					
				}
			}
			 if(flag==1)
				{
				
					message.put("message", "ID already exist");
					out.println(message.toString());
					
				}
			 else if(flag==2) 
				{
					st.executeUpdate("insert into "+eventid+" values ('"+gatesid1+"','"+stime+"','2007-1-1')");
					message.put("message", "scan successful");
					out.println(message.toString());
					flag=2;
				}
			
	}
	else if(!rs1.next())
	{
		message.put("message", "user not registered");
		out.println(message.toString());
	
	}
			
	con.close();		
	}
		catch(Exception e)
		{
			e.printStackTrace();
		try{
				JSONObject message=new JSONObject();
				message.put("message", "Device not connected to network");
				PrintWriter out=response.getWriter();
				out.println(message.toString());
			}
			catch(Exception f)
			{
				f.printStackTrace();
			}
		}
	}
}


