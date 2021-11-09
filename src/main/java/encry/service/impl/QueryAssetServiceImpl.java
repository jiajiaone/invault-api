package encry.service.impl;

import com.alibaba.fastjson.JSON;
import encry.entity.OpenResult;
import encry.entity.RPCResult;
import encry.service.QueryAssetService;
import encry.utils.HttpClientUtil;
import encry.utils.SignUtils;

/**
 * @Classname queryAssetServiceImpl
 * @Date 2021/11/8 15:04
 * @Created by chenrujia
 * @Description
 */
public class QueryAssetServiceImpl implements QueryAssetService {

    @Override
    public OpenResult queryAssets(String keyStr, String privateKey, String url) {
        // 符合JSON-RPC 2.0 ， queryAssetList为每个接口的method
        RPCResult rpcResult = RPCResult.placeDate("queryAssets",null);
        // 转化为json字符串
        String data = JSON.toJSONString(rpcResult);
        String sign = SignUtils.sign(privateKey,data);
        String context = HttpClientUtil.doPostJson(keyStr,sign,url,data);
        return JSON.parseObject(context,OpenResult.class);
    }
}
