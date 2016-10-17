
(ns cljs-dnd.examples.sort
  (:require [reagent.core :as r]
            [cljs-dnd.core :refer [dnd-init!]]
            [cljs.core.async :refer [<! put! chan timeout]]
            [cljs-dnd.wrapper :refer [drag-wrapper]]
            [cljs-dnd.state :refer [state]]))

(def cards
  [{:id 1 :title "First things first"}
   {:id 2 :title "Second place is not an option"}
   {:id 3 :title "Three is like 1 and 2 combined"}
   {:id 4 :title "In what culture 4 is lucky number?"}
   {:id 5 :title "Five is pentagramm of numbers"}])

(def style
  {:border "1px dashed black"
   :background-color "white"
   :padding "10px"
   :margin-bottom "5px"
   :width "300px"
   :cursor "move"})

;; you define your component as usual
(defn card [props]
  (let [{:keys [id title state]} props]
    (.log js/console (clj->js props))
    ^{:key id}
    [:li
      {:style (if (:is-dragging state)
                  (merge {:background-color "red"
                          :opacity ".4"})
                  style)}
      title]))

;; this might be any (pure) function that updates your state (eg. re-frame dispatch)
(defn swap-items
  "Swaps two items with given indexes"
  [a b]
  (map
   (fn [x] (condp = (:id x)
             a (assoc-in x [:id] b)
             b (assoc-in x [:id] a)
             x))
   cards))

(defn on-hover [props dnd-state]
    ;; list available props and state keys
    (let [{:keys [move-card]} props
          {:keys [id index]}  dnd-state]
      (if "a lot of cond met"
        (swap-items index id))))

(defn on-drag [dnd-chan id index]
  ;; get item id and index
  (put! dnd-chan id index))

;; We'll pass handlers as map
(def opts
  {:drag-type :card
   :on-hover on-hover
   :on-drag on-drag})

(defn container []
  [:ul
    [:input {:type "button"
             :on-click #(.log js/console (clj->js @state))
             :value "State"}]
    (for [item cards]
      ^{:key (:id item)}
      [drag-wrapper opts
       [card item]])])

(defn mount-root []
  ;; initialize dnd with default html5 backend
  (dnd-init!)
  (r/render [container]
    (.getElementById js/document "root")))
