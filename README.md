
# cljs-dnd

Idiomatic drag-and-drop for ClojureScript applications with React rendering.

# ==============================
# WORK IN PROGRESS / NOT USEABLE
# Current state: active design stage
# ==============================

# Current design sketch

Components wrapped with HoCs provided by `cljs-dnd`.

```clojure
(:require [cljs-dnd.core :refer [drag-wrapper
                                 drop-wrapper]])

(defn container []
  [:ul
    (for [item cards]
      [drop-wrapper type card-target
        [drag-wrapper type card-source
          [card item]]])]) ;; items is the components we wrap
```

Both `card-target` and `card-source` provide interface for handling custom logic of your app. This might look like this:

```clojure
(def card-target
  {:on-hover (fn [props dnd-state]
                (let [{:keys [drag-index drop-index]} dnd-state
                      {:keys [id move-card]} props]
                    ;; more logic
                    (move-card id)))})
```

# Goals

- Declarative drag-and-drop for ClojureScript apps rendered with React
- Not dependent on framework (Reagent/Rum)
- Flexible and low-level solution
- Use of ClojureScript killer features: `core.async` & `Google Closure`
- pluggable backends (html5, touch)

# Inspiration

[React-DnD](http://gaearon.github.io/react-dnd/)

## License

Copyright © 2016 Oleg Akbarov

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
