
(ns cljs-dnd.examples.sort
  (:require [reagent.core :as r]
            [cljs-dnd.core :refer [dnd-start!]]
            [cljs.core.async :refer [<! put! chan timeout]]
            [cljs-dnd.wrapper :refer [drag-wrapper]]))

(def cards
  [{:id 1 :title "First things first"}
   {:id 2 :title "Second place is not an option"}
   {:id 3 :title "Three is like 1 and 2 combined"}
   {:id 4 :title "In what culture 4 is lucky number?"}
   {:id 5 :title "Five is pentagramm of numbers"}])

(def style
  {:border "1px dashed black"
   :padding "10px"
   :margin-bottom "5px"
   :cursor "move"})

;; you define your component as usual
(defn card [props]
  (let [{:keys [id title]} props]
    ^{:key id}
    [:li
      {:style style}
      title]))

(def drag-type "CARD")

(def card-target
  {:on-hover (fn [props dnd-state]
                (let [{:keys [hix dix]}   dnd-state
                      {:keys [move-card]} props]
                  (if "a lot of cond met"
                    (move-card))))})

(def card-source
  {:on-drag (fn [dnd-chan]
              (let [id "1" ;; get id and index somewhere
                    index "1"]
                (put! dnd-chan id index)))})

(defn container []
  [:ul
    (for [item cards]
      ^{:key (:id item)}
      [drag-wrapper {:drag-type drag-type :drag-target card-target}
       [card item]])])

(defn mount-root []
  ;; initialize dnd
  (dnd-start!)
  (r/render [container]
    (.getElementById js/document "root")))
