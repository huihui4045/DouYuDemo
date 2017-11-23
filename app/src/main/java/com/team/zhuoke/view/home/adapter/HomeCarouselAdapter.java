package com.team.zhuoke.view.home.adapter;

import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 作者：${User}
 * 电话：18810474975
 * 邮箱：18810474975@163.com
 * 版本号：
 * 类描述：
 * 修改时间：${DATA}1702
 */
public class HomeCarouselAdapter implements BGABanner.Adapter<SimpleDraweeView, String> {
    @Override
    public void fillBannerItem(BGABanner banner, SimpleDraweeView itemView, String model, int position) {
        itemView.setImageURI(Uri.parse(model));
//        itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (itemView.getWidth() != 0 && itemView.getHeight() != 0) {
//                    FrescoUtils.showThumb(itemView, model, itemView.getWidth(), itemView.getHeight());
//                }
//            }
//        });
    }
}
