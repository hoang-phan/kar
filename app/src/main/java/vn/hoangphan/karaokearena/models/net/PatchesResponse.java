package vn.hoangphan.karaokearena.models.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import vn.hoangphan.karaokearena.models.Patch;

/**
 * Created by Hoang Phan on 2/27/2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatchesResponse {
    private List<Patch> patches;

    public List<Patch> getPatches() {
        return patches;
    }

    public void setPatches(List<Patch> patches) {
        this.patches = patches;
    }
}
