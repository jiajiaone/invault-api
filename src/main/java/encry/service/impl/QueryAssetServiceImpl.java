package encry.service.impl;

import com.alibaba.fastjson.JSON;
import encry.entity.OpenEntity;
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
    public OpenResult queryAssets(OpenEntity entity) {
        // 符合JSON-RPC 2.0 ， queryAssetList为每个接口的method
        RPCResult rpcResult = RPCResult.placeDate("queryAssets",null);
        // 转化为json字符串
        String data = JSON.toJSONString(rpcResult);
        String sign = SignUtils.sign(entity.getPrivateKey(),data);
        String context = HttpClientUtil.doPostJson(entity.getKeyStr(),sign,entity.getURL(),data);
        return JSON.parseObject(context,OpenResult.class);
    }
}
