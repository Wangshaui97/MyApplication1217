package com.bawei.www.myapplication1217.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.www.myapplication1217.R;
import com.bawei.www.myapplication1217.bean.GoodsBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ShopChildAdpter extends RecyclerView.Adapter<ShopChildAdpter.MyViewHolder> {
    private Context mcontext;
    private List<GoodsBean.DataBean.ListBean> mlist = new ArrayList<>();
    private int num;

    public ShopChildAdpter(Context context, List<GoodsBean.DataBean.ListBean> list) {
        this.mcontext = context;
        this.mlist = list;
    }

    @NonNull
    @Override
    public ShopChildAdpter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mcontext, R.layout.item_shopcar, null);
        MyViewHolder mh = new MyViewHolder(view);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopChildAdpter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.item_title.setText(mlist.get(i).getTitle());
        myViewHolder.item_price.setText(mlist.get(i).getPrice() + "");
        myViewHolder.putin.setText(mlist.get(i).getNum() + "");

        num = mlist.get(i).getNum();
        String url = mlist.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(mcontext).load(url).into(myViewHolder.item_img);

        //设置初始化状态
        myViewHolder.check_shopchild.setChecked(mlist.get(i).isCheck());

        myViewHolder.check_shopchild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mlist.get(i).setCheck(isChecked);
                //回调
                if (mshopCallBackListener != null) {
                    mshopCallBackListener.callback();
                }
            }
        });

        myViewHolder.jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                myViewHolder.putin.setText(num+"");
                mshopjjCallbackListener.setcallback(num);
                mlist.get(i).setNum(num);
                if (mshopCallBackListener != null) {
                    mshopCallBackListener.callback();
                }
            }
        });

        myViewHolder.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num<=0){
                    return;
                }else {
                    num--;
                    if (num < 1) {
                        Toast.makeText(mcontext,"再减还要不？？",Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        myViewHolder.putin.setText(num+"");
                        mshopjjCallbackListener.setcallback(num);
                        mlist.get(i).setNum(num);
                        if (mshopCallBackListener != null) {
                            mshopCallBackListener.callback();
                        }
                    }
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void selectorRemoveall(boolean checked) {
        for (GoodsBean.DataBean.ListBean bean : mlist) {
            bean.setCheck(checked);
        }
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox check_shopchild;
        private final ImageView item_img;
        private final TextView item_title, item_price;
        private final Button jia, jian;
        private final EditText putin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            check_shopchild = itemView.findViewById(R.id.check_shopchild);
            item_img = itemView.findViewById(R.id.item_img);
            item_title = itemView.findViewById(R.id.item_title);
            item_price = itemView.findViewById(R.id.item_price);
            jia = itemView.findViewById(R.id.jia);
            jian = itemView.findViewById(R.id.jian);
            putin = itemView.findViewById(R.id.putin);
        }
    }


    //设置复选框 单击事件回调
    private ShopCallBackListener mshopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mshopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callback();

    }

    //设置加减的监听
    private ShopjjCallbackListener mshopjjCallbackListener;

    public void setjjListener(ShopjjCallbackListener shopjjCallbackListener) {
        this.mshopjjCallbackListener = shopjjCallbackListener;
    }

    public interface ShopjjCallbackListener {
        void setcallback(int i);
    }


}
