package com.iyuba.voaEnglish.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.iyuba.headlinelibrary.HeadlineType;
import com.iyuba.headlinelibrary.ui.content.AudioContentActivityNew;
import com.iyuba.headlinelibrary.ui.content.TextContentActivity;
import com.iyuba.headlinelibrary.ui.content.VideoContentActivityNew;
import com.iyuba.headlinelibrary.ui.video.VideoMiniContentActivity;
import com.iyuba.module.favor.data.model.BasicFavorPart;
import com.iyuba.module.favor.event.FavorItemEvent;
import com.iyuba.module.favor.ui.BasicFavorFragment;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.databinding.ActivityLoginBinding;
import com.iyuba.voaEnglish.databinding.ActivityMyCollectBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import personal.iyuba.personalhomelibrary.event.ArtDataSkipEvent;

public class MyCollectActivity extends AppCompatActivity {

    ActivityMyCollectBinding binding;

    private BasicFavorFragment basicFavorFragment;
    //private SentenceCollectFragment sentenceCollectFragment;

    private PageCollectFragment pageCollectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyCollectBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        EventBus.getDefault().register(this);

        //执行收藏展示
        initFragment();

        initOperation();
    }

    private void initOperation() {


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mycollect_fl_fragment, basicFavorFragment)
                .add(R.id.mycollect_fl_fragment, pageCollectFragment)
                .show(basicFavorFragment)
                .hide(pageCollectFragment)
                .commit();

        binding.mycollectTvText.setTextColor(getResources().getColor(R.color.colorPrimary));
        binding.mycollectTvSentence.setTextColor(getResources().getColor(android.R.color.black));

        //文章
        binding.mycollectTvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.mycollectTvText.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.mycollectTvSentence.setTextColor(getResources().getColor(android.R.color.black));

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                        .show(basicFavorFragment)
                        .hide(pageCollectFragment)
                        .commit();
            }
        });
        //句子
        binding.mycollectTvSentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.mycollectTvSentence.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.mycollectTvText.setTextColor(getResources().getColor(android.R.color.black));

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                        .show(pageCollectFragment)
                        .hide(basicFavorFragment)
                        .commit();
            }
        });
        //toolbar
    }

    private void initFragment() {

        basicFavorFragment = BasicFavorFragment.newInstance();

        pageCollectFragment = PageCollectFragment.newInstance();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ArtDataSkipEvent event) {
        if (event.voa != null) {
            BasicFavorPart part = new BasicFavorPart();
            part.setType(event.voa.categoryString);
            part.setCategoryName(event.voa.categoryString);
            part.setTitle(event.voa.title);
            part.setTitleCn(event.voa.title_cn);
            part.setPic(event.voa.pic);
            part.setId(event.voa.voaid + "");
            part.setSound(event.voa.sound + "");
            jumpToCorrectFavorActivityByCate(this, part);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FavorItemEvent event) {
        BasicFavorPart item = event.items.get(event.position);
        jumpToCorrectFavorActivityByCate(this, item);
    }

    public void jumpToCorrectFavorActivityByCate(Context context, BasicFavorPart item) {
        switch (item.getType()) {
            case "news":
                startActivity(TextContentActivity.getIntent2Me(context, item.getId(), item.getTitle(), item.getTitleCn(), item.getType(),
                        item.getCategoryName(), item.getCreateTime(), item.getPic(), item.getSource()));
                break;
            case "headnews":
                startActivity(TextContentActivity.getIntent2Me(context, item.getId(), item.getTitle(), item.getTitleCn(), "news",
                        item.getCategoryName(), item.getCreateTime(), item.getPic(), item.getSource()));
                break;
            case "voa":
            case "csvoa":
            case "bbc":
            case "song":
                startActivity(AudioContentActivityNew.getIntent2Me(context, item.getCategoryName(), item.getTitle(),
                        item.getTitleCn(), item.getPic(), item.getType(), item.getId(), item.getSound()));
                break;
            case "voavideo":
            case "meiyu":
            case "ted":
            case "japanvideos":
            case "bbcwordvideo":
            case "topvideos":
                startActivity(VideoContentActivityNew.getIntent2Me(context, item.getCategoryName(), item.getTitle(),
                        item.getTitleCn(), item.getPic(), item.getType(), item.getId(), item.getSound()));
                break;
            //小视频
            case "smallvideo_jp":
            case HeadlineType.SMALLVIDEO:

                startActivity(VideoMiniContentActivity.buildIntentForOne(this, item.getId(), 0, 1, 1));
                break;
            case "series":
//                startActivity(new Intent(context, OneMvSerisesView.class)
//                        .putExtra("serisesid", item.getSeriseId())
//                        .putExtra("voaid", item.getId()));
                break;
            default:
                break;
        }
    }

}