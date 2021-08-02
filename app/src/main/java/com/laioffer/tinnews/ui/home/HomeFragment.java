package com.laioffer.tinnews.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.laioffer.tinnews.MainActivity;
import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.FragmentHomeBinding;
import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.repository.NewsRepository;
import com.laioffer.tinnews.repository.NewsViewModelFactory;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.List;


public class HomeFragment extends Fragment implements CardStackListener {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private CardStackLayoutManager layoutManager;//多个方法会用这个class variable 所以定义在这里
    private List<Article> articles;
    private CardSwipeAdapter swipeAdapter;



    public HomeFragment() {
        // Required empty public constructor

    }

    //提供这个fragment的layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


// Setup CardStackView
        swipeAdapter = new CardSwipeAdapter();
        layoutManager = new CardStackLayoutManager(requireContext(), this);
        layoutManager.setStackFrom(StackFrom.Top);
        binding.homeCardStackView.setLayoutManager(layoutManager);
        binding.homeCardStackView.setAdapter(swipeAdapter);


        NewsRepository repository = new NewsRepository();


//        viewModel = new HomeViewModel(repository);  这个不好，每次onViewCreated 都要new 用viewprovider

        //Fragment implements the ViewModelStoreOwner interface's getViewModelStore()  本质里面用的 map
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(HomeViewModel.class);


        viewModel.setCountryInput("us");

        viewModel.getTopHeadlines().observe(getViewLifecycleOwner(), newsResponse -> {
            if (newsResponse != null) {
                Log.d("HomeFragment", newsResponse.toString());

                articles = newsResponse.articles;
                swipeAdapter.setArticles(articles);
            }
        });

        // Handle like unlike button clicks
        binding.homeLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.this.swipeCard(Direction.Right);
            }
        });
        binding.homeUnlikeButton.setOnClickListener(v -> swipeCard(Direction.Left));


        //    rewind button clicks

        binding.homeRewindButton.setOnClickListener(v->
        {
            layoutManager.setRewindAnimationSetting(new RewindAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new DecelerateInterpolator())
                    .build());
            binding.homeCardStackView.rewind();
//            Toast.makeText(getContext(), "Executed Rewind", Toast.LENGTH_SHORT).show();

        });
    }

    private void swipeCard(Direction direction) {
        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                .setDirection(direction)
                .setDuration(Duration.Normal.duration)
                .build();
        layoutManager.setSwipeAnimationSetting(setting);
        binding.homeCardStackView.swipe();
    }




    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Left) {
            Log.d("CardStackView", "Unliked " + layoutManager.getTopPosition());
        } else if (direction == Direction.Right) {
            Log.d("CardStackView", "Liked "  + layoutManager.getTopPosition());
        }



        if (layoutManager.getTopPosition() == swipeAdapter.getItemCount()) {
            // -------------------- last position reached, do something ---------------------
//            startActivity(new Intent(this, MainActivity.class));

//              startActivity(new Intent());


            Log.d("CardStackView", "bottom "  + layoutManager.getTopPosition());

        }



    }

    @Override
    public void onCardRewound() {

        Log.d("CardStackView", "onCardRewound: " + layoutManager.getTopPosition());


    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
}