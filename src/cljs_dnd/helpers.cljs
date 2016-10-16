
(ns cljs-dnd.helpers)

(defn firefox? []
  (memoize (boolean (re-find #"firefox" js/navigator.userAgent))))

(defn safari? []
  (memoize (boolean js/window.safari)))
