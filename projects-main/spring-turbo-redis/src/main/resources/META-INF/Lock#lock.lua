--[[

    Copyright 2022-present the original author or authors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

]]
--[[
可重入分布式锁 - 加锁
作者: 应卓

KEYS   : 未使用
ARGV[1]: 作为键的字符串
ARGV[2]: HASH的 field
ARGV[3]: 键TTL (秒)

return: 整数
    0: 加锁失败
    其他值: 当前锁被重入的次数
--]]

local key = ARGV[1]
local field = ARGV[2]
local ttl = ARGV[3]

if redis.call('EXISTS', key) == 1 and redis.call('HEXISTS', key, field) == 0 then
    return 0
end

local count = redis.call('HINCRBY', key, field, 1)
redis.call('EXPIRE', key, ttl)
return count
