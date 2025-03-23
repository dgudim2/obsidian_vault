#!/usr/bin/venv python3

import json
from pathlib import Path

ignore_conf = json.loads(Path(".ignore_config.json").read_text("utf-8"))

git_ignore = []
st_ignore = []

for ignore, target in ignore_conf.items():
    if target == "git":
        git_ignore.append(ignore)
        st_ignore.append(f"!{ignore}")
        continue

    if target == "both":
        git_ignore.append(ignore)
        st_ignore.append(ignore)
        continue

    print(f"Unsupported type: {target} for pattern {ignore}")


st_ignore.append("*")

Path(".gitignore").write_text("\n".join(git_ignore))
Path(".stignore").write_text("\n".join(st_ignore))

print(f"Generated ignore files, {len(git_ignore)} patterns")
