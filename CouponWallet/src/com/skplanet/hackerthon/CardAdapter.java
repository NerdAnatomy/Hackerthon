package com.skplanet.hackerthon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haarman.listviewanimations.ArrayAdapter;

public class CardAdapter extends ArrayAdapter<Integer> {

	private Context mContext;
	private LruCache<Integer, Bitmap> mMemoryCache;
	
	public CardAdapter(Context context) {
		
		mContext = context;

		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory;
		mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(Integer key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
			}
		};
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.card_layout, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) view.findViewById(R.id.card_textView);
			viewHolder.imageView = (ImageView) view.findViewById(R.id.card_imageView);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.textView.setText("This is card " + (getItem(position) + 1));
		setImageView(viewHolder, position);

		return view;
	}
	
	private void setImageView(ViewHolder viewHolder, int position) {
		int imageResId;
		switch (getItem(position) % 4) {
			case 0:
				imageResId = R.drawable.card_hp;
				break;
			case 1:
				imageResId = R.drawable.card_illy;
				break;
			case 2:
				imageResId = R.drawable.card_starbucks;
				break;
			default:
				imageResId = R.drawable.card_whitecoffee;
		}

		Bitmap bitmap = getBitmapFromMemCache(imageResId);
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(mContext.getResources(), imageResId);
			addBitmapToMemoryCache(imageResId, bitmap);
		}
		viewHolder.imageView.setImageBitmap(bitmap);
	}

	private void addBitmapToMemoryCache(int key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	private Bitmap getBitmapFromMemCache(int key) {
		return mMemoryCache.get(key);
	}

	private static class ViewHolder {
		TextView textView;
		ImageView imageView;
	}

	

}
