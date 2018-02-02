package controllers;

import database.GetterAndSetter;
import database.MovieDB;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static values.strings.*;

public class ManagementController extends Application implements Initializable {

    @FXML
    public Button btnSearch;
    public TextField textTitle, textCategory, textDescription, textFieldSearch;
    public ImageView imgView, imgViewPreview, tvImageView;
    public Label tvDescription, tvTitle;
    public Text tvCountRecords;
    public ComboBox<String> comboBox;
    public Button btnSave,btnUpdate;
    public PieChart pieChart;
    private MovieDB movieDB = new MovieDB();
    @FXML
    TableView<GetterAndSetter> tableview;
    @FXML
    TableColumn idCol;
    @FXML
    TableColumn descriptionCol;
    @FXML
    TableColumn titleCol;
    @FXML
    TableColumn categoryCol;
    private Stage stage = new Stage();
    private File file;
//    public ObservableList data;
    //private ObservableList<GetterAndSetter> data;


    /**
    When this button is clicked it will add StTitle StCategory StDesc and file to database
     */
    public void onClickSaveMovie() throws SQLException, IOException {
        String StTitle = textTitle.getText();
        String StCategory = comboBox.getSelectionModel().getSelectedItem();
        String StDesc = textDescription.getText();
        movieDB.addMovie(StTitle, StCategory, StDesc, file);
        JOptionPane.showMessageDialog(null, messageAddedSuccessful);
        clearAllFields();
        movieDB.refreshTable(tableview,tvCountRecords);
    }

    /**
     * When this button is clicked, it will get data from Searchbox and display it in tableview
     */
    public void onClickSearch() {
        movieDB.searchMovie(tableview, textFieldSearch,tvCountRecords);
    }

    /**
     * When this button is clicked, it will refresh the tableview data
     */
    public void onClickLoadData() throws SQLException, IOException {
        movieDB.refreshTable(tableview,tvCountRecords);
    }

    /**
     *When this button is clicked, it will detect which row in tableview is selected then it will run deleteMovie method
     * if the button okay is clicked
     */
    public void onClickDelete() throws SQLException {
        int selectedIndex = tableview.getSelectionModel().getSelectedIndex();
        if (selectedIndex>=0) {
            Alert alert = new Alert(Alert.AlertType.WARNING, messageDeleteConfirmation, ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                GetterAndSetter tvDt = tableview.getSelectionModel().getSelectedItem();
                movieDB.deleteMovie(tvDt.getId());
                tableview.getItems().remove(selectedIndex);
            }
        }else {
            JOptionPane.showMessageDialog(null,messageNoSelectedRow);
        }
    }

    /**
     *This is where the codes is running when the program first started
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnUpdate.setDisable(true);
        try {
            movieDB.refreshTable(tableview,tvCountRecords);
        } catch (IOException e) {
            e.printStackTrace();
        }
        syncDataSqlWithTableColumn();
        tvDescription.setText("");
        tvTitle.setText("");
        comboBox.getItems().clear();
        tableview.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> c) {
                int selectedIndex = tableview.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    GetterAndSetter tvDt = tableview.getSelectionModel().getSelectedItem(); //TableViewDataType is the class which you have used to create the TableView.
                    tvTitle.setText(tvDt.getTitle());
                    tvDescription.setText(tvDt.getDescription());
                    tvDescription.setWrapText(true);
                    tvDescription.setTextAlignment(TextAlignment.JUSTIFY);
                    try {
                        movieDB.retrieveImage(tvDt.getId(),imgViewPreview);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            movieDB.fillCBTypeOfMovie(comboBox);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    /**
     * This method is used to remove TextField and Image to empty
     */
    private void clearAllFields() {
        textCategory.clear();
        textDescription.clear();
        textTitle.clear();
        imgView.setImage(null);
    }

    /**
     *This button i
     */
    public void onClickChooseImage() throws IOException, SQLException {
        browseImage(stage, imgView);
    }

    /**
     *This method is used for open file browser to select image and preview in Imageview
     */
    private void browseImage(Stage stage, ImageView imgView) throws IOException, SQLException {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fc.getExtensionFilters().addAll(ext1,ext2);
        this.file = fc.showOpenDialog(stage);
        BufferedImage bf;
        bf = ImageIO.read(this.file);
        Image image = SwingFXUtils.toFXImage(bf, null);
        imgView.setImage(image);
    }

    /**
     *This button is getting all the data and put in TextFields
     */
    public void onClickEditData() throws IOException, SQLException {
        GetterAndSetter tvDt = tableview.getSelectionModel().getSelectedItem();
        textTitle.setText(tvDt.getTitle());
        textDescription.setText(tvDt.getDescription());
        comboBox.getSelectionModel().getSelectedItem();
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
    }

    public void onClickUpdate() throws SQLException, IOException {
        GetterAndSetter tvDt = tableview.getSelectionModel().getSelectedItem();
        movieDB.updateMovie(tvDt.getId(),textTitle,comboBox,textDescription);
        JOptionPane.showMessageDialog(null,messageUpdated);
        btnSave.setDisable(false);
        movieDB.refreshTable(tableview,tvCountRecords);
        clearAllFields();
        btnUpdate.setDisable(true);
    }

    private void syncDataSqlWithTableColumn() {
        idCol.setCellValueFactory(
                new PropertyValueFactory<GetterAndSetter, Integer>(db_column1)
        );
        titleCol.setCellValueFactory(
                new PropertyValueFactory<GetterAndSetter, String>(db_column2)
        );
        categoryCol.setCellValueFactory(
                new PropertyValueFactory<GetterAndSetter, String>(db_column3)
        );
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<GetterAndSetter, String>(db_column4)
        );
    }
}