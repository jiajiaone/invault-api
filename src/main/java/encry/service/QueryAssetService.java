package encry.service;

import encry.entity.OpenResult;

/**
 * @Classname queryAssetService
 * @Date 2021/11/8 15:01
 * @Created by chenrujia
 * @Description
 */
public interface QueryAssetService {
    OpenResult queryAssets(String keyStr,String privateKey,String url);
}
