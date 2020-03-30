package sql;

import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import java.sql.*;

/*
链接数据的步骤：
注册驱动（只做一次）：一个线程只能注册一次，在真实环境里应用程序和数据库不在一台机子上，就像是在河的两边。驱动就是一个物流公司
建立连接（socket）：Connection就是物流公司在桥上建立的一座桥，造桥成本很大。
创建执行语句的对象Statement：Statement就是在桥上来回运送货物的汽车。货物就是sql语句
执行语句
处理执行结果ResultSet：驱动会将结果包装成一个二维表的样子，使用next（）方法读取下一行
释放资源：释放资源的顺序为ResultSet、Statement、Connection，后创建的要先释放。
 */
public class jdbc_test {
    public static void main(String[] args) throws Exception {
        Statement statement = null;
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String user = "root";
            String password = "test";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&useSSL=false", user, password);

            //PreparedStatement：参数化查询，通过connection.preparedStatement(sql)得到，
            // 如果数据库支持的话，系统会对sql语句进行预编译。
            // 预编译就是JDBC在创建prepared对象的时候就将sql语句发送给数据库进行编译。
            // 如果需要动态的发送sql语句时最好使用preparedStatement，好处哟亮点（1）提高效率（2）避免了sql注入。
            // 何为sql注入？就是将一个一条sql语句利用字符串拼接起来的形式，拼接的变量可能会导致sql语句改变原有的意思，也就是说会有恶意sql语法出现如下语句
            //String sql = "SELECT * FROM tb1_students WHERE name= '"+varname+"' and passwd='"+varpasswd+"'";
            PreparedStatement preparedStatement = con.prepareStatement("select * from user_info where id = ? ", 33);
            preparedStatement.execute();

            //批处理，加入缓冲区，其参数开始序号从1开始
            preparedStatement.setString(1, "1");
            preparedStatement.addBatch();
            preparedStatement.setString(1, "2");
            preparedStatement.addBatch();
            preparedStatement.executeBatch();

            //statement：系统只执行一次语句时，用statement来执行，因为如果使用preparedStatement对象系统会将sql语句进行预编译

            // 开销比较大，若只执行一次的语句用preparedStatement不合算。通过connection.createStatement()得到
            // 开销比较大，若只执行一次的语句用preparedStatement不合算。通过connection.createStatement()得到
            // 开销比较大，若只执行一次的语句用preparedStatement不合算。通过connection.createStatement()得到
            statement = con.createStatement();

            //执行存储过程
            CallableStatement callableStatement = con.prepareCall("{call add_pro(?,?,?)");
            callableStatement.setInt(1,4);
            callableStatement.setInt(2,5);
            callableStatement.registerOutParameter(3,Types.INTEGER);
            callableStatement.execute();
            System.out.println("执行结果是:"+callableStatement.getInt(3));

            //boolean execute（sql）执行DDL语句，返回值为boolean类型，true代表有结果集，false代表没有结果集
            //int executeUpdate（sql）执行DML语句，返回值为int类型表示生效的行数
            //ResultSet executeQuery（sql）执行DQL语句，返回值为ResultSet对象，这个对象是语句的结果集
            Boolean bool = statement.execute("CREATE TABLE `user_info` (\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(255) DEFAULT NULL,\n" +
                    "  `age` int(3) DEFAULT NULL,\n" +
                    "  `address` text,\n" +
                    "  `phone` varchar(255) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `index_name` (`name`) USING BTREE\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=10088 DEFAULT CHARSET=utf8 COMMENT='用户信息表';");
            System.out.println(bool);
            int rows = statement.executeUpdate("update user_info set name='新名字' where id=1 ");
            System.out.println(rows);
            ResultSet rs = statement.executeQuery("select * from user_info");
            while (rs.next()) {
                int id = rs.getInt("id");// 获取第一个列的值 编号id
                String name = rs.getString("name");
                System.out.println(id);
                System.out.println(name);
            }

            // 将自动提交设置为 false，若设置为 true 则数据库将会把每一次数据更新认定为一个事务并自动提交
            con.setAutoCommit(false);
            // 将 A 账户中的金额减少 500
            statement.execute("update t_account set amount = amount - 500 where account_id = 'A'");
            // 将 B 账户中的金额增加 500
            statement.execute("update t_account set amount = amount + 500 where account_id = 'B'");
            // 提交事务
            con.commit();
            // 事务提交：转账的两步操作同时成功
        } catch(SQLException sqle) {
            // 发生异常，回滚在本事务中的操做
            con.rollback();
            // 事务回滚：转账的两步操作完全撤销
            statement.close();
            con.close();
        }
    }

    public void XATransaction() {
        UserTransaction userTx = null;
        Connection connA = null;
        Statement stmtA = null;
        Connection connB = null;
        Statement stmtB = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String user = "root";
            String password = "test";
            // 获得 Transaction 管理对象
            InitialContext context = new InitialContext();
            userTx = (UserTransaction)context.lookup("java:comp/UserTransaction");
            connA = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&useSSL=false", user, password);;// 从数据库 A 中取得数据库连接
            connB = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&useSSL=false", user, password);;// 从数据库 B 中取得数据库连接
            userTx.begin(); // 启动事务
            stmtA = connA.createStatement();// 将 A 账户中的金额减少 500
            stmtA.execute("update t_account set amount = amount - 500 where account_id = 'A'");
            // 将 B 账户中的金额增加 500
            stmtB = connB.createStatement();
            stmtB.execute("update t_account set amount = amount + 500 where account_id = 'B'");
            userTx.commit();// 提交事务
            // 事务提交：转账的两步操作同时成功（数据库 A 和数据库 B 中的数据被同时更新）
        } catch(Exception exception) {
            try {
                // 发生异常，回滚在本事务中的操纵
                userTx.rollback();// 事务回滚：数据库 A 和数据库 B 中的数据更新被同时撤销 } catch(Exception ne){ }
            } catch (Exception e) {
            }
        }
    }
}
