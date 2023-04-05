package com.techpalle.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techpalle.dao.AdminDao;
import com.techpalle.dao.CustomerDao;
import com.techpalle.model.Customer;


@WebServlet("/")
public class Controller extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String path = request.getServletPath();
		switch(path)
		{
		case"/delete":
			deleteCustomer(request,response);
		
		
		
		case"/edit":
			editCustomer(request,response);
			break;
		
		
		case"/editForm":
			getEditForm(request,response);
			break;
		case "/insertForm":
			getInsertForm(request,response);
			break;
		
		case "/add":
		addCustomer(request,response);
		break;
		
		case"/list":
			validateAdmin(request,response);
			break;
			
			default:
			getStartUpPage(request,response);
			break;
		
		}
	}
	
    private void getCustomerListpage(HttpServletRequest request, HttpServletResponse response)
    {
    	try 
		{
			
		ArrayList<Customer> alCustomer = CustomerDao.getAllCustomer();
		RequestDispatcher rd = request.getRequestDispatcher("customer.list.jsp");
		request.setAttribute("al",alCustomer);
		rd.forward(request, response);
		
		}
		catch (ServletException e)
		{
		
			e.printStackTrace();
		}
		catch (IOException e)
		{
		
			e.printStackTrace();
		}
		
		
    	
    }
	
	
	private void validateAdmin(HttpServletRequest request, HttpServletResponse response)
	{
		// read the username and password
		String u= request.getParameter("tbUser");
		String p = request.getParameter("tbPass");
		
		
		
		// call the dao method to validate admin
		boolean res=AdminDao.validateAdmin(u, p);
		
		//condtion to redirect admin to list page
		if(res)
		{
          getCustomerListpage(request,response);
			
		}
		else
		{
		  try 
		  {
			response.sendRedirect("/default");
		  }
		  catch (IOException e)
		  {
		
			e.printStackTrace();
		  }
		}
		
		
	
	}

	
	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
	{
		// read the id form url
		int i =Integer.parseInt(request.getParameter("id"));
		
		
		
		CustomerDao.deleteCustomer(i);
		
		// redirect customer list page
		/*try
		{
			response.sendRedirect("list");
		}
		catch (IOException e1)
		{
			
			e1.printStackTrace();
		}*/
		
		}
		
		
	


	private void editCustomer(HttpServletRequest request, HttpServletResponse response) {
		
	int i=Integer.parseInt(request.getParameter("tbId"));
	String n=request.getParameter("tbName");
	String e=request.getParameter("tbEmail");
	long m =Long.parseLong(request.getParameter("tbMobile"));
	
	Customer c = new Customer(i,n,e,m);
	
	CustomerDao.editCustomer(c);
	
	// redirect user to customer list page
	/*try
	{
		response.sendRedirect("list");
	}
	catch (IOException e1)
	{
		
		e1.printStackTrace();
	}*/
	
	}


	private void getEditForm(HttpServletRequest request, HttpServletResponse response)
	{
		// Edit the id from url
		int i = Integer.parseInt(request.getParameter("id"));
		
		Customer c = CustomerDao.getOneCustomer(i);
		     
		try
		{
			RequestDispatcher rd = request.getRequestDispatcher("customer.form.jsp");
			request.setAttribute("customer", c);
			rd.forward(request,response);
			
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
			
		}
	


	private void addCustomer(HttpServletRequest request, HttpServletResponse response)
	{
		String n = request.getParameter("tbName");
		String e = request.getParameter("tbEmail");
		long m = Long.parseLong(request.getParameter("tbMobile"));
		
		
		//store the admin given data into model/ abject
		Customer c = new Customer(n, e, m);
		
		//Insert Customer data to DB
		CustomerDao.addCustomer(c);
		
		//redirect admin to home page(Customer.list page)
	
		
		
	}


	private void getStartUpPage(HttpServletRequest request, HttpServletResponse response)
	{
	try
	{
		RequestDispatcher rd = request.getRequestDispatcher("admin-login.jsp");
		rd.forward(request, response);
		
	}
	catch (ServletException e)
	{
		e.printStackTrace();
	}
	catch (IOException e) 
	{
		
		e.printStackTrace();
	}
		
	}


	private void getInsertForm(HttpServletRequest request, HttpServletResponse response) 
	{
		try
		{
			RequestDispatcher rd =request.getRequestDispatcher("customer.form.jsp");
			rd.forward(request, response);
		}
		
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			
			e.printStackTrace();
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		doGet(request, response);
	}

}
