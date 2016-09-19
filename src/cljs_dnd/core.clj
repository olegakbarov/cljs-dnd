
(ns cljs-dnd.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
    [cljs-dnd.state :refer [state update-state]]
    [cljs.core.async :refer [<! put! chan timeout]]
    [goog.events :as events])
  (:import [goog.events EventType]))

(defn parse-event [e]
  ; (cond = (-> e .-type)
  ;   "dragenter" (.preventDefault e)
  ;   "dragleave" (.preventDefault e))
  (let [ix (-> e .-target .-dataset .-index int)
        dix (if (nil? (:dix @state)) ix (:dix @state))
        bottom (-> e .-target .getBoundingClientRect .-bottom)
        top (-> e .-target .getBoundingClientRect .-top)
        event-type (-> e .-type)]
    {:dix dix
     :hix ix
     :top top
     :bottom bottom
     :mid (/ (- bottom top) 2)
     :item-type  (-> e .-target .-dataset .-type)
     :event-type event-type}))

(defn handle-drag-start [e]
  (let [{:keys [dix item-type]} e]))

(defn handle-drag-enter [e]
  (let [{:keys [dix hix]} @state]))

(defn handle-drag-leave [e]
  (let [{:keys [dix hix]} @state]))

(defn handle-drag-end [e]
  (let [{:keys [dix hix]} @state]))

(defn handle-drag [e]
  (swap! state assoc :y (.-clientY e)))

(defn handle-drag-over [e]
  (let [{:keys [dix hix]} @state]))

(defn handle-drop [e]
  (let [{:keys [dix hix]} @state]))

; (defn- events->chan [el event-type c]
;   (events/listen el event-type #(put! c %))
;   c)

;; --------------------------
;; EVENTS:

(defn on-event [e]
  (put! dnd-chan (parse-event e)))

;; --------------------------
;; LISTENER:

(defn listen! []
  (events/listen js/window EventType.DRAGSTART on-event)
  (events/listen js/window EventType.DRAGOVER  on-event)
  (events/listen js/window EventType.DRAGEND   on-event)
  (events/listen js/window EventType.DRAG      on-event)
  (events/listen js/window EventType.DRAGENTER on-event)
  (events/listen js/window EventType.DRAGLEAVE on-event)

  (go-loop []
     (let [e (<! dnd-chan)
           {:keys [event-type]} e]
        (condp = event-type
          "dragenter" (handle-drag-enter e)
          "dragleave" (handle-drag-leave e)
          "dragstart" (handle-drag-start e)
          "dragover"  (handle-drag-over e)
          "drag"      (handle-drag e)
          "drop"      (handle-drop e)
          :default    (prn event-type))
       (recur))))

(listen!)
