package com.team.zhuoke.model.logic.common;

import android.content.Context;

import com.team.zhuoke.presenter.common.interfaces.CommonPcLiveVideoContract;

import okhttp3.Request;


/**
 * 作者：gaoyin
 * 电话：18810474975
 * 邮箱：18810474975@163.com
 * 版本号：1.0
 * 类描述：
 * 备注消息：
 * 修改时间：2017/2/10 下午7:48
 **/
public class CommonPcLiveVideoModelLogic implements CommonPcLiveVideoContract.Model {

    @Override
    public Request getModelPcLiveVideoInfo(Context context, String room_id) {
        /**
         * 房间加密处理
         */
//        int time = (int)(System.currentTimeMillis() / 1000) ;
//       String app_key="a2053899224e8a92974c729dceed1cc99b3d8282";
//        String VER="2017063001";
//        String UA="Mozilla/5.0 (Macint; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
//
////        String str = "lapi/live/thirdPart/getPlay/" + room_id + "?aid=pcclient&rate=0&time=" + time + "9TUk5fjjUjg9qIMH3sdnh";
////        String auth = MD5Util.getToMd5Low32(str);
//////        L.e("地址为:"+NetWorkApi.baseUrl + NetWorkApi.getLiveVideo + room_id+"?"+tempParams.toString());
////        Request requestPost = new Request.Builder()
////                .url(NetWorkApi.oldBaseUrl+NetWorkApi.getOldLiveVideo+ room_id + "?rate=0")
////                .get()
////                .addHeader("aid","pcclient")
////                .addHeader("auth",auth)
////                .addHeader("time",time+"")
////                .build();
//        String did= MD5Util.getMD5UpperString(time+"");
//        String sign= MD5Util.getMD5String(room_id+did+app_key+time);
//        FormBody.Builder builder=new FormBody.Builder();
//        builder.add("ver",VER);
//        builder.add("sign",sign);
//        builder.add("did",did);
//        builder.add("rate","0");
//        builder.add("tt",time+"");
//        builder.add("cdn","ws");
//        RequestBody requestBody=builder.build();
////        Request requestPost = new Request.Builder()
////                .url(NetWorkApi.oldBaseUrl+NetWorkApi.getOldLiveVideo+ room_id)
//////                .url("https://www.douyu.com/lapi/live/getPlay/"+room_id)
////                .post(requestBody)
//////                .addHeader("User-Agent",UA)
//////                .addHeader("aid","pcclient")
//////                .addHeader("auth",auth)
//////                .addHeader("time",time+"")
////                .build();
//        Request requestPost = new Request.Builder()
//                .url(NetWorkApi.oldBaseUrl+NetWorkApi.getOldLiveVideo+ room_id+ "?rate=0")
////                .url("https://www.douyu.com/lapi/live/getPlay/"+room_id)
//                .get()
////                .addHeader("User-Agent",UA)
//                .addHeader("aid","pcclient")
//                .addHeader("sign",sign)
//                .addHeader("ver",VER+"")
//                .addHeader("did",did+"")
//                .addHeader("tt",time+"")
//                .addHeader("cdn","ws")
//                .build();osh

        String str="https://m.douyu.com/html5/live?roomId="+room_id;

//        String str = "lapi/live/thirdPart/getPlay/" + room_id + "?aid=pcclient&rate=0&time=" + time + "9TUk5fjjUjg9qIMH3sdnh";
//        String auth = MD5Util.getToMd5Low32(str);
//        L.e("地址为:"+NetWorkApi.baseUrl + NetWorkApi.getLiveVideo + room_id+"?"+tempParams.toString());
        Request requestPost = new Request.Builder()
//                .url(NetWorkApi.oldBaseUrl+ NetWorkApi.getOldLiveVideo+ room_id + "?rate=0")
                .url(str)
                .get()
//                .addHeader("aid","pcclient")
//                .addHeader("auth",auth)
//                .addHeader("time",time+"")
                .build();
        return requestPost;
    }
}
