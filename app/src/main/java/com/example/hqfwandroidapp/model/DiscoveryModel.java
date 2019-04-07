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

            //JSONArray jsonArray = new JSONArray(result);   // 获取到的字符串转化为 JSONArray


            /*ArrayList<DiscoveryCard> discoveryCardList = new ArrayList<>(); // 保存所有 DiscoveryCard
            // 提取内容，创建 DiscoveryCard 对象
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i); // 获取一个 JSONObject 对象
                // 创建 user 对象
                User user = new User();
                user.setHeadURL(jsonObject.optString("head"));  // 头像
                user.setName(jsonObject.optString("name"));  // 名字
                // 创建 Discovery 对象
                Discovery discovery = new Discovery();
                discovery.setDiscoveryID(jsonObject.optString("article_id"));          // 文章编号
                // 时间转化
                String timeStr = jsonObject.optString("time");
                Timestamp timestamp = Timestamp.valueOf(timeStr);
                discovery.setDateTime(timestamp);                                                // 时间
                discovery.setTag(jsonObject.optString("tag"));                        // 标签
                discovery.setContent(jsonObject.optString("content"));                // 内容
                discovery.setImgURL(jsonObject.optString("img_url_json"));      // 图片路径

                DiscoveryCard discoveryCard = new DiscoveryCard(user, discovery);         // 创建对应的 ViewData
                discoveryCardList.add(discoveryCard);                                   // 放入 discoveryCardList 中
            }*/
            //return discoveryCardList;   // 返回结果

        } catch (Exception e) {
            e.printStackTrace();
        }
        return discoveryCardList;
    }


}
