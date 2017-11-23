package com.team.zhuoke.view.common.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.zhuoke.R;
import com.team.zhuoke.ui.CustomRoundView;
import com.team.zhuoke.ui.HorizontalListView;
import com.team.zhuoke.ui.MagicTextView;
import com.team.zhuoke.utils.DisplayUtil;
import com.team.zhuoke.utils.SoftKeyBoardListener;
import com.team.zhuoke.view.common.adapter.AudienceAdapter;
import com.team.zhuoke.view.common.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LayerFragment extends Fragment implements View.OnClickListener {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    };
    /**
     * 标示判断
     */
    private boolean isOpen;
    private long liveTime;

    /**
     * 界面相关
     */
    private RelativeLayout llpicimage;
    private RelativeLayout rlsentimenttime;
    private HorizontalListView hlvaudience;
    private TextView tvtime;
    private TextView tvdate;
    private LinearLayout llgiftcontent;
    private ListView lvmessage;
    private TextView tvSendone;
    private TextView tvSendtwo;
    private TextView tvSendthree;
    private TextView tvSendfor;
    private EditText etInput;
    private TextView tvChat;
    private TextView sendInput;
    private LinearLayout llInputParent;
    protected Unbinder unbinder;
    private ImageView img_close;

    /**
     * 动画相关
     */
    private NumAnim giftNumAnim;
    private TranslateAnimation inAnim;
    private TranslateAnimation outAnim;
    private AnimatorSet animatorSetHide = new AnimatorSet();
    private AnimatorSet animatorSetShow = new AnimatorSet();

    /**
     * 数据相关
     */
    private List<View> giftViewCollection = new ArrayList<View>();
    private List<String> messageData = new LinkedList<>();
    private MessageAdapter messageAdapter;

    private Timer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layer, container, false);
        //            注解绑定
        unbinder = ButterKnife.bind(getActivity());
        llpicimage = (RelativeLayout) view.findViewById(R.id.llpicimage);
        rlsentimenttime = (RelativeLayout) view.findViewById(R.id.rlsentimenttime);
        hlvaudience = (HorizontalListView) view.findViewById(R.id.hlvaudience);
        tvtime = (TextView) view.findViewById(R.id.tvtime);
        tvdate = (TextView) view.findViewById(R.id.tvdate);
        llgiftcontent = (LinearLayout) view.findViewById(R.id.llgiftcontent);
        lvmessage = (ListView) view.findViewById(R.id.lvmessage);
        tvChat = (TextView) view.findViewById(R.id.tvChat);
        tvSendone = (TextView) view.findViewById(R.id.tvSendone);
        tvSendtwo = (TextView) view.findViewById(R.id.tvSendtwo);
        tvSendthree = (TextView) view.findViewById(R.id.tvSendthree);
        tvSendfor = (TextView) view.findViewById(R.id.tvSendfor);
        llInputParent = (LinearLayout) view.findViewById(R.id.llinputparent);
        etInput = (EditText) view.findViewById(R.id.etInput);
        sendInput = (TextView) view.findViewById(R.id.sendInput);
        img_close = (ImageView) view.findViewById(R.id.img_close);
        img_close.setOnClickListener(this);
        tvChat.setOnClickListener(this);
        tvSendone.setOnClickListener(this);
        tvSendtwo.setOnClickListener(this);
        tvSendthree.setOnClickListener(this);
        tvSendfor.setOnClickListener(this);
        sendInput.setOnClickListener(this);
        inAnim = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.gift_in);
        outAnim = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.gift_out);
        giftNumAnim = new NumAnim();
        clearTiming();
        sendDanMu();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llInputParent.getVisibility() == View.VISIBLE) {
                    tvChat.setVisibility(View.VISIBLE);
                    llInputParent.setVisibility(View.GONE);
                    hideKeyboard();
                }
            }
        });
        softKeyboardListnenr();
        messageData.add("官方消息：欢迎来到美少女的直播间,喜欢的就点关注吧。斗鱼提倡健康的直播环境，对直播内容24小时巡查。任何传播违法，违规，低俗等不良信息时将被封号");
//        for (int x = 0; x < 20; x++) {
//            messageData.add("超人不哭说: " + x);
//        }
        messageAdapter = new MessageAdapter(getActivity(), messageData);
        lvmessage.setAdapter(messageAdapter);
        lvmessage.setSelection(messageData.size());
        hlvaudience.setAdapter(new AudienceAdapter(getActivity()));
        startTimer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvChat:/*聊天*/
                showChat();
                break;
            case R.id.sendInput:/*发送*/
                sendText();
                break;
            case R.id.tvSendone:/*礼物1*/
                showGift("超人不哭");
                break;
            case R.id.tvSendtwo:/*礼物2*/
                showGift("杯里鱼");
                break;
            case R.id.tvSendthree:/*礼物3*/
                showGift("超人不会飞");
                break;
            case R.id.tvSendfor:/*礼物4*/
                showGift("花花");
                break;
            case R.id.img_close:
                getActivity().finish();
                break;
        }
    }


    /**
     * 添加礼物view,(考虑垃圾回收)
     */
    private View addGiftView() {
        View view = null;
        if (giftViewCollection.size() <= 0) {
            /*如果垃圾回收中没有view,则生成一个*/
            view = LayoutInflater.from(getActivity()).inflate(R.layout.item_gift, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = 10;
            view.setLayoutParams(lp);
            llgiftcontent.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {
                }

                @Override
                public void onViewDetachedFromWindow(View view) {
                    giftViewCollection.add(view);
                }
            });
        } else {
            view = giftViewCollection.get(0);
            giftViewCollection.remove(view);
        }
        return view;
    }

    /**
     * 删除礼物view
     */
    private void removeGiftView(final int index) {
        final View removeView = llgiftcontent.getChildAt(index);
        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llgiftcontent.removeViewAt(index);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removeView.startAnimation(outAnim);
            }
        });
    }

    /**
     * 获取弹幕  信息
     */
    public void sendDanMu() {
        final ArrayList<String> mArrayList = new ArrayList<String>();
        mArrayList.add("主播真美！");
        mArrayList.add("主播MM发育很好呀！🐂");
        mArrayList.add("主播多大咯！");
        mArrayList.add("主播哪里人！");
        mArrayList.add("笑起来真甜！❤️❤️❤️");
        mArrayList.add("主播好幽默！🐱");
        mArrayList.add("声音真甜！🐶");
        mArrayList.add("美女主播 hello 👌");
        mArrayList.add("6666666666");
        mArrayList.add("色情主播，我报警啦！🚓🚓🚓");
        mArrayList.add("主播，求BGM🎵🎵🎵");
        mArrayList.add("主播不要逗");
        mArrayList.add("秀一波6666 ");
        mArrayList.add("主播别脱咯 🐦");
        mArrayList.add("受不了啦，高潮咯！");
        mArrayList.add("裤子全湿了  😝😝😝");
        final Random random = new Random(mArrayList.size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (messageData != null && messageAdapter != null) {
                                    messageData.add("超人不哭说: " + mArrayList.get(random.nextInt(mArrayList.size())));
                                    messageAdapter.NotifyAdapter(messageData);
                                    lvmessage.setSelection(messageData.size());
                                }
                            }
                        });
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

    }


    /**
     * 显示礼物的方法
     */
    private void showGift(String tag) {
        View giftView = llgiftcontent.findViewWithTag(tag);
        if (giftView == null) {/*该用户不在礼物显示列表*/

            if (llgiftcontent.getChildCount() > 2) {/*如果正在显示的礼物的个数超过两个，那么就移除最后一次更新时间比较长的*/
                View giftView1 = llgiftcontent.getChildAt(0);
                CustomRoundView picTv1 = (CustomRoundView) giftView1.findViewById(R.id.crvheadimage);
                long lastTime1 = (Long) picTv1.getTag();
                View giftView2 = llgiftcontent.getChildAt(1);
                CustomRoundView picTv2 = (CustomRoundView) giftView2.findViewById(R.id.crvheadimage);
                long lastTime2 = (Long) picTv2.getTag();
                if (lastTime1 > lastTime2) {/*如果第二个View显示的时间比较长*/
                    removeGiftView(1);
                } else {/*如果第一个View显示的时间长*/
                    removeGiftView(0);
                }
            }
            giftView = addGiftView();/*获取礼物的View的布局*/
            giftView.setTag(tag);/*设置view标识*/

            CustomRoundView crvheadimage = (CustomRoundView) giftView.findViewById(R.id.crvheadimage);
            final MagicTextView giftNum = (MagicTextView) giftView.findViewById(R.id.giftNum);/*找到数量控件*/
            giftNum.setText("x1");/*设置礼物数量*/
            crvheadimage.setTag(System.currentTimeMillis());/*设置时间标记*/
            giftNum.setTag(1);/*给数量控件设置标记*/

            llgiftcontent.addView(giftView);/*将礼物的View添加到礼物的ViewGroup中*/
            llgiftcontent.invalidate();/*刷新该view*/
            giftView.startAnimation(inAnim);/*开始执行显示礼物的动画*/
            inAnim.setAnimationListener(new Animation.AnimationListener() {/*显示动画的监听*/
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    giftNumAnim.start(giftNum);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        } else {/*该用户在礼物显示列表*/
            CustomRoundView crvheadimage = (CustomRoundView) giftView.findViewById(R.id.crvheadimage);/*找到头像控件*/
            MagicTextView giftNum = (MagicTextView) giftView.findViewById(R.id.giftNum);/*找到数量控件*/
            int showNum = (Integer) giftNum.getTag() + 1;
            giftNum.setText("x" + showNum);
            giftNum.setTag(showNum);
            crvheadimage.setTag(System.currentTimeMillis());
            giftNumAnim.start(giftNum);
        }
    }

    /**
     * 显示聊天布局
     */
    private void showChat() {
//        etInput.requestFocus();
        tvChat.setVisibility(View.GONE);
        llInputParent.setVisibility(View.VISIBLE);
        etInput.setFocusable(true);
        etInput.setFocusableInTouchMode(true);
        etInput.requestFocus();
//        llInputParent.requestFocus();
        showKeyboard();
    }

    /**
     * 发送消息
     */
    private void sendText() {
        if (!etInput.getText().toString().trim().isEmpty()) {
            messageData.add("超人不哭说: " + etInput.getText().toString().trim());
            etInput.setText("");
            messageAdapter.NotifyAdapter(messageData);
            lvmessage.setSelection(messageData.size());
            hideKeyboard();
        } else
            hideKeyboard();
    }

    /**
     * 开始计时功能
     */
    private void startTimer() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.add(Calendar.HOUR_OF_DAY, -8);
        Date time = calendar.getTime();
        liveTime = time.getTime();
        handler.post(timerRunnable);
    }

    /**
     * 显示软键盘并因此头布局
     */
    private void showKeyboard() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etInput, InputMethodManager.SHOW_FORCED);
            }
        }, 100);
    }

    /**
     * 隐藏软键盘并显示头布局
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
    }

    /**
     * 软键盘显示与隐藏的监听
     */
    private void softKeyboardListnenr() {
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {/*软键盘显示：执行隐藏title动画，并修改listview高度和装载礼物容器的高度*/
                animateToHide();
                dynamicChangeListviewH(100);
                dynamicChangeGiftParentH(true);
            }

            @Override
            public void keyBoardHide(int height) {/*软键盘隐藏：隐藏聊天输入框并显示聊天按钮，执行显示title动画，并修改listview高度和装载礼物容器的高度*/
                tvChat.setVisibility(View.VISIBLE);
                llInputParent.setVisibility(View.GONE);
                animateToShow();
                dynamicChangeListviewH(150);
                dynamicChangeGiftParentH(false);
            }
        });
    }

    /**
     * 动态的修改listview的高度
     *
     * @param heightPX
     */
    private void dynamicChangeListviewH(int heightPX) {
        ViewGroup.LayoutParams layoutParams = lvmessage.getLayoutParams();
        layoutParams.height = DisplayUtil.dip2px(getActivity(), heightPX);
        lvmessage.setLayoutParams(layoutParams);
    }

    /**
     * 动态修改礼物父布局的高度
     *
     * @param showhide
     */
    private void dynamicChangeGiftParentH(boolean showhide) {
        if (showhide) {/*如果软键盘显示中*/
            if (llgiftcontent.getChildCount() != 0) {
                /*判断是否有礼物显示，如果有就修改父布局高度，如果没有就不作任何操作*/
                ViewGroup.LayoutParams layoutParams = llgiftcontent.getLayoutParams();
                layoutParams.height = llgiftcontent.getChildAt(0).getHeight();
                llgiftcontent.setLayoutParams(layoutParams);
            }
        } else {/*如果软键盘隐藏中*/
            /*就将装载礼物的容器的高度设置为包裹内容*/
            ViewGroup.LayoutParams layoutParams = llgiftcontent.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            llgiftcontent.setLayoutParams(layoutParams);
        }
    }

    /**
     * 头部布局执行显示的动画
     */
    private void animateToShow() {
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(rlsentimenttime, "translationX", -rlsentimenttime.getWidth(), 0);
        ObjectAnimator topAnim = ObjectAnimator.ofFloat(llpicimage, "translationY", -llpicimage.getHeight(), 0);
        animatorSetShow.playTogether(leftAnim, topAnim);
        animatorSetShow.setDuration(300);
        animatorSetShow.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOpen = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isOpen = true;
            }
        });
        if (!isOpen) {
            animatorSetShow.start();
        }
    }

    /**
     * 头部布局执行退出的动画
     */
    private void animateToHide() {
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(rlsentimenttime, "translationX", 0, -rlsentimenttime.getWidth());
        ObjectAnimator topAnim = ObjectAnimator.ofFloat(llpicimage, "translationY", 0, -llpicimage.getHeight());
        animatorSetHide.playTogether(leftAnim, topAnim);
        animatorSetHide.setDuration(300);
        animatorSetHide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOpen = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isOpen = true;
            }
        });
        if (!isOpen) {
            animatorSetHide.start();
        }
    }

    /**
     * 循环执行线程
     */
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(timerRunnable, 1000);
            long sysTime = System.currentTimeMillis();
            liveTime += 1000;
            CharSequence sysTimeStr = DateFormat.format("HH:mm:ss", liveTime);
            CharSequence sysDateStr = DateFormat.format("yyyy/MM/dd", sysTime);
            tvtime.setText(sysTimeStr);
            tvdate.setText(sysDateStr);
        }
    };

    /**
     * 定时清除礼物
     */
    private void clearTiming() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int count = llgiftcontent.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = llgiftcontent.getChildAt(i);
                    CustomRoundView crvheadimage = (CustomRoundView) view.findViewById(R.id.crvheadimage);
                    long nowtime = System.currentTimeMillis();
                    long upTime = (Long) crvheadimage.getTag();
                    if ((nowtime - upTime) >= 3000) {
                        removeGiftView(i);
                        return;
                    }
                }
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 3000);
    }

    /**
     * 数字放大动画
     */
    public class NumAnim {
        private Animator lastAnimator = null;

        public void start(View view) {
            if (lastAnimator != null) {
                lastAnimator.removeAllListeners();
                lastAnimator.end();
                lastAnimator.cancel();
            }
            ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX", 1.3f, 1.0f);
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY", 1.3f, 1.0f);
            AnimatorSet animSet = new AnimatorSet();
            lastAnimator = animSet;
            animSet.setDuration(200);
            animSet.setInterpolator(new OvershootInterpolator());
            animSet.playTogether(anim1, anim2);
            animSet.start();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (etInput != null) {
            etInput = null;
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        timer.cancel();
    }
}