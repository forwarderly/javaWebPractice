package top.gendseo.book.servlet;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.gendseo.book.dao.studentsDao;



/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet/*")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		try {
			String studentsjson = studentsDao.SELECT();
			PrintWriter out = response.getWriter();
			out.write(studentsjson);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("application/plain");
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");

	    if (request.getRequestURI().equals("/webPractice/StudentServlet/DELETE")) //根据项目路径更改
	    {
	      System.out.println("delete");
	      String studentid = request.getParameter("studentid");
	      String result;
	      try {
	        result = studentsDao.DELETE(studentid);
	        PrintWriter out = response.getWriter();
	        out.write(result);
	        out.flush();
	        out.close();
	      } catch (ClassNotFoundException | SQLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }

	    }

	    if (request.getRequestURI().equals("/webPractice/StudentServlet/UPDATE")) {
	      System.out.println("update");

	      BufferedReader reader = request.getReader();
	      String json = reader.readLine();
	      System.out.println(json);
	      reader.close();
	      String result;
	      try {
	        result = studentsDao.UPDATE(json);
	        PrintWriter out = response.getWriter();
	        out.write(result);
	        out.flush();
	        out.close();
	      } catch (ClassNotFoundException | SQLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }
	    }

	    if (request.getRequestURI().equals("/webPractice/StudentServlet/INSERT")) {
	      System.out.println("insert");

	      BufferedReader reader = request.getReader();
	      String json = reader.readLine();
	      System.out.println(json);
	      reader.close();
	      String result;
	      try {
	        result = studentsDao.INSERT(json);
	        PrintWriter out = response.getWriter();
	        out.write(result);
	        out.flush();
	        out.close();
	      } catch (ClassNotFoundException | SQLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }
	    }
	}
}


