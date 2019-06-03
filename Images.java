package xupt.se.ttms.model;

import javafx.scene.image.Image;

public class Images {

    Image image ;
    int width ;
    int height ;

    public Image getImage() {
        return image;
    }

    public void setImage(String path) {
        Image im = new Image(path) ;
        this.image = im;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
