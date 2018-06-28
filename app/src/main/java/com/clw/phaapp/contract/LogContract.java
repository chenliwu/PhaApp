package com.clw.phaapp.contract;

import com.clw.phaapp.base.IBaseView;
import com.clw.phaapp.model.entity.LogEntity;

/**
 * 日志业务契约
 */
public interface LogContract {

    //////////////////  用户反馈   ////////////////////////
    /**
     * 获取浏览历史View层接口
     */
    interface IGetVisitHistoryView extends IBaseView {

        /**
         * 成功回调接口
         */
        void getVisitHistorySuccess(LogEntity logEntity);

        /**
         * 失败回调接口
         */
        void getVisitHistoryFail();
    }

    /**
     *  获取浏览历史Presenter层接口
     */
    interface IGetVisitHistoryPresenter{
        /**
         *
         * @param logEntity
         */
        void getVisitHistory(LogEntity logEntity);
    }
    /////////////////////////////////////////////////

}
