#!/usr/bin/env bash
# Build docs/ (GitHub Pages deploy target) from site/ (website) + app assets.
# Website = docs/ root.  Full-screen app = docs/app/.
set -e
ROOT="$(cd "$(dirname "$0")" && pwd)"
APP="$ROOT/app/src/main/assets"
SITE="$ROOT/site"
DST="$ROOT/docs"

rm -rf "$DST"
mkdir -p "$DST/app"

# ---- website (front page) ----
cp "$SITE/index.html" "$SITE/manifest.webmanifest" "$SITE/sw.js" "$SITE/quran-data.js" "$DST/"
cp "$APP/bg-calligraphy.jpg" "$APP/bg-mosque.jpg" "$DST/"
cp "$APP/icon_masjid.svg" "$APP/icon-192.png" "$APP/icon-512.png" \
   "$APP/icon-maskable-512.png" "$APP/apple-touch-icon.png" "$DST/"
# offline Quran text + Amiri font for the website's in-page reader
cp "$APP/quran.js" "$APP/quran-font.ttf" "$DST/"

# ---- app (full screen, installable PWA) ----
for f in index.html quran.js quran-font.ttf azan.mp3 bg-calligraphy.jpg bg-mosque.jpg \
         icon_masjid.svg icon-192.png icon-512.png icon-maskable-512.png \
         apple-touch-icon.png masjid_manifest.webmanifest sw.js; do
  cp "$APP/$f" "$DST/app/$f"
done

: > "$DST/.nojekyll"
echo "synced $(find "$DST" -type f | wc -l) files to docs/ (site at /, app at /app/)"
