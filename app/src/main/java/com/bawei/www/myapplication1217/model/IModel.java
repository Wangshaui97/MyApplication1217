package com.bawei.www.myapplication1217.model;


import com.bawei.www.myapplication1217.bean.GoodsBean;
import com.bawei.www.myapplication1217.mycallback.MyCallback;
import com.bawei.www.myapplication1217.okhttputil.Httputil;
import com.bawei.www.myapplication1217.okhttputil.ICallBack;

public class IModel implements IM {

    @Override
    public void setRequest(String url, final MyCallback myCallback) {
        Httputil.getInstance().getEnqueue(url, GoodsBean.class, new ICallBack() {
            @Override
            public void success(Object obj) {
                myCallback.setData(obj);
            }

            @Override
            public void failed(Exception e) {

            }
        });
    }


}
