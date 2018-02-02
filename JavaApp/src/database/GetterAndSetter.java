package database;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class GetterAndSetter {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty  title = new SimpleStringProperty();
    private StringProperty  category = new SimpleStringProperty();
    private StringProperty  description = new SimpleStringProperty();;
    private Image image;
    //Getters
    public Integer getId() {
        return id.get();
    }
    public String getTitle() {
        return title.get();
    }
    public Image getImage() {
        return image;
    }

    public String getCategory() {
        return category.get();
    }
    public String getDescription() {
        return description.get();
    }
    //Setters
    void setId(Integer value) {
         id.set(value);
    }
    void setImage(Image value) { image.equals(value);
    }
    void setTitle(String value) {
        title.set(value);
    }
    void setCategory(String value) {
        category.set(value);
    }
    void setDescription(String value) {
        description.set(value);
    }
    //Property values
    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty titleProperty() {
        return title;
    }
    public StringProperty categoryProperty() {
        return category;
    }
    public StringProperty descriptionProperty() {
        return description;
    }

}
