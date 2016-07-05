import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.lask.scientifichabits.Utils.Identicon;

import java.lang.ref.WeakReference;

/**
 * Created by Kellen on 6/12/2016.
 */
public class IdenticonGeneratorAsync extends AsyncTask<Object, Void, Bitmap> {
	private WeakReference<ImageView> imageView;

	public IdenticonGeneratorAsync(ImageView view) {
		imageView = new WeakReference<ImageView>(view);
	}

	@Override
	protected Bitmap doInBackground(Object... params) {
		return Identicon.generate(params[0], 6, 256, Color.parseColor("#FFFFFF"));
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if(bitmap != null && imageView != null) {
			final ImageView view = imageView.get();
			if(view != null) {
				view.setImageBitmap(bitmap);
			}
		}
	}
}
