const CACHE = 'masjid-site-v3';
const ASSETS = [
  './', './index.html', './bg-calligraphy.jpg', './bg-mosque.jpg',
  './icon_masjid.svg', './icon-192.png', './icon-512.png',
  './apple-touch-icon.png', './manifest.webmanifest'
];

self.addEventListener('install', e => {
  e.waitUntil(caches.open(CACHE).then(c => c.addAll(ASSETS)).then(() => self.skipWaiting()));
});

self.addEventListener('activate', e => {
  e.waitUntil(
    caches.keys()
      .then(ks => Promise.all(ks.filter(k => k !== CACHE).map(k => caches.delete(k))))
      .then(() => self.clients.claim())
  );
});

self.addEventListener('fetch', e => {
  const r = e.request;
  if (r.method !== 'GET') return;
  const u = new URL(r.url);
  if (u.origin !== location.origin) return;      // fonts / aladhan / youtube -> network
  if (u.pathname.indexOf('/app/') > -1) return;  // app manages its own cache
  e.respondWith(
    caches.match(r).then(hit => hit || fetch(r).then(res => {
      if (res && res.ok && res.type === 'basic') {
        const cp = res.clone();
        caches.open(CACHE).then(c => c.put(r, cp));
      }
      return res;
    }).catch(() => caches.match('./index.html')))
  );
});
