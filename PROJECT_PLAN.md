# PROJECT_PLAN — Masjid e Mohammadi website PHASE 2 (FULL APP MIRROR REBUILD)

> Phase 1 (landing/sidebar/dock/reel-M) = REJECTED by Shan 2026-10-10.
> Phase 2 = website APK ka poora mirror, naya premium look, animations, phone+desktop dono.

## Spec (Shan, 2026-10-10)
- Mobile pe sure site sahi thi, LAPTOP pe nahi → dono ke liye build.
- Animations WITH. Login gate + "Save login?" popup (Save=always open, Not=har baar password).
- Home pe countdown NAHI. Qibla = auto | manual degree. Nav = BOTTOM DOCK (mac-style hover name).
- Language: sab options. First-login popup: image(opt) + name/gender/dob/language(req) = profile.

## Master TODO (webdev pipeline — tick as done)
- [x] Boot questions (Shan ne spec de di)
- [x] Memory save (AGENTS.md + vault)
- [x] PROJECT_PLAN.md + PRD.md (ye files)
- [ ] UI PREVIEW GATE: site/ui-preview.html — 3 directions (Arctic Glass / Cream Editorial / Liquid Slate) → Shan picks
- [ ] PRD review ok (Shan ke answers ke saath finalize)
- [ ] Design system tokens (winner direction) — colors/fonts/spacing/radius/shadow/dock
- [ ] Screens build (one at a time, tested):
  - [ ] Login gate + Save-login popup + first-login profile popup
  - [ ] App shell: wallpaper bg + bottom dock + panel router (no page reload)
  - [ ] Home panel (prayer times + alerts + hijri + qibla bearing, NO countdown)
  - [ ] Quran panel (reader quran.js, 6 qari, 36 tarjuma) + Duas + Tasbeeh + Qibla (auto/manual + compass)
  - [ ] YouTube panel (channel videos + in-app player)
  - [ ] Contacts panel
  - [ ] User panel (profile edit + language full options + settings)
- [ ] Animations pass (entrances, dock hover, transitions) + prefers-reduced-motion
- [ ] Responsive QA (375px phone + 1440px laptop — Shan laptop pe bhi check karega)
- [ ] smoke-site QA (extend asserts: login flow, dock, panels)
- [ ] CUSTOMIZATION ROUND (Shan changes feedback)
- [ ] Deploy: sync-web.sh → docs/ → push (live) + sw cache bump
- [ ] Post-launch feedback loop

## Rules
- ONE screen at a time, tested before next (Shan: app me bhi yahi rule).
- Demo/preview always in site/ + preserve loop in sync-web.sh.
- Live URL dekhne ke liye `?v=<unique>` cache-bust.
- app/ (WebView) is untouched. docs/app/ rehta hai.