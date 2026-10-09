const CACHE = 'masjid-v1';
const ASSETS = [
  './', './index.html', './quran.js', './quran-font.ttf',
  './azan.mp3', './bg-calligraphy.jpg', './bg-mosque.jpg',
  './icon_masjid.svg', './icon-192.png', './icon-512.png',
  './apple-touch-icon.png', './masjid_manifest.webmanifest'
];

self.addEventListener('install', e => {
  e.waitUntil(
    caches.open(CACHE).then(c => c.addAll(ASSETS)).then(() => self.skipWaiting())
  );
});

self.addEventListener('activate', e => {
  e.waitUntil(
    caches.keys()
      .then(ks => Promise.all(ks.filter(k => k !== CACHE).map(k => caches.delete(k))))
      .then(() => self.clients.claim())
  );
});

self.addEventListener('fetch', e => {
  const req = e.request;
  if (req.method !== 'GET') return;
  const u = new URL(req.url);
  if (u.origin !== location.origin) return; // fonts / quran api / youtube go to network
  e.respondWith(
    caches.match(req).then(hit => hit || fetch(req).then(res => {
      if (res && res.ok && res.type === 'basic') {
        const cp = res.clone();
        caches.open(CACHE).then(c => c.put(req, cp));
      }
      return res;
    }).catch(() => caches.match('./index.html')))
  );
});
