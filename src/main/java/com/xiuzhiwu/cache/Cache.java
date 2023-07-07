package com.xiuzhiwu.cache;

import java.io.Serializable;

/***
 * @Author xiuzhiwu
 * @Date 2023/5/23 11:06
 * @Description
 */
public interface Cache {

    <T extends Serializable> T get(String key);
}
