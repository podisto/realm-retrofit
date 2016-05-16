package android.courses.realmretrofit.rest.models;

import android.courses.realmretrofit.models.Content;

import java.util.List;

/**
 * Created by Podisto on 15/05/2016.
 */
public class ContentResponse {

    private String title;
    private String type;
    private double version;
    private List<Content> contents;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
