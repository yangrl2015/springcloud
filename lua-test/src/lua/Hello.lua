---
--- Created by yang.
--- DateTime: 2020/9/19 21:39
---
local redis = require 'redis'
local host = "192.168.0.3"
local port = 6379
local client = redis.connect(host, port)

local function readFile(fileName)
    local f = io.open(fileName,'r')
    print(f)
    local content = f:read('*a')
    f:close()
    return content
end
local content = readFile("C:\\code\\springcloud\\lua-test\\src\\lua\\eval.lua")
--print(content)
local result = client:eval(content,0)
print(result[1])
