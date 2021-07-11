package fun.lianys.liuli.services;

public interface RedisService {

    public void set(String key, Object value);

    public Object get(String key);

    public Boolean delete(String key);
}
