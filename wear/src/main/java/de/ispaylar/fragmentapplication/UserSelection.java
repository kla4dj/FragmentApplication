package de.ispaylar.fragmentapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deniz on 23.02.15.
 */
public class UserSelection extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selection_grid);
        GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new UserFragment(this, getFragmentManager()));

        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);
    }






     class UserFragment extends FragmentGridPagerAdapter{

        private static final int TRANSITION_DURATION_MILLIS = 100;

        private final Context mContext;
        private List<Row> mRows;
        private ColorDrawable mDefaultBg;

        private ColorDrawable mClearBg;

        public UserFragment (Context ctx, FragmentManager fm) {
            super(fm);
            mContext = ctx;

            mRows = new ArrayList<Row>();

            mRows.add(new Row(cardFragment(R.string.title, R.string.user1)));
            mRows.add(new Row(cardFragment(R.string.title, R.string.user2)));
            mRows.add(new Row(cardFragment(R.string.title, R.string.user3)));
            mRows.add(new Row(cardFragment(R.string.title, R.string.user4)));
            mRows.add(new Row(cardFragment(R.string.title, R.string.user5)));

//      In case in one row several cardFragments are needed
//        mRows.add(new Row(
//                cardFragment(R.string.cards_title, R.string.cards_text),
//                cardFragment(R.string.expansion_title, R.string.expansion_text)));

            mDefaultBg = new ColorDrawable(R.color.dark_grey);
            mClearBg = new ColorDrawable(android.R.color.transparent);
        }


        LruCache<Integer, Drawable> mRowBackgrounds = new LruCache<Integer, Drawable>(3) {
            @Override
            protected Drawable create(final Integer row) {
                int resid = BG_IMAGES[row % BG_IMAGES.length];
                new DrawableLoadingTask(mContext) {
                    @Override
                    protected void onPostExecute(Drawable result) {
                        TransitionDrawable background = new TransitionDrawable(new Drawable[] {
                                mDefaultBg,
                                result
                        });
                        mRowBackgrounds.put(row, background);
                        notifyRowBackgroundChanged(row);
                        background.startTransition(TRANSITION_DURATION_MILLIS);
                    }
                }.execute(resid);
                return mDefaultBg;
            }
        };

     class MyCardFragment extends CardFragment{
            private View fragmentView;
            private View.OnClickListener listener;

            @Override
            protected View onCreateContentView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
                fragmentView = super.onCreateContentView(inflater, container, savedInstanceState);
                fragmentView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View view) {
                        if (listener != null) {
                            listener.onClick(view);
                        }
                    }
                });
                return fragmentView;
            }
            public void setOnClickListener(final View.OnClickListener listener) {
                this.listener = listener;
            }

        }

        private Fragment cardFragment(int titleRes, int textRes) {
            Resources res = mContext.getResources();
            final MyCardFragment fragment = (MyCardFragment) MyCardFragment.create(res.getText(titleRes), res.getText(textRes));
            fragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Intent i = new Intent(UserSelection.this, MainActivity.class);
//                i.putExtra("OBJECT_NAME", someObject);

                    startActivity(i);
                }
            });
            return fragment;

        }

//      im orginal an der Stelle ein static cast
         final int[] BG_IMAGES = new int[] {
                R.drawable.station1,
                R.drawable.station2,
                R.drawable.station3,
                R.drawable.station4,
                R.drawable.station5
        };


        /** A convenient container for a row of fragments. */
        private class Row {
            final List<Fragment> columns = new ArrayList<Fragment>();

            public Row(Fragment... fragments) {
                for (Fragment f : fragments) {
                    add(f);
                }
            }

            public void add(Fragment f) {
                columns.add(f);
            }

            Fragment getColumn(int i) {
                return columns.get(i);
            }

            public int getColumnCount() {
                return columns.size();
            }
        }


        @Override
        public Fragment getFragment(int row, int col) {
            Row adapterRow = mRows.get(row);
            return adapterRow.getColumn(col);
        }

        @Override
        public Drawable getBackgroundForRow(final int row) {
            return mRowBackgrounds.get(row);
        }


        @Override
        public int getRowCount() {
            return mRows.size();
        }

        @Override
        public int getColumnCount(int rowNum) {
            return mRows.get(rowNum).getColumnCount();
        }

        class DrawableLoadingTask extends AsyncTask<Integer, Void, Drawable> {
            private static final String TAG = "Loader";
            private Context context;

            DrawableLoadingTask(Context context) {
                this.context = context;
            }

            @Override
            protected Drawable doInBackground(Integer... params) {
                Log.d(TAG, "Loading asset 0x" + Integer.toHexString(params[0]));
                return context.getResources().getDrawable(params[0]);
            }
        }
    }

}
