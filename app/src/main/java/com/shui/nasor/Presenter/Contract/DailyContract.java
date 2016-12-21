package com.shui.nasor.Presenter.Contract;

import com.shui.nasor.Base.BasePresenter;
import com.shui.nasor.Base.BaseView;
import com.shui.nasor.Model.Bean.Zhihu.ZhihuDailyEntity;

/**
 * 作者： max_Shui on 2016/12/12.
 * 邮箱：shuicz0505@qq.com
 * ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ
 * ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ
 * ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ
 */


public interface DailyContract {
    interface View extends BaseView
    {
        void ShowContent(ZhihuDailyEntity entity);
        void TopChange(int count);
    }
    interface Presenter extends BasePresenter<View>
    {
        void getData();
        void startChange();
        void stopChange();
        void insertReadToDB(int id);
    }
}
