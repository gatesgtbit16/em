package com.androidtechies.emapi;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.androidtechies.utils.TableData;
/**
 * Servlet implementation class show
 */
@WebServlet({"/show","/xfdfgdfg"})
public class show extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public show() {
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
		{
			Class.forName(TableData.DB_DRIVERS);
			Connection con=DriverManager.getConnection(TableData.CONNECTION_URL,TableData.USERNAME,TableData.PASSWORD);
			String eventid=request.getParameter("eventid");
			PrintWriter out=response.getWriter();
			
			JSONArray details=new JSONArray();
			Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=st.executeQuery("select t2.gatesid,name,contact,EXTRACT(epoch from stime) from gatesreg t1, "+eventid+" t2 where t1.gatesid=t2.gatesid and t2.etime='2007-01-01 00:00:00'");
			if(rs.next())
			{
				rs.previous();
				while(rs.next())
				{	JSONObject data=new JSONObject();
					data.put("gatesid",rs.getString(1) );
					data.put("name", rs.getString(2));
					data.put("contact", rs.getString(3));
					data.put("stime", rs.getString(4));
					details.put(data);

				
				
						
				}
				
				out.println(details.toString());
			}
			else
			{JSONObject message=new JSONObject();
				message.put("message","NO DATA AVAILABLE" );
				out.println(message.toString());
			}
			con.close();
		}
		catch(Exception E)
		{E.printStackTrace();
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


