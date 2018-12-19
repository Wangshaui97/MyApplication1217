package com.bawei.www.myapplication1217.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bawei.www.myapplication1217.Apis;
import com.bawei.www.myapplication1217.R;
import com.bawei.www.myapplication1217.adpter.ShopCarAdpter;
import com.bawei.www.myapplication1217.bean.GoodsBean;
import com.bawei.www.myapplication1217.presonter.IPresonter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {

    private IPresonter iPresonter;
    private RecyclerView recyclerView;
    private CheckBox btn_check;
    private TextView zprice;
    private ShopCarAdpter sd;
    private List<GoodsBean.DataBean> databeans;
    private Button jiesuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iPresonter = new IPresonter(this);

        initView();
        initeData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresonter.onDistuch();
    }

    private void initeData() {
        iPresonter.setResponse(Apis.URL);
    }

    private void initView() {
        recyclerView = findViewById(R.id.xlistview);
        btn_check = findViewById(R.id.btn_check);
        zprice = findViewById(R.id.zprice);
        jiesuan = findViewById(R.id.btn_jieusuan);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        sd = new ShopCarAdpter(this);
        recyclerView.setAdapter(sd);


        sd.setListener(new ShopCarAdpter.ShopCallBackListener() {
            @Override
            public void setcallback(int i) {

            }
            @Override
            public void callBack(List<GoodsBean.DataBean> list) {

                double totalPrice = 0;
                int num = 0;
                int number = 0;

                for (int a = 0; a < list.size(); a++) {
                    //获取商家里商品
                    List<GoodsBean.DataBean.ListBean> listAll = list.get(a).getList();
                    for (int i = 0; i < listAll.size(); i++) {
                        number = number + listAll.get(i).getNum();
                        //取选中的状态
                        if (listAll.get(i).isCheck()) {
                            totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                            num = num + listAll.get(i).getNum();
                        }
                    }
                }

                if (num < number) {
                    //不是全部选中
                    btn_check.setChecked(false);
                } else {
                    //是全部选中
                    btn_check.setChecked(true);
                }

                zprice.setText("合计："+totalPrice);
                jiesuan.setText("结算（"+num+")");
            }


        });
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSeller(btn_check.isChecked());
                sd.notifyDataSetChanged();
            }
        });

    }

    private void checkSeller(boolean checked) {
        double totalPrice = 0;
        int num = 0;
        for (int a = 0; a < databeans.size(); a++) {
            //遍历商家，改变状态
            GoodsBean.DataBean dataBean = databeans.get(a);
            dataBean.setCheck(checked);

            List<GoodsBean.DataBean.ListBean> listAll = databeans.get(a).getList();

            //重新遍历 现有的商品
            for (int i = 0; i < listAll.size(); i++) {
                listAll.get(i).setCheck(checked);
                totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                num = num + listAll.get(i).getNum();
            }
        }

        if (checked) {
            zprice.setText("合计：" + totalPrice);
            jiesuan.setText("结算（"+num+")");
        } else {
            zprice.setText("合计：0.00");
            jiesuan.setText("结算（"+num+")");
        }


    }

    @Override
    public void setSuccess(Object data) {
        GoodsBean goodsBean = (GoodsBean) data;
        databeans = goodsBean.getData();
        if (databeans != null) {
            databeans.remove(0);
            sd.setData(databeans);
        }

    }
}
