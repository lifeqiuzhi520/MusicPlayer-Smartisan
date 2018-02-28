package com.yibao.music.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yibao.music.MyApplication;
import com.yibao.music.base.listener.OnMusicItemClickListener;
import com.yibao.music.model.AlbumInfo;
import com.yibao.music.model.ArtistInfo;
import com.yibao.music.model.MusicBean;
import com.yibao.music.model.greendao.MusicBeanDao;
import com.yibao.music.model.greendao.MusicInfoDao;
import com.yibao.music.util.MusicListUtil;
import com.yibao.music.util.RandomUtil;
import com.yibao.music.util.RxBus;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Stran
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.base
 * @文件名: BaseFragment
 * @创建时间: 2018/1/1 17:36
 * @描述： TODO
 */
public abstract class BaseFragment extends Fragment {
    protected String tag;
    protected Activity mActivity;
    protected static ArrayList<MusicBean> musicBeans;
    protected static ArrayList<AlbumInfo> mAlbumList;
    protected static ArrayList<ArtistInfo> mArtistList;
    protected static RxBus mBus;
    protected static CompositeDisposable compositeDisposable;
    public ArrayList<MusicBean> mSongList;
    public MusicBeanDao mMusicBeanDao;
    public MusicInfoDao mMusicInfoDao;
    protected boolean isShowDetailsView = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tag = this.getClass().getSimpleName();
        mMusicBeanDao = MyApplication.getIntstance().getMusicDao();
        mMusicInfoDao = MyApplication.getIntstance().getMusicInfoDao();
        mActivity = getActivity();
        compositeDisposable = new CompositeDisposable();
        mBus = MyApplication.getIntstance().bus();


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mSongList == null) {
            mSongList = (ArrayList<MusicBean>) mMusicBeanDao.queryBuilder().list();


        }
        if (musicBeans == null) {
            musicBeans = (ArrayList<MusicBean>) mMusicBeanDao.queryBuilder().list();
        }
        if (mAlbumList == null) {
            mAlbumList = MusicListUtil.getAlbumList((ArrayList<MusicBean>) mMusicBeanDao.queryBuilder().list());
        }
        if (mArtistList == null) {
            mArtistList = MusicListUtil.getArtistList((ArrayList<MusicBean>) mMusicBeanDao.queryBuilder().list());

        }

    }

    protected void randomPlayMusic() {
        int position = RandomUtil.getRandomPostion(mSongList);
        if (getActivity() instanceof OnMusicItemClickListener) {
            ((OnMusicItemClickListener) getActivity()).startMusicService(position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 返回子类的一个标记
     *
     * @return
     */
    protected abstract int getFlag();

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }


}
