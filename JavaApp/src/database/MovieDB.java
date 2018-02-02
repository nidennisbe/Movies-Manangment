package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import static values.strings.*;

public class MovieDB {
    private ObservableList<GetterAndSetter> data;
    private ObservableList dataG;

    /**
     * This method handle inserting values to mysql Database
     * @param StTitle
     * @param StCategory
     * @param StDesc
     * @param file
     * @throws SQLException
     * @throws IOException
     */
//Insert new movie
    public void addMovie(String StTitle, String StCategory, String StDesc, File file) throws SQLException, IOException {
        Connection connection = MysqlDBConnector.connectdb();
        FileInputStream fin = new FileInputStream(file);
        int len = (int) file.length();
            String query = insertInfo;
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, StTitle);
            preparedStatement.setString(2, StCategory);
            preparedStatement.setString(3, StDesc);
            preparedStatement.setBinaryStream(4, fin, len);
            preparedStatement.executeUpdate();
            connection.close();
    }

    /**
     * This method handle edit movies data
     * @param id
     * @param textTitle
     * @param comboBox
     * @param textDescription
     * @throws SQLException
     */
    public void updateMovie(int id,TextField textTitle,ComboBox comboBox,TextField textDescription) throws SQLException {
        Connection connection = MysqlDBConnector.connectdb();
        assert connection != null;
        Statement stmt = connection.createStatement();
        String updateQuery = "UPDATE tb_movies SET title='" + textTitle.getText() + "',category='" + comboBox.getSelectionModel().getSelectedItem() + "', description='" + textDescription.getText() + "' WHERE id = " + id;
        stmt.executeUpdate(updateQuery);
        connection.close();
    }

    /**
     * This method handle delete query by specific unique id
     * @param id
     */
    public void deleteMovie (int id) throws SQLException {
        Connection connection = MysqlDBConnector.connectdb();
        assert connection != null;
        Statement stmt = connection.createStatement();
        String deleteQuery = deleteRowById + id;
        stmt.executeUpdate(deleteQuery);
        connection.close();
    }

    /**
     * This method used for searching specific words from column title in mysql database
     * @param tableview
     * @param textFieldSearch
     * @param tvCountRecords
     */
    public void searchMovie(TableView<GetterAndSetter> tableview, TextField textFieldSearch, Text tvCountRecords) {
        int count=0;
        try {
            data = FXCollections.observableArrayList();
            Connection connection = MysqlDBConnector.connectdb();
            String query = "SELECT * FROM tb_movies WHERE title LIKE('" + textFieldSearch.getText() + "%')";
            assert connection != null;
            ResultSet results = connection.createStatement().executeQuery(query);
            while (results.next()) {
                ++count;
                getMovieDetailFromResultSet(results);
            }
            // assign the data to the table
            tableview.setItems(data);
            tableview.getSelectionModel().selectFirst();
            tvCountRecords.setText(String.valueOf(count));
            connection.close();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method do the select query all data from database
     * @param tableview
     * @param tvCountRecords
     */
    public void refreshTable(TableView<GetterAndSetter> tableview, Text tvCountRecords) throws IOException {
        int count=0;
        try {
            data = FXCollections.observableArrayList();
            Connection connection = MysqlDBConnector.connectdb();
            String query = selectAllColumns;
            assert connection != null;
            ResultSet results = connection.createStatement().executeQuery(query);
            while (results.next()) {
                ++count;
                getMovieDetailFromResultSet(results);
            }
            // assign the data to the table
            tvCountRecords.setText(String.valueOf(count));
            tableview.setItems(data);
            tableview.getSelectionModel().selectFirst();
            connection.close();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id It used to identify the unique id inside database table
     * @param imgViewPreview It is a ImageView where it shows image of selected row in database
     */
    public void retrieveImage(int id, ImageView imgViewPreview) throws SQLException, IOException {
        Connection connection = MysqlDBConnector.connectdb();
        Image image = null;
        String query = selectImage + id;
        assert connection != null;
        ResultSet rs = connection.createStatement().executeQuery(query);
        while (rs.next()) {
            InputStream is = rs.getBinaryStream(db_column5);
            BufferedImage imBuff = ImageIO.read(is);  //converting to buffered image
            image = SwingFXUtils.toFXImage(imBuff, null);  //converting to    FXimage
        }
            imgViewPreview.setImage(image);  //setting image to imageview
            imgViewPreview.setPreserveRatio(true);
            connection.close();
    }

    /**This method is used for loading all the combobox from database
     * @param comboBox It is a ComboBox from Management Controller
     *
     */
    public void fillCBTypeOfMovie(ComboBox comboBox) throws SQLException {
            ObservableList<String> comboString = FXCollections.observableArrayList();
            Connection connection = MysqlDBConnector.connectdb();
            String query = selectCategory;
        assert connection != null;
        ResultSet rs = connection.createStatement().executeQuery(query);
            while (rs.next()) {
                //get string from db,whichever way
                comboString.add(rs.getString(db_column2));
            }
            comboBox.setItems(comboString);
            connection.close();
    }
    private void drawGraph() throws SQLException {

    }

    /**This method is used for get database column and set it to ObservableList arraylist
     * @param results get query result from Resultset
     */
    private void getMovieDetailFromResultSet(ResultSet results) throws SQLException, IOException, ClassNotFoundException {
        GetterAndSetter getterAndSetter = new GetterAndSetter();
        getterAndSetter.setId(results.getInt(db_column1));
        getterAndSetter.setTitle(results.getString(db_column2));
        getterAndSetter.setCategory(results.getString(db_column3));
        getterAndSetter.setDescription(results.getString(db_column4));
        data.add(getterAndSetter);
    }

//    public void drawGraph(PieChart pieChart) throws SQLException {
//        data = FXCollections.observableArrayList();
//        Connection connection = MysqlDBConnector.connectdb();
//        String Query = "SELECT id, category FROM tb_movies GROUP BY category";
//        assert connection != null;
//        //Statement stmt = connection.createStatement();
//        ResultSet results = connection.createStatement().executeQuery(Query);
//        while (results.next()) {
//            dataG.add(new PieChart.Data(results.getString(2),results.getInt(1)));
//
//        }
//        pieChart.getData().add(dataG);
//
//        connection.close();
//    }
}
