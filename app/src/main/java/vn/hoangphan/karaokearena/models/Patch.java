package vn.hoangphan.karaokearena.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Hoang Phan on 2/27/2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Patch extends RealmObject {
    private String link;

    @PrimaryKey
    private long version;

    @Index
    private boolean updated;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
