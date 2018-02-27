package values;

public class strings {
   public static String messageDeleteConfirmation ="Are you sure wanna delete this record?";
   public static String messageNoSelectedRow="nothing selected";
   public static String messageLoginSuccessful= "Login Successesfully";
   public static String messageFailledLogin="Invalid Username or Password! Please try again";
   public static String messageAddedSuccessful= "Added successfully";
   public static String messageUpdated= "Updated successfully";
   public static String formSignInTitle="Sign In";
   public static String formManagmentTitle="Management";
   public static String usernamedb = "root";
   public static String passworddb = "";
   public static String db = "jdbc:mysql://localhost/javadb";
   public static String dbDriver ="com.mysql.jdbc.Driver";
   public static String db_column1 = "id";
   public static String db_column2 = "title";
   public static String db_column3 = "category";
   public static String db_column4 = "description";
   public static String db_column5 = "image";
   public static String selectImage ="SELECT image FROM tb_movies WHERE id = ";
   public static String selectCategory ="SELECT title FROM tb_category";
   public static String selectAllColumns = "SELECT  id,title,category,description,image FROM tb_movies ORDER BY title ASC ";
   public static String insertInfo ="INSERT INTO tb_movies (title,category,description,image) VALUES (?,?,?,?)";
   public static String deleteRowById ="DELETE  FROM tb_movies WHERE id = ";



}
