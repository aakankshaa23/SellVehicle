package com.dextroxd.sellvehicle.exploreFragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dextroxd.sellvehicle.activities.filterActivity;
import com.dextroxd.sellvehicle.exploreFragment.adapter_explore.GridAdapter;
import com.dextroxd.sellvehicle.exploreFragment.model_explore.ModelCard;
import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.PostProperty.model.Response;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.support.v7.widget.RecyclerView.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment implements Animation.AnimationListener {
    private final String LINK_URL = "http://www.litstays.com/wp-json/wp/v2/properties";
    int p = 1;
    int to_be_returned = 200;
    private RecyclerView recyclerView;
    private ApiInterface mApiInterface;
    private ImageButton filter_button;
    String cost, bedroom, furnishing;
    private GridAdapter gridAdapter;
    private SkeletonScreen skeletonScreen;
//    private ArrayList<ModelCard> modelCards;
    private int visibleThreshold = 6;
    List<Response> responsesProperty;
    private int currentPage = 1,visibleItemCount,totalItemCount,firstVisibleItem;
    private int previousTotal = 0;
    private boolean loading = true;
    Animation animFadein;


    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         final  View view = inflater.inflate(R.layout.fragment_explore, container, false);
        animFadein = AnimationUtils.loadAnimation(view.getContext(),
                R.anim.fade_in);
//        modelCards = new ArrayList<ModelCard>();
        mApiInterface = ApiUtils.getAPIService();
        responsesProperty =  new ArrayList<>();
        recyclerView = view.findViewById(R.id.id_recycler_explore);
        view.startAnimation(animFadein);
        mApiInterface.getProperty().enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                skeletonScreen.hide();
                Log.e("Hell",response.body().toString());
                responsesProperty = response.body();
                gridAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {

            }
        });
        gridAdapter = new GridAdapter(view.getContext(),responsesProperty);
        filter_button=(ImageButton)view.findViewById(R.id.filter_button);
        filter_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), filterActivity.class));
            }
        });

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(gridAdapter);
        skeletonScreen = Skeleton.bind(recyclerView).adapter(gridAdapter).shimmer(true).count(10).load(R.layout.skeleton_view).show();
//        getJson(LINK_URL,view.getContext());



//       recyclerView.addOnScrollListener(new OnScrollListener() {
//           @Override
//           public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//               super.onScrollStateChanged(recyclerView, newState);
//           }
//
//           @Override
//           public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//               super.onScrolled(recyclerView, dx, dy);
//               if (loading) {
//                   if (dy > 0) //check for scroll down
//                   {
//                       visibleItemCount = linearLayoutManager.getChildCount();
//                       totalItemCount = linearLayoutManager.getItemCount();
//                       firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
//
//                           if (firstVisibleItem + visibleItemCount == totalItemCount) {
//                               loading=false;
//                               p++;
//                               if (getJson(LINK_URL + "?page=" + p, view.getContext()) == 400) {
//                                   return;
////               super.onScrolled(recyclerView, dx, dy);
//                               }
//                           }
//                       }
//
//                   }
//               }
//
//
//
//       });
        return view;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

}










