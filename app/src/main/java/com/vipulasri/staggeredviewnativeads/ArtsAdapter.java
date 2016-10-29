package com.vipulasri.staggeredviewnativeads;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import java.util.List;

/**
 * Created by HP-HP on 29-03-2016.
 */
public class ArtsAdapter extends RecyclerView.Adapter {

    private static final int TYPE_AD = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private List<Object> mFeedList;

    public ArtsAdapter(List<Object> feedList) {
        this.mFeedList = feedList;
    }

    @Override
    public int getItemViewType(int position) {
        return (position % MainActivity.ITEMS_PER_AD == 0) ? TYPE_AD : TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        switch (viewType) {
            case TYPE_ITEM: View view = View.inflate(parent.getContext(), R.layout.item_art, null);
                            return new ArtViewHolder(view);
            case TYPE_AD:

            default: View adView = View.inflate(parent.getContext(), R.layout.item_native_ad, null);
                return new AdViewHolder(adView);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        Log.e("position","->"+position);

        switch (viewType) {
            case TYPE_ITEM:
                final ArtViewHolder artViewHolder = (ArtViewHolder) holder;

                final Artwork artwork;
                try {
                    artwork = (Artwork) mFeedList.get(position);

                    artViewHolder.mArtName.setText(artwork.getTitle());
                    ImageLoadingUtils.load(artViewHolder.mArtImage, artwork.getThumbnailImage());
                    artViewHolder.mArtImage.setAspectRatio(artwork.getAspectRatio());

                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;

            case TYPE_AD:

            default:

                final AdViewHolder adViewHolder = (AdViewHolder) holder;

                AdLoader.Builder builder = new AdLoader.Builder(context, MainActivity.ADMOB_AD_UNIT_ID);

                builder.forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
                    @Override
                    public void onAppInstallAdLoaded(NativeAppInstallAd nativeAppInstallAd) {

                        adViewHolder.mNativeAppInstallAdView.setImageView(adViewHolder.mAdImage);
                        adViewHolder.mNativeAppInstallAdView.setIconView(adViewHolder.mAdIcon);
                        adViewHolder.mNativeAppInstallAdView.setHeadlineView(adViewHolder.mAdHeadline);
                        adViewHolder.mNativeAppInstallAdView.setBodyView(adViewHolder.mAdBody);
                        adViewHolder.mNativeAppInstallAdView.setCallToActionView(adViewHolder.mAdButton);

                        ((TextView) adViewHolder.mNativeAppInstallAdView.getHeadlineView()).setText(nativeAppInstallAd.getHeadline());
                        ((TextView) adViewHolder.mNativeAppInstallAdView.getBodyView()).setText(nativeAppInstallAd.getBody());
                        ((Button) adViewHolder.mNativeAppInstallAdView.getCallToActionView()).setText(nativeAppInstallAd.getCallToAction());
                        ((ImageView) adViewHolder.mNativeAppInstallAdView.getIconView()).setImageDrawable(nativeAppInstallAd.getIcon().getDrawable());

                        List<NativeAd.Image> images = nativeAppInstallAd.getImages();

                        if (images.size() > 0) {
                            ((ImageView) adViewHolder.mNativeAppInstallAdView.getImageView()).setImageDrawable(images.get(0).getDrawable());
                        }

                        // Assign native ad object to the native view.
                        adViewHolder.mNativeAppInstallAdView.setNativeAd(nativeAppInstallAd);

                        adViewHolder.mAdParentView.removeAllViews();
                        adViewHolder.mAdParentView.addView(adViewHolder.mNativeAppInstallAdView);
                    }
                });

                adViewHolder.mNativeAppInstallAdView.setVisibility(View.INVISIBLE);

                AdLoader adLoader = builder.withAdListener(new AdListener() {

                    @Override
                    public void onAdLoaded() {
                        Log.e("Ads","loaded native ad");
                        adViewHolder.mNativeAppInstallAdView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        Toast.makeText(context, "Failed to load native ad: "+ errorCode, Toast.LENGTH_SHORT).show();
                    }
                }).build();

                adLoader.loadAd(new AdRequest.Builder().build());

        }

    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    public class ArtViewHolder extends RecyclerView.ViewHolder {

        TextView mArtName;
        SimpleDraweeView mArtImage;

        public ArtViewHolder(View itemView) {
            super(itemView);
            mArtName = (TextView) itemView.findViewById(R.id.artTextView);
            mArtImage = (SimpleDraweeView) itemView.findViewById(R.id.artImage);
        }
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {

        CardView mAdParentView;
        NativeAppInstallAdView mNativeAppInstallAdView;
        ImageView mAdImage;
        ImageView mAdIcon;
        TextView mAdHeadline;
        TextView mAdBody;
        Button mAdButton;

        public AdViewHolder(View itemView) {
            super(itemView);
            mAdParentView = (CardView) itemView.findViewById(R.id.adCardView);
            mNativeAppInstallAdView = (NativeAppInstallAdView) itemView.findViewById(R.id.nativeAppInstallAdView);
            mAdImage = (ImageView) itemView.findViewById(R.id.appinstall_image);
            mAdIcon = (ImageView) itemView.findViewById(R.id.appinstall_app_icon);
            mAdHeadline = (TextView) itemView.findViewById(R.id.appinstall_headline);
            mAdBody = (TextView) itemView.findViewById(R.id.appinstall_body);
            mAdButton = (Button) itemView.findViewById(R.id.appinstall_call_to_action);

        }

    }

}
