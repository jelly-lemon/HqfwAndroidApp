package com.example.hqfwandroidapp.model;


//import com.example.hqfwandroidapp.viewdata.DiscoveryCard;
import com.example.hqfwandroidapp.entity.DiscoveryCard;
import com.example.hqfwandroidapp.interfaces.IDiscoveryPresenter;
import com.example.hqfwandroidapp.presenter.DiscoveryPresenter;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import java.lang.reflect.Type;
import java.util.List;


/**
 * 发现页面模型
 */
public class DiscoveryModel {
    private IDiscoveryPresenter iDiscoveryPresenter;

    public DiscoveryModel(DiscoveryPresenter discoveryPresenter) {
        iDiscoveryPresenter = discoveryPresenter;
    }


    /**
     * 刷新
     */
    public void refresh() {
        OkGo.<String>get(Urls.DiscoverServlet())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        List<DiscoveryCard> discoveryCardList = convertData(response.body());  // 转化格式
                        iDiscoveryPresenter.showRefreshResult(discoveryCardList);   // 接口回调，通知界面显示结果
                    }
                });
    }

    /**
     * 加载更多
     * @param start 从哪一条数据开始获取
     */
    public void loadMore(int start) {
        OkGo.<String>post(Urls.DiscoverServlet())
                .params("start", start)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        List<DiscoveryCard> discoveryCardList = convertData(response.body());  // 转化格式
                        iDiscoveryPresenter.showLoadMoreResult(discoveryCardList);   // 接口回调，通知界面显示结果
                    }
                });

    }

    /**
     * 将 string 类型的数据转化成 ArrayList
     * @param result json 格式的内容
     * @return  List 类型的数据
     */
    private List<DiscoveryCard> convertData(String result) {
        List<DiscoveryCard> discoveryCardList = null;
        try {

            Type type = new TypeToken<List<DiscoveryCard>>(){}.getType();
            Gson gson = new Gson();
            discoveryCardList = gson.fromJson(result, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return discoveryCardList;
    }


}
