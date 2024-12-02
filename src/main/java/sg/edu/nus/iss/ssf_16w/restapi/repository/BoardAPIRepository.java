package sg.edu.nus.iss.ssf_16w.restapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.ssf_16w.utilities.Constant;

@Repository
public class BoardAPIRepository {
    @Qualifier(Constant.REDIS_TEMPLATE)
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String key, String element) {
        String[] values = element.split(",");
        String id = values[0];
        redisTemplate.opsForHash().put(key, id, element);
    }

    public String getById(String key, int id) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        String data = ops.get(key, Integer.toString(id));
        return data;
    }

    public boolean put(String key, String element, boolean upsert) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        String[] values = element.split(",");
        if (!ops.hasKey(key, values[0]) && !upsert) {
            return false;
        }
        ops.put(key, values[0], element);
        return true;
    }
}
