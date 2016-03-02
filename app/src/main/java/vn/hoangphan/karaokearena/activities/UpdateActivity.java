package vn.hoangphan.karaokearena.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import vn.hoangphan.karaokearena.R;
import vn.hoangphan.karaokearena.adapters.PatchesAdapter;
import vn.hoangphan.karaokearena.callbacks.OnPatchItemClicked;
import vn.hoangphan.karaokearena.db.DatabaseHelper;
import vn.hoangphan.karaokearena.models.Patch;
import vn.hoangphan.karaokearena.models.net.PatchesResponse;
import vn.hoangphan.karaokearena.net.APIService;
import vn.hoangphan.karaokearena.tasks.UpdateService;

public class UpdateActivity extends AppCompatActivity {
    private PatchesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mPatchesRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initComponents();
        bindComponents();
    }

    private void initComponents() {
        mAdapter = new PatchesAdapter();
        mLayoutManager = new LinearLayoutManager(this);

        DatabaseHelper.init(this);
        mPatchesRv = (RecyclerView) findViewById(R.id.rv_patches);
    }

    private void bindComponents() {
        mAdapter.setOnPatchItemClicked(new OnPatchItemClicked() {
            @Override
            public void update(Patch patch) {
                UpdateService.addToDownload(patch);
                startService(new Intent(UpdateActivity.this, UpdateService.class));
            }
        });
        mPatchesRv.setLayoutManager(mLayoutManager);
        mPatchesRv.setAdapter(mAdapter);

        Call<PatchesResponse> response = APIService.getInstance().getPatches();
        response.enqueue(new Callback<PatchesResponse>() {
            @Override
            public void onResponse(Response<PatchesResponse> patchesResponse, Retrofit retrofit) {
                List<Patch> patches = patchesResponse.body().getPatches();
                Log.e("patches", patches.toString());
                DatabaseHelper.getInstance().save(patches);
                mAdapter.setPatches(patches);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Error", t.toString());
            }
        });
    }
}
