package study.liangyehong.android.thenewsdemo;

/**
 * Created by Curtain_Liang on 2018/6/23.
 */

public class News {
    private String title;
    private String description;
    private String image;
    private String type;
    private String comment;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
