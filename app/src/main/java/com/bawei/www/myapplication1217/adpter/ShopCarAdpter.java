package com.bawei.www.myapplication1217.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bawei.www.myapplication1217.R;
import com.bawei.www.myapplication1217.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

public class ShopCarAdpter extends RecyclerView.Adapter<ShopCarAdpter.MAViewHolder> {
    private Context context;
    private List<GoodsBean.DataBean> mlist;

    public ShopCarAdpter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
    }

    @NonNull
    @Override
    public ShopCarAdpter.MAViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.shopcar, null);
        MAViewHolder vh = new MAViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopCarAdpter.MAViewHolder maViewHolder, final int i) {
        maViewHolder.tv_shop.setText(mlist.get(i).getSellerName());

        final ShopChildAdpter shopChildAdpter = new ShopChildAdpter(context,mlist.get(i).getList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        maViewHolder.recycler_shop.setLayoutManager(layoutManager);
        maViewHolder.recycler_shop.setAdapter(shopChildAdpter);


        maViewHolder.check_shop.setChecked(mlist.get(i).isCheck());

        shopChildAdpter.setListener(new ShopChildAdpter.ShopCallBackListener() {
            @Override
            public void callback() {
                if(mShopCallBackListener!=null){
                    mShopCallBackListener.callBack(mlist);
                }
                List<GoodsBean.DataBean.ListBean> listBeans = mlist.get(i).getList();

                boolean isallChecked= true;

                for(GoodsBean.DataBean.ListBean bean : listBeans){
                    if(!bean.isCheck()){
                        isallChecked=false;
                        break;
                    }
                }

                maViewHolder.check_shop.setChecked(isallChecked);
                mlist.get(i).setCheck(isallChecked);
            }
        });

        shopChildAdpter.setjjListener(new ShopChildAdpter.ShopjjCallbackListener() {
            @Override
            public void setcallback(int num) {
                if(mShopCallBackListener!=null){
                    mShopCallBackListener.setcallback(num);
                }
            }
        });

        maViewHolder.check_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.get(i).setCheck(maViewHolder.check_shop.isChecked());
                shopChildAdpter.selectorRemoveall(maViewHolder.check_shop.isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setData(List<GoodsBean.DataBean> databeans) {
        this.mlist = databeans;
        notifyDataSetChanged();
    }

    public class MAViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox check_shop;
        private final RecyclerView recycler_shop;
        private final TextView tv_shop;

        public MAViewHolder(@NonNull View itemView) {
            super(itemView);
            check_shop = itemView.findViewById(R.id.check_shop);
            recycler_shop = itemView.findViewById(R.id.recycler_shop);
            tv_shop =  itemView.findViewById(R.id.tv_shop);
        }
    }

    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack(List<GoodsBean.DataBean> list);
        void setcallback(int i);
    }

    //加减的监听

//    //设置加减的监听
//    private ShopjjCallbackListener mshopjjCallbackListener;
//
//    public void setjjListener(ShopjjCallbackListener shopjjCallbackListener) {
//        this.mshopjjCallbackListener = shopjjCallbackListener;
//    }
//
//    public interface ShopjjCallbackListener {
//        void setcallback(int i);
//    }



}
