#!/usr/bin/env bash
# Sync the shared app source into docs/ for GitHub Pages (web/PWA target).
# One source of truth: app/src/main/assets  →  docs/
set -e
ROOT="$(cd "$(dirname "$0")" && pwd)"
SRC="$ROOT/app/src/main/assets"
DST="$ROOT/docs"
mkdir -p "$DST"
for f in index.html quran.js quran-font.ttf azan.mp3 bg-calligraphy.jpg bg-mosque.jpg \
         icon_masjid.svg icon-192.png icon-512.png icon-maskable-512.png \
         apple-touch-icon.png masjid_manifest.webmanifest sw.js; do
  cp "$SRC/$f" "$DST/$f"
done
: > "$DST/.nojekyll"
echo "synced $(ls "$DST" | wc -l) files to docs/"
