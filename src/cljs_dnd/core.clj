
(ns cljs-dnd.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
    [cljs-dnd.state :refer [state]]
    [cljs.core.async :refer [<! put! chan timeout]]
    [goog.dom :as g-dom]
    [goog.events :as events])
  (:import [goog.events EventType]))


(defn update-state [key val]
  (let [keyz (set (keys @state))]
    (if (contains? keyz key)
      (swap! state assoc key val)
      ("ERROR"))))

(defn parse-event [e]
  {:hix (-> e .-target .-dataset .-index int) ;; index of hovering or hoverable item?
   :dix (:dix @state)         ;; and here?

   :item-bottom (-> e .-target .getBoundingClientRect .-bottom)
   :item-top (-> e .-target .getBoundingClientRect .-top)
   :item-mid (/ (- brect-bottom brect-top) 2)
   :item-type  (-> e .-target .-dataset .-type)
   :event-type (-> e .-type)}) ;; TODO check!

(defn on-drag-start [e]
  (let [{:keys [index drag-index brect-top brect-bottom middle-y item-type event-type]} (parse-event e)]))
    ;; (swap! dnd-store assoc :drag-id index)))

(defn on-drag-over [e]
  (let [{:keys [index drag-index brect-top brect-bottom middle-y item-type event-type]} (parse-event e)]))
    ;; (put! dnd-chan)))

(defn on-drag [e]
  "nimp")
  ;; update client-y

(defn on-drag-end [e]
  "nimp")

; (defn- events->chan [el event-type c]
;   (events/listen el event-type #(put! c %))
;   c)

(defn listen! []
  (events/listen js/window EventType.DRAGSTART on-drag-start)
  (events/listen js/window EventType.DRAGOVER on-drag-over)
  (events/listen js/window EventType.DRAGEND on-drag-end)
  ; (events/listen js/window EventType.DRAGENTER on-drag-enter)
  ; (events/listen js/window EventType.DROP on-drop)

  (go-loop []
     (let [e (<! dnd-chan)
           {:keys [hix cli-y]} e        ;; destruct event
           {:keys [drag-index]} @state]  ;; destruct state

        (when-not (= dix hix)
          (dispatch [:reorder_msg drag-index hover client-y]))

       (recur))))

(listen!)
