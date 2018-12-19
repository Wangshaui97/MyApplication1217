package com.bawei.www.myapplication1217.presonter;

import com.bawei.www.myapplication1217.model.IModel;
import com.bawei.www.myapplication1217.mycallback.MyCallback;
import com.bawei.www.myapplication1217.view.IView;

public class IPresonter implements IP {
    private IView iView;
    private IModel iModel;

    public IPresonter(IView iView) {
        this.iView = iView;
        iModel = new IModel();
    }

    @Override
    public void setResponse(String url) {
        iModel.setRequest(url, new MyCallback() {
            @Override
            public void setData(Object data) {
                iView.setSuccess(data);
            }
        });
    }


    //内存泄露
    public void onDistuch() {
        if (iModel != null) {
            iModel = null;
        }
        if (iView != null) {
            iView = null;
        }
    }
}
