
# cljs-dnd

>
> «... friends don't let friends keep their state in the DOM ...»
>

Idiomatic drag-and-drop for ClojureScript applications with React rendering.

# This is very much work in progress. I'm figuring this out...

# Entities:

### State

The state lets you update the props of your components in response to the drag and drop state changes.

# High level overview:

```
1. wrapped-component receives props

2. you manage your state with this data (rearrange, change styles, dispatch stuff)

3. wrapped comp emits events and goes to 1.
```

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

- Build in the spirit of React-DnD i.e. data, and not the views, as the source of truth, pluggable backends, flexible and low-level
- Use of ClojureScript killer features: `core.async` & `Google Closure`
- No dependency on framework (Reagent/Rum)

# Inspiration

[React-DnD](http://gaearon.github.io/react-dnd/)

## License

Copyright © 2016 Oleg Akbarov

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
