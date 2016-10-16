
(ns examples.sort
  (:require [reagent.core :as r]
            [cljs-dnd.core :refer [dnd-start!
                                   drag-wrapper
                                   drop-wrapper]]))

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

(defn card [props]
  (let [{:keys [id title]} props]
    ^{:key id}
    [:li
      {:style style}
      title]))

(def type "CARD")

;; BUSINESS LOGIC HERE

(def card-target
  {:on-hover (fn [props dnd-state]
                (let [{:keys [hix dix]}   dnd-state
                      {:keys [move-card]} props]
                  (if "a lot of cond met"
                    (move-card))))})

(def card-source
  {:on-drag (fn [dnd-chan]
              (let [id index] ;; get id and index somewhere
                (put! dnd-chan id index)))})

(defn container []
  [:ul
    (for [item cards]
      [drop-wrapper type card-target
        [drag-wrapper type card-source
          [card item]]])])

(defn mount-root []
  (dnd-start!)
  (r/render [container]
    (.getElementById js/document "root")))
