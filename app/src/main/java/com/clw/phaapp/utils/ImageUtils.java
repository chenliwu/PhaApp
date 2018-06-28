package com.clw.phaapp.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片工具类
 * 
 * @version 1.0.0
 * 
 * @date 2017-7-26 下午12:07:49
 *
 * @author chenliwu
 */
public class ImageUtils {


	/**
	 * 根据InputStream获取图片实际的宽度和高度
	 *
	 * @param imageStream
	 * @return
	 */
	public static ImageSize getImageSize(InputStream imageStream)
	{
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(imageStream, null, options);
		return new ImageSize(options.outWidth, options.outHeight);
	}

	public static class ImageSize
	{
		int width;
		int height;

		public ImageSize()
		{
		}

		public ImageSize(int width, int height)
		{
			this.width = width;
			this.height = height;
		}

		@Override
		public String toString()
		{
			return "ImageSize{" +
					"width=" + width +
					", height=" + height +
					'}';
		}
	}

	public static int calculateInSampleSize(ImageSize srcSize, ImageSize targetSize)
	{
		// 源图片的宽度
		int width = srcSize.width;
		int height = srcSize.height;
		int inSampleSize = 1;

		int reqWidth = targetSize.width;
		int reqHeight = targetSize.height;

		if (width > reqWidth && height > reqHeight)
		{
			// 计算出实际宽度和目标宽度的比率
			int widthRatio = Math.round((float) width / (float) reqWidth);
			int heightRatio = Math.round((float) height / (float) reqHeight);
			inSampleSize = Math.max(widthRatio, heightRatio);
		}
		return inSampleSize;
	}

	/**
	 * 根据ImageView获适当的压缩的宽和高
	 *
	 * @param view
	 * @return
	 */
	public static ImageSize getImageViewSize(View view)
	{

		ImageSize imageSize = new ImageSize();

		imageSize.width = getExpectWidth(view);
		imageSize.height = getExpectHeight(view);

		return imageSize;
	}

	/**
	 * 根据view获得期望的高度
	 *
	 * @param view
	 * @return
	 */
	private static int getExpectHeight(View view)
	{

		int height = 0;
		if (view == null) return 0;

		final ViewGroup.LayoutParams params = view.getLayoutParams();
		//如果是WRAP_CONTENT，此时图片还没加载，getWidth根本无效
		if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT)
		{
			height = view.getWidth(); // 获得实际的宽度
		}
		if (height <= 0 && params != null)
		{
			height = params.height; // 获得布局文件中的声明的宽度
		}

		if (height <= 0)
		{
			height = getImageViewFieldValue(view, "mMaxHeight");// 获得设置的最大的宽度
		}

		//如果宽度还是没有获取到，憋大招，使用屏幕的宽度
		if (height <= 0)
		{
			DisplayMetrics displayMetrics = view.getContext().getResources()
					.getDisplayMetrics();
			height = displayMetrics.heightPixels;
		}

		return height;
	}

	/**
	 * 根据view获得期望的宽度
	 *
	 * @param view
	 * @return
	 */
	private static int getExpectWidth(View view)
	{
		int width = 0;
		if (view == null) return 0;

		final ViewGroup.LayoutParams params = view.getLayoutParams();
		//如果是WRAP_CONTENT，此时图片还没加载，getWidth根本无效
		if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT)
		{
			width = view.getWidth(); // 获得实际的宽度
		}
		if (width <= 0 && params != null)
		{
			width = params.width; // 获得布局文件中的声明的宽度
		}

		if (width <= 0)

		{
			width = getImageViewFieldValue(view, "mMaxWidth");// 获得设置的最大的宽度
		}
		//如果宽度还是没有获取到，憋大招，使用屏幕的宽度
		if (width <= 0)

		{
			DisplayMetrics displayMetrics = view.getContext().getResources()
					.getDisplayMetrics();
			width = displayMetrics.widthPixels;
		}

		return width;
	}

	/**
	 * 通过反射获取imageview的某个属性值
	 *
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static int getImageViewFieldValue(Object object, String fieldName)
	{
		int value = 0;
		try
		{
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			int fieldValue = field.getInt(object);
			if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE)
			{
				value = fieldValue;
			}
		} catch (Exception e)
		{
		}
		return value;

	}











	
	//private static final String TAG = "LoadImageUtil";
	
	private static ImageUtils instance;
	
	/**
	 * 图片缓存
	 */
	private static HashMap<String, SoftReference<Bitmap>> imgCaches;
	
	private static ExecutorService executorThreadPool = Executors
			.newFixedThreadPool(1);
	
	static {
		instance = new ImageUtils();
		imgCaches = new HashMap<String, SoftReference<Bitmap>>();
	}

	/**
	 * 获取图片工具类实例
	 * @return
	 */
	public static ImageUtils getInstance() {
		if (instance != null) {
			return instance;
		}
		return null;
	}

	/**
	 * 加载图片
	 * @param path
	 * @param listener
	 */
	@SuppressLint("HandlerLeak")
	public void loadBitmap(final String path,
			final OnLoadBitmapListener listener) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bitmap bitmap = (Bitmap) msg.obj;
				listener.loadImage(bitmap, path);
			}
		};
		new Thread() {

			@Override
			public void run() {
				executorThreadPool.execute(new Runnable() {
					@Override
					public void run() {
						Bitmap bitmap = loadBitmapFromCache(path);
						if (bitmap != null) {
							Message msg = handler.obtainMessage();
							msg.obj = bitmap;
							handler.sendMessage(msg);
						}

					}
				});
			}

		}.start();
	}

	/**
	 * 从指定缓存路径加载图片
	 * @param path
	 * @return
	 */
	private Bitmap loadBitmapFromCache(String path) {
		if (imgCaches == null) {
			imgCaches = new HashMap<String, SoftReference<Bitmap>>();
		}
		Bitmap bitmap = null;
		if (imgCaches.containsKey(path)) {
			bitmap = imgCaches.get(path).get();
		}
		if (bitmap == null) {
			bitmap = loadBitmapFromLocal(path);
		}
		return bitmap;
	}

	/**
	 * 从本地路径加载图片
	 * @param path
	 * @return
	 */
	private Bitmap loadBitmapFromLocal(String path) {
		if (path == null) {
			return null;
		}
		Options options = new Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		float height = 800f;
		float width = 480f;
		float scale = 1;
		if (options.outWidth > width && options.outWidth > options.outHeight) {
			scale = options.outWidth / width;
		} else if (options.outHeight > height
				&& options.outHeight > options.outWidth) {
			scale = options.outHeight / height;
		} else {
			scale = 1;
		}
		options.inSampleSize = (int) scale;
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(path, options);
		bitmap = decodeBitmap(bitmap);
		if (!imgCaches.containsKey(path)) {
			addCache(path, bitmap);
		}
		return bitmap;
	}

	/**
	 * 
	 * @param bitmap
	 * @return
	 */
	private Bitmap decodeBitmap(Bitmap bitmap) {
		int scale = 100;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, scale, bos);
		while ((bos.toByteArray().length / 1024) > 30) {
			bos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, scale, bos);
			scale -= 10;
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		bitmap = BitmapFactory.decodeStream(bis);
		return bitmap;
	}
	
	/**
	 * 添加图片缓存
	 * @param path
	 * @param bitmap
	 */
	public void addCache(String path,Bitmap bitmap){
		imgCaches.put(path, new SoftReference<Bitmap>(bitmap));
	}
	
	/**
	 * 移除图片缓存
	 * @param path
	 */
	public void removeCache(String path){
		imgCaches.remove(path);
	}

	/**
	 * 加载图片接口
	 * 
	 * @version 1.0.0
	 * 
	 * @date 2017-7-26 下午12:07:04
	 *
	 * @author chenliwu
	 */
	public interface OnLoadBitmapListener {
		void loadImage(Bitmap bitmap, String path);
	}
}
