package top.gendseo.book.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDao {

	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/Student";
	private static String user = "root";
	private static String password = "root";
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public static void closeAll(Connection con, Statement stmt, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (con != null) {
			con.close();
		}
	}

	public int executeSQL(String preparedSql, Object[] param) throws ClassNotFoundException {
		Connection con = null;
		PreparedStatement ps = null;
		/* 处理SQL,执行SQL */
		try {
			con = getConnection(); // 得到数据库连接
			ps = con.prepareStatement(preparedSql); // 得到PreparedStatement对象
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					ps.setObject(i + 1, param[i]); // 为预编译sql设置参数
				}
			}
			ResultSet rs = ps.executeQuery(); // 执行SQL语句
		} catch (SQLException e) {
			e.printStackTrace(); // 处理SQLException异常
		} finally {
			try {
				StudentDao.closeAll(con, ps, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

}


