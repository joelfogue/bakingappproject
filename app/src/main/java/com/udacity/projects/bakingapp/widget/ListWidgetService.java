package com.udacity.projects.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.projects.bakingapp.BakingApplication;
import com.udacity.projects.bakingapp.R;
import com.udacity.projects.bakingapp.data.RecipeRepository;
import com.udacity.projects.bakingapp.data.model.Ingredient;
import com.udacity.projects.bakingapp.ui.details.StepListActivity;

import java.util.List;

import javax.inject.Inject;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    public class ListRemoteViewsFactory implements
            RemoteViewsService.RemoteViewsFactory {

        private final Context mContext;
        private List<Ingredient> mIngredientList;
        private int recipeId;

        @Inject
        RecipeRepository recipeRepository;

        ListRemoteViewsFactory(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onCreate() {
            BakingApplication.getComponent(mContext).inject(this);
        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(StepListActivity.PREF, Context.MODE_PRIVATE);
            recipeId = sharedPreferences.getInt(StepListActivity.RECIPE_ID, 0);

            if (recipeId != 0) {
                mIngredientList = recipeRepository.getRecipe(recipeId).getIngredients();
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mIngredientList == null ? 0 : mIngredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(),
                    R.layout.list_item_widget);

            Ingredient recipeIngredient = mIngredientList.get(position);
            String measure = String.valueOf(recipeIngredient.getQuantity());
            String ingredient = recipeIngredient.getIngredient();
            views.setTextViewText(R.id.appwidget_ingredients, ingredient + "   " + measure);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
