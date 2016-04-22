package com.graemegrier.gametests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that houses the {@link GameView}.
 */
public class GameViewFragment extends Fragment {

    private GameView gameView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);
        gameView = (GameView) v.findViewById(R.id.game_view);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        gameView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        gameView.onPause();
    }
}
