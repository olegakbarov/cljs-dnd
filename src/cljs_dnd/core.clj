
(ns cljs-dnd.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
    ; [reagent.core :as r]
    ; [re-frame.core :refer [dispatch]]

    [cljs.core.async :refer [<! put! chan timeout]]
    [goog.dom :as g-dom]
    [goog.events :as events])
  (:import [goog.events EventType]))

(defn on-drag-start [e]
  (let [index (int (.-index (.-dataset (.-target e)))) ;; TODO: not being bound to .-dataset
        brect-bottom (.-bottom (.getBoundingClientRect (.-target e)))
        brect-top (.-top (.getBoundingClientRect (.-target e)))
        middle-y (/ (- brect-bottom brect-top) 2)]
      (swap! dnd-store assoc :drag-id index)))

(defn on-drag [e]
  "nimp")

(defn on-drag-over [e]
  (let [index (int (.-index (.-dataset (.-target e))))
        brect-bottom (.-bottom (.getBoundingClientRect (.-target e)))
        brect-top (.-top (.getBoundingClientRect (.-target e)))
        middle-y (/ (- brect-bottom brect-top) 2)
        client-y (.-clientY e)]
      ;; TODO: not seems to belong here
      (swap! dnd-store assoc :client-y client-y)
      ;;
      (if (and index brect-top brect-bottom middle-y)
          (put! dnd-chan {:client-y client-y
                          :hover {:index index
                                  :brect-bottom brect-bottom
                                  :brect-top brect-top
                                  :middle-y middle-y}}))))

(defn on-drag-end [e]
  "nimp")

(defn listen! []
  (events/listen js/window EventType.DRAGSTART on-drag-start)
  (events/listen js/window EventType.DRAGOVER on-drag-over)
  ;; TODO:
  ;; moar events
  (go-loop []
     (let [e (<! dnd-chan)
           {:keys [hover client-y]} e
           drag-index (:drag-index @dnd-store)]
        (when-not (= drag-index (:index hover))
          (dispatch [:reorder_msg drag-index hover client-y]))
       (recur))))

(listen!)
