package com.skplanet.hackerthon;

import java.util.ArrayList;

import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.itemmanipulation.SwipeDismissAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity implements OnDismissCallback {

	private CardAdapter cardAdapter;
	private ListView mainListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mainListView = (ListView)findViewById(R.id.main_listView);
		cardAdapter = new CardAdapter(this);
		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new SwipeDismissAdapter(cardAdapter, this));
		swingBottomInAnimationAdapter.setListView(mainListView);

		mainListView.setAdapter(swingBottomInAnimationAdapter);
		cardAdapter.addAll(getItems());
	}

	// Cards Items 를 가져와서 ListView 에 적용함
	private ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			items.add(i);
		}
		return items;
	}
	
	@Override
	public void onDismiss(ListView listView, int[] reverseSortedPositions) {
		for (int position : reverseSortedPositions) {
			cardAdapter.remove(position);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
