package vn.hoangphan.karaokearena.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.hoangphan.karaokearena.R;
import vn.hoangphan.karaokearena.callbacks.OnPatchItemClicked;
import vn.hoangphan.karaokearena.models.Patch;

/**
 * Created by Hoang Phan on 2/27/2016.
 */
public class PatchesAdapter extends RecyclerView.Adapter<PatchesAdapter.PatchHolder> {
    private List<Patch> mPatchList = new ArrayList<>();
    private OnPatchItemClicked mOnPatchItemClicked;

    public void setOnPatchItemClicked(OnPatchItemClicked onPatchItemClicked) {
        mOnPatchItemClicked = onPatchItemClicked;
    }

    public void setPatches(List<Patch> patchList) {
        mPatchList = patchList;
    }

    @Override
    public PatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patch, parent, false);
        return new PatchHolder(view);
    }

    @Override
    public void onBindViewHolder(PatchHolder holder, int position) {
        final Patch patch = mPatchList.get(position);
        holder.mVersionTv.setText(patch.getVersion());
        holder.mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPatchItemClicked != null) {
                    mOnPatchItemClicked.update(patch);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPatchList.size();
    }

    public static class PatchHolder extends RecyclerView.ViewHolder {
        private TextView mVersionTv;
        private Button mUpdateBtn;

        public PatchHolder(View itemView) {
            super(itemView);
            mVersionTv = (TextView) itemView.findViewById(R.id.tv_patch_version);
            mUpdateBtn = (Button) itemView.findViewById(R.id.btn_update);
        }
    }
}
