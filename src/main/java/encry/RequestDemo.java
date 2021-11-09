package encry;

import com.alibaba.fastjson.JSON;
import encry.entity.OpenEntity;
import encry.entity.OpenResult;
import encry.service.QueryAssetService;
import encry.service.impl.QueryAssetServiceImpl;

/**
 * @Classname RequestDemo
 * @Date 2021/11/9 10:49
 * @Created by chenrujia
 * @Description
 */
public class RequestDemo {

   public static void main(String[] args) {
       // Request queryAssets
       QueryAssetService queryAssetService = new QueryAssetServiceImpl();
       OpenResult result = queryAssetService.queryAssets(new OpenEntity());
       System.out.println(JSON.toJSONString(result));
   }
}
