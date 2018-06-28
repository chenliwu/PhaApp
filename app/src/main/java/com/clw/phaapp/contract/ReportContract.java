package com.clw.phaapp.contract;

import com.clw.phaapp.base.IBaseView;
import com.clw.phaapp.model.entity.ReportEntity;

public interface ReportContract {

    ///////////////////////  举报  ////////////////////////////////

    interface IReportView extends IBaseView {

        /**
         * 举报成功
         * @param msg
         */
        void reportSuccess(String msg);

        /**
         * 举报失败
         * @param msg
         */
        void reportFail(String msg);

    }

    interface  IReportPresenterr{
        /**
         * 举报
         * @param reportEntity
         */
        void report(ReportEntity reportEntity);
    }



    ///////////////////////////////////////////////////////////////////

}
