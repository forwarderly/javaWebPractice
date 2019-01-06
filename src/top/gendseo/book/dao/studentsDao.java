package top.gendseo.book.dao;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import com.google.gson.Gson;

import top.gendseo.book.pojo.StudentBean;
import top.gendseo.book.pojo.student;

public class studentsDao extends StudentDao{
	/*
	 * 序列化和反序列化 JSON 的 GOOGLE 插件
	 */
	private static Gson gson = new Gson();
	
	public static String SELECT() throws ClassNotFoundException, SQLException {
		/*
		 * n 本书的集合列表 studentsList
		 * 声明 student 的 Entity 实体类
		 * 实际参见 com.dyc.javawebstudent.entity.student
		 */
		Connection con = StudentDao.getConnection();
		String sql = "select * from student";
		PreparedStatement ps = con.prepareStatement(sql);
		List<student> studentList = new ArrayList<student>();
		// 获得查询出来的结果集合
		ResultSet rs =ps.executeQuery();
		// 如果结果集合不为空 do while
		while (rs.next()) {
			student student = new student();
			// 声明一本书的类，并且往里添加数据，一一对应
			student.setstudentid(rs.getInt("studentid"));
			student.setstudentame(rs.getString("studentname"));
			student.setinstitute(rs.getString("institute"));
			student.setcourse(rs.getInt("course"));
			student.setintake(rs.getInt("inntake"));
			// 最后把这本书添加到书的集合列表 studentsList
			studentList.add(student);
		}
		/*
		 * n 本书的集合列表 studentBean
		 * 声明 student 的 Entity 实体类
		 * 实际参见 com.dyc.javawebstudent.entity.studentBean
		 */
		StudentBean studentBean = new StudentBean();
		// 图书的列表
		studentBean.setstudents(studentList);
		// 图书的总数
		studentBean.setTotal(String.valueOf(studentList.size()));
		StudentDao.closeAll(con, ps, rs);
		return gson.toJson(studentBean);
	}

	/*
	 * 删除操作
	 */
	public static String DELETE	(String id) throws ClassNotFoundException, SQLException {
		try {
			Connection con = StudentDao.getConnection();
			String sql = "DELETE FROM book WHERE id=?;";
			System.out.println(sql);
			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setInt(1, Integer.parseInt(id));
			ps.executeUpdate();
			StudentDao.closeAll(con, ps, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "true";
	}

	/*
	 * 更新操作
	 */
	public static String UPDATE(String json) throws ClassNotFoundException, SQLException {
		// 使用 Gson 将 JSON 转换成 emtity 实体类 student
		student student = gson.fromJson(json, student.class);
		try {
			Connection con = StudentDao.getConnection();
			String sql = "update student set studentname = ? ,intake =? ,course = ? ,institute = ? where id = ? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student.getstudentname());
			ps.setString(2, student.getinsititute());
			ps.setInt(4, student.getstudentid());
			ps.setInt(3, student.getintake());
			ps.setInt(5, student.getcourse());
			ps.executeUpdate();
			StudentDao.closeAll(con, ps, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "true";
	}
	/*
	 * 增加操作
	 */
	public static String INSERT(String json) throws ClassNotFoundException, SQLException {
		// 使用 Gson 将 JSON 转换成 emtity 实体类 student
		student student = gson.fromJson(json, student.class);
		try {
			Connection con = StudentDao.getConnection();
			String sql = "INSERT INTO student (name,institute,intake,course) values(?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, student.getstudentname());
			ps.setString(2, student.getinsititute());
			ps.setInt(4, student.getstudentid());
			ps.setInt(3, student.getintake());
			ps.setInt(5, student.getcourse());
			ps.executeUpdate();
			StudentDao.closeAll(con, ps, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "true";
	}
}

