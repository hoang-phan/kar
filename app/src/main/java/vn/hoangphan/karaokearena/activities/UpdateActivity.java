package vn.hoangphan.karaokearena.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
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

public class UpdateActivity extends AppCompatActivity {
    private PatchesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Realm mRealm;
    RealmConfiguration mConfiguration;

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
        mConfiguration = new RealmConfiguration.Builder(this).build();

        mRealm = Realm.getInstance(this);
        mPatchesRv = (RecyclerView) findViewById(R.id.rv_patches);
    }

    private void bindComponents() {
        mAdapter.setOnPatchItemClicked(new OnPatchItemClicked() {
            @Override
            public void update(Patch patch) {
                Toast.makeText(UpdateActivity.this, patch.getLink(), Toast.LENGTH_LONG).show();
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
                DatabaseHelper.save(mRealm, patches);
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
