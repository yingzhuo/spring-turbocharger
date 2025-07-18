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
可重入分布式锁 - 刷新TTL
作者: 应卓

KEYS   : 未使用
ARGV[1]: 作为键的字符串
ARGV[2]: HASH的 field
ARGV[3]: 新的TTL

return:
    true : 续期成功
    false: 续期失败
--]]

local key = ARGV[1]
local field = ARGV[2]
local newTtl = ARGV[3]

-- 如果键已存在并且已存在保存重入数的字段，则表示锁不属于当前线程
if redis.call('EXISTS', key) == 1 and redis.call('HEXISTS', key, field) == 0 then
    return false
else
    return redis.call('EXPIRE', key, newTtl) == 1
end
