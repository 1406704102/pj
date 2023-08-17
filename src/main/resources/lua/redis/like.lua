-- 调用放传递的keys 和 values  execute(RedisScript<T> script, List<K> keys, Object... args)
local keys = KEYS
local values = ARGV;

local function execute()

    local k = {values[1],'_',values[2]}
    local mapKey = table.concat(k)
    local nowLike = redis.call("hget", keys[1], mapKey)

    if nowLike == "1" then
        if values[3] == "0" then
            redis.call("hincrby", keys[2], values[2], "-1")
        end
    else
        redis.call("hincrby", keys[2], values[2], "1")
    end

    redis.call("hset", keys[1], mapKey, values[3])

    return true
end

return execute()