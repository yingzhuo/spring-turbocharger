#!/usr/bin/env bash
set -e

# 检查是否有未提交的更改
if git diff --quiet && git diff --staged --quiet; then
    echo "[INFO] 没有检测到更改，跳过提交。"
    exit 0
fi

# 暂存所有更改并提交
git add .
TIMESTAMP=$(/bin/date "+%F %T")
git commit -m "$TIMESTAMP"

REMOTES=$(git remote)
for remote in $REMOTES; do
    echo "[INFO] 正在推送至远程仓库：$remote"
    git push "$remote" HEAD  # HEAD 表示当前分支[10,11](@ref)
done
