# PRD — Masjid e Mohammadi website (Phase 2: full APK mirror)

## Product vision
Website jo APK ka poora experience de — login se last screen tak — desktop
aor mobile dono pe clean, animated, premium. Masjid content (namaz timing,
Quran, videos, contacts) ek jagah.

## Goals
1. APK parity: har app screen website pe (login gate, home, quran, duas,
   tasbeeh, qibla, youtube, contacts, user/settings).
2. Laptop pe bhi "sahi" — naya responsive design (mobile pe pehle se sahi tha).
3. Animations jo kaam samjhayen (dock hover, panel switch, entrances).
4. Profile + saved login: ek baar login, phir aaram se.

## Target users
- Shan (admin/owner) — content dikhao, logo/profile personal.
- Masjid visitors — namaz time, Quran, videos, contact ke liye.
- Phone + laptop dono pe browsing.

## User stories
- As a visitor: password set kar ke login karun, "Save" chunun → agli baar
  seedha khule.
- First login: profile popup me image, naam, gender, DOB, language bharein.
- Home pe namaz timings + alerts dekhu (countdown NAHI).
- Qibla: auto (location) ya manually degree daal kar direction lo.
- Dock (bottom) me hover → naam, click → tab khule. Mobile pe bhi dock.
- Quran: 114 surah, 6 qari audio, 36 tarjuma languages.
- Language screen: koi bhi language choose karun.
- User screen: profile edit + settings + admin link.

## Functional requirements
- **Auth (local, no server)**: password set on first profile setup. Gate
  screen har visit pe (agar Not-save). "Save login?" popup after login:
  Save → localStorage persistent; Not save → sessionStorage (tab-close pe
  wapas password). Change password in User panel.
- **Profile**: localStorage `masjid-profile` {img?, name, gender, dob, lang}.
  First-login popup (required: naam/gender/dob/lang; image optional).
  Home greeting: "Assalamu alaikum, {name}".
- **Home panel**: prayer times (Aladhan API + city select + geolocation/IP
  fallback, localStorage `masjid-city`), alerts list, hijri date, qibla
  bearing chip. NO countdown ring.
- **Quran panel**: tabs Quran | Duas | Tasbeeh | Qibla (reuse quran.js +
  quran-data.js). Reader: surah list/search, ayah text + translation,
  play per-ayah + play-surah (6 qari), 36 translations (English bundled).
  Qibla tab: AUTO (geolocation → bearing + optional deviceorientation
  needle) | MANUAL (degree input/slider + city) — ek clear toggle.
- **YouTube panel**: real channel videos (shanhaq ka masjid channel via RSS)
  + in-page youtube-nocookie player.
- **Contacts panel**: phone/maps/YouTube/Muazzin (placeholders, admin baad me).
- **User panel**: profile card (image/naam/gender/dob edit), Language screen
  (all 36 + UI langs), Settings (theme? admin link, logout/reset).

## Non-functional
- Single index.html (inline CSS/JS) + shared data files (quran.js,
  quran-data.js, quran-font.ttf, sw.js). No frameworks. <500KB logic.
- Responsive: 375px + 1440px both tested. `overflow:hidden` shell + panel
  router (no page reload), document.title updates.
- Animations: transform/opacity only, prefers-reduced-motion degrade.
- Offline: sw.js caches app shell + Quran data. Location fallbacks chain.
- a11y: focus rings, aria-labels on dock, contrast ≥4.5:1, keyboard nav.

## User flows
1. Visit → gate (password) → Save-login popup → profile popup (first time)
   → Home.
2. Dock tap → panel open (Quran/YouTube/Contacts/User).
3. Quran: surah list → reader → tarjuma/qari pick → play.
4. Qibla: auto location OR manual degree → needle.

## Edge cases
- Password bhoole? → Reset (local data clear) button on gate (grey, with
  warning). Save-login reject → sessionStorage hi.
- Location block → IP fallback → city select prompt.
- Offline → cached data, toasts.
- DataURL wallpaper (admin override) → keep.

## Success metrics
- Shan laptop pe kholke bolta hai "sahi hai".
- Har APK screen website pe kaam karta hai.
- Smoke test + mobile/laptop manual check pass.

## Out of scope (abhi)
- Real backend admin (baad me), real contact numbers (admin), iOS/Windows
  installers, multi-language UI text (sirf translations + lang select).