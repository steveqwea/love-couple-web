# -*- coding: utf-8 -*-
"""把本地 uploads 目录下的照片/头像转成 photo_file 表的 INSERT 语句"""
import os

BASE = r"C:\Users\12659\Desktop\hanhan\love-couple-web"
UPLOADS = os.path.join(BASE, "uploads")
OUT = os.path.join(BASE, "database", "photo_file_migration.sql")

CTYPES = {".jpg": "image/jpeg", ".jpeg": "image/jpeg", ".png": "image/png", ".gif": "image/gif", ".webp": "image/webp"}

lines = [
    "-- 存量照片/头像迁移：把本地文件内容写入 photo_file 表",
    "CREATE TABLE IF NOT EXISTS `photo_file` (",
    "  `id` bigint PRIMARY KEY AUTO_INCREMENT,",
    "  `url` varchar(255) NOT NULL,",
    "  `content_type` varchar(100) DEFAULT 'image/jpeg',",
    "  `data` mediumblob NOT NULL,",
    "  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,",
    "  UNIQUE KEY `uk_url` (`url`)",
    ") COMMENT='上传文件内容';",
]

count = 0
for root, dirs, files in os.walk(UPLOADS):
    for name in files:
        path = os.path.join(root, name)
        rel = os.path.relpath(path, UPLOADS).replace("\\", "/")
        url = "/uploads/" + rel
        ext = os.path.splitext(name)[1].lower()
        ctype = CTYPES.get(ext, "application/octet-stream")
        with open(path, "rb") as f:
            data = f.read()
        hexdata = data.hex()
        lines.append(
            "INSERT INTO photo_file (url, content_type, data) VALUES ('%s', '%s', 0x%s);"
            % (url, ctype, hexdata)
        )
        count += 1
        print(url, len(data))

with open(OUT, "w", encoding="utf-8") as f:
    f.write("\n".join(lines) + "\n")

print("total:", count, "->", OUT)
