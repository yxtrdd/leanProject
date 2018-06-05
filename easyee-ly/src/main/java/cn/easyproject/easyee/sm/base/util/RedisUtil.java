package cn.easyproject.easyee.sm.base.util;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import cn.easyproject.easyee.sm.base.tool.Redis;

/**
 * redis cache 工具类
 * 
 */
public final class RedisUtil {

    @Resource
    public RedisTemplate<Serializable, Object> redisTemplate;
    public static boolean reidsCache = true;
    public static int loopExcption = 0;

    /**
     * 批量删除对应的value
     * 
     * @param keys
     */
    public void remove(final String... keys) {
        try {
            if (reidsCache) {
                for (String key : keys) {
                    System.out.println(">>>删 除KEY：" + key);
                    remove(key);
                }
            }
        } catch (org.springframework.dao.DataAccessResourceFailureException ex) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }
        }
    }

    /**
     * 批量删除key
     * 
     * @param pattern
     */
    public void removePattern(final String pattern) {
        try {
            if (reidsCache) {
                Set<Serializable> keys = redisTemplate.keys(pattern);
                if (keys.size() > 0) {
                    System.out.println(">>>批量删 除KEY：" + keys);
                    redisTemplate.delete(keys);
                }
            }
        } catch (org.springframework.dao.DataAccessResourceFailureException ex) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }

        }
    }

    /**
     * 删除对应的value
     * 
     * @param key
     */
    public void remove(final String key) {
        try {
            if (reidsCache) {
                if (exists(key)) {
                    redisTemplate.delete(key);
                }
            }
        } catch (org.springframework.dao.DataAccessResourceFailureException ex) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * 
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        try {
            if (reidsCache) {
                return redisTemplate.hasKey(key);
            } else {
                return false;
            }
        } catch (org.springframework.dao.DataAccessResourceFailureException ex) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }
            return false;
        }

    }

    /**
     * 读取缓存
     * 
     * @param key
     * @return
     */
    public Object getObject(final String key) {
        Object result = null;
        try {
            if (reidsCache) {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                return result = operations.get(key);
            }
        } catch (org.springframework.dao.DataAccessResourceFailureException ex) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }
            return result;
        }
        return result;
    }

    /**
     * 写入缓存
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean setObject(final String key, Object value) {
        boolean result = false;
        try {
            if (reidsCache) {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                result = true;
            }
        } catch (org.springframework.dao.DataAccessResourceFailureException ex) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }
            return result;
        }
        return result;

    }

    /**
     * 读取缓存
     * 
     * @param key
     * @return
     */
    public String getString(final String key) {
        Object result = null;
        try {
            if (reidsCache) {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                result = operations.get(key);
            }
        } catch (org.springframework.dao.DataAccessResourceFailureException ex) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }
        }
        return result == null ? "" : result.toString();

    }

    /**
     * 写入缓存
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean setString(final String key, String value) {
        boolean result = false;
        try {
            if (reidsCache) {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                result = true;
            }
        } catch (org.springframework.dao.DataAccessResourceFailureException ex) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }
        }
        return result;
    }

    /**
     * 写入缓存
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean setString(final String key, String value, Long expireTime) {
        boolean result = false;
        try {
            if (reidsCache) {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
                result = true;
            }
        } catch (org.springframework.dao.DataAccessResourceFailureException ex) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }
        }
        return result;
    }

    /**
     * 写入缓存
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean setObject(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            if (reidsCache) {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
                result = true;
            }
        } catch (org.springframework.dao.DataAccessResourceFailureException ex) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }
        }
        return result;
    }

    /**
     * 执行删除在某个db环境下执行的话，只删除当前db的数据
     * @author gaojx
     */
    public void flushDB(){
        try {
            redisTemplate.getConnectionFactory().getConnection().flushDb();
        } catch (org.springframework.dao.DataAccessResourceFailureException e) {
            loopExcption++;
            if (loopExcption == Redis.excptionNum) {
                reidsCache = false;
            }
        }
    }
}