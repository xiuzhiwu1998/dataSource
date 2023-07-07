package com.xiuzhiwu.cache;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.Serializable;

/***
 * @Author xiuzhiwu
 * @Date 2023/5/23 11:12
 * @Description
 */
public abstract class L2Cache implements Cache {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final long DEFAULT_TIMEOUT_MILLS = 60000L;
    private final Object lock = new Object();

    public L2Cache() {
    }

    @Override
    public <T extends Serializable> T get(String key) {
        if (isBlank(key)){
            return null;
        } else {
            Serializable obj = getFromLocal(key);
            if (obj == null){
                synchronized (lock){
                    obj = getFromLocal(key);
                    if (obj == null){
                        try {
                            obj = getFromRemote(key);
                            if (obj == null){
                                obj = getFromDb(key);
                            }
                        } catch (Exception var6) {
                            this.logger.error("", var6);
                        }

                        if (obj == null) {
                            obj = new NullCacheObject();
                        }

                        this.putToLocal(key, (Serializable) obj);
                    }
                }
            }

            return (T) (obj instanceof NullCacheObject ? null : obj);
        }

    }

    protected Serializable getFromDb(String key) {
        return null;
    }

    protected abstract Serializable getFromLocal(String key);

    protected abstract Serializable getFromRemote(String key);

    protected abstract void putToLocal(String key, Serializable obj);

    protected abstract void putToRemote(String key, Serializable value);


    public static boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        } else {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }
}
